package com.carfix.controller;

import com.carfix.common.Result;
import com.carfix.entity.User;
import com.carfix.service.UserService;
import com.carfix.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制器
 * 
 * @author CarFix Team
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 微信登录
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestParam String code) {
        try {
            String token = userService.wxLogin(code);
            Long userId = JwtUtil.getUserIdFromToken(token);
            User user = userService.getById(userId);
            
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            data.put("userInfo", user);
            
            return Result.success(data);
        } catch (Exception e) {
            log.error("登录失败", e);
            return Result.error("登录失败：" + e.getMessage());
        }
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/info")
    public Result<User> getUserInfo(@RequestHeader("Authorization") String token) {
        Long userId = JwtUtil.getUserIdFromToken(token);
        User user = userService.getById(userId);
        return Result.success(user);
    }

    /**
     * 更新用户信息
     */
    @PutMapping("/update")
    public Result<String> updateUserInfo(
            @RequestHeader("Authorization") String token,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) String avatar,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String realName) {
        
        Long userId = JwtUtil.getUserIdFromToken(token);
        boolean result = userService.updateUserInfo(userId, nickname, avatar, phone, realName);
        
        return result ? Result.success("更新成功") : Result.error("更新失败");
    }
}

