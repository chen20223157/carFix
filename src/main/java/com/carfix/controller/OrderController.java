package com.carfix.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.carfix.common.PageResult;
import com.carfix.common.Result;
import com.carfix.entity.Order;
import com.carfix.service.OrderService;
import com.carfix.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单控制器
 * 
 * @author CarFix Team
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     */
    @PostMapping("/create")
    public Result<Order> createOrder(
            @RequestHeader("Authorization") String token,
            @RequestBody Order order) {
        
        Long userId = JwtUtil.getUserIdFromToken(token);
        order.setUserId(userId);
        
        try {
            Order createdOrder = orderService.createOrder(order);
            return Result.success("预约成功", createdOrder);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取用户订单列表
     */
    @GetMapping("/user/list")
    public Result<PageResult<Order>> getUserOrders(
            @RequestHeader("Authorization") String token,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        
        Long userId = JwtUtil.getUserIdFromToken(token);
        Page<Order> page = orderService.getUserOrders(userId, status, current, size);
        
        PageResult<Order> pageResult = new PageResult<>(
                page.getRecords(),
                page.getTotal(),
                page.getCurrent(),
                page.getSize()
        );
        
        return Result.success(pageResult);
    }

    /**
     * 获取门店订单列表
     */
    @GetMapping("/store/list")
    public Result<PageResult<Order>> getStoreOrders(
            @RequestParam Long storeId,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        
        Page<Order> page = orderService.getStoreOrders(storeId, status, current, size);
        
        PageResult<Order> pageResult = new PageResult<>(
                page.getRecords(),
                page.getTotal(),
                page.getCurrent(),
                page.getSize()
        );
        
        return Result.success(pageResult);
    }

    /**
     * 获取订单详情
     */
    @GetMapping("/detail/{orderId}")
    public Result<Order> getOrderDetail(@PathVariable Long orderId) {
        Order order = orderService.getOrderDetail(orderId);
        return order != null ? Result.success(order) : Result.error("订单不存在");
    }

    /**
     * 确认订单
     */
    @PutMapping("/confirm/{orderId}")
    public Result<String> confirmOrder(
            @PathVariable Long orderId,
            @RequestParam Long technicianId) {
        
        boolean result = orderService.confirmOrder(orderId, technicianId);
        return result ? Result.success("确认成功") : Result.error("确认失败");
    }

    /**
     * 开始服务
     */
    @PutMapping("/start/{orderId}")
    public Result<String> startService(@PathVariable Long orderId) {
        boolean result = orderService.startService(orderId);
        return result ? Result.success("开始服务") : Result.error("操作失败");
    }

    /**
     * 完成服务
     */
    @PutMapping("/finish/{orderId}")
    public Result<String> finishService(@PathVariable Long orderId) {
        boolean result = orderService.finishService(orderId);
        return result ? Result.success("服务完成") : Result.error("操作失败");
    }

    /**
     * 取消订单
     */
    @PutMapping("/cancel/{orderId}")
    public Result<String> cancelOrder(
            @PathVariable Long orderId,
            @RequestParam String cancelReason) {
        
        boolean result = orderService.cancelOrder(orderId, cancelReason);
        return result ? Result.success("取消成功") : Result.error("取消失败");
    }
}

