package com.carfix.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.carfix.entity.User;

/**
 * 用户服务接口
 * 
 * @author CarFix Team
 */
public interface UserService extends IService<User> {

    /**
     * 通过openid查询用户
     */
    User getUserByOpenid(String openid);

    /**
     * 微信登录
     */
    String wxLogin(String code);

    /**
     * 更新用户信息
     */
    boolean updateUserInfo(Long userId, String nickname, String avatar, String phone, String realName);
}

