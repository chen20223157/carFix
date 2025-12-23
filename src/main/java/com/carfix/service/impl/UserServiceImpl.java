package com.carfix.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.carfix.entity.User;
import com.carfix.mapper.UserMapper;
import com.carfix.service.UserService;
import com.carfix.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 * 
 * @author CarFix Team
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private WxMaService wxMaService;

    @Override
    public User getUserByOpenid(String openid) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getOpenid, openid);
        return this.getOne(queryWrapper);
    }

    @Override
    public String wxLogin(String code) {
        try {
            // 调用微信接口获取openid
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
            String openid = session.getOpenid();
            
            // 查询用户是否存在
            User user = getUserByOpenid(openid);
            
            // 如果用户不存在，创建新用户
            if (user == null) {
                user = new User();
                user.setOpenid(openid);
                user.setUserType(0); // 默认为车主
                user.setStatus(0);
                this.save(user);
            }
            
            // 生成token
            return JwtUtil.generateToken(user.getId(), openid);
            
        } catch (Exception e) {
            log.error("微信登录失败", e);
            throw new RuntimeException("微信登录失败：" + e.getMessage());
        }
    }

    @Override
    public boolean updateUserInfo(Long userId, String nickname, String avatar, String phone, String realName) {
        User user = this.getById(userId);
        if (user == null) {
            return false;
        }
        
        if (nickname != null) {
            user.setNickname(nickname);
        }
        if (avatar != null) {
            user.setAvatar(avatar);
        }
        if (phone != null) {
            user.setPhone(phone);
        }
        if (realName != null) {
            user.setRealName(realName);
        }
        
        return this.updateById(user);
    }
}

