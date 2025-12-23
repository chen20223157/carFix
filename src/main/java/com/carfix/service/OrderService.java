package com.carfix.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.carfix.entity.Order;

/**
 * 订单服务接口
 * 
 * @author CarFix Team
 */
public interface OrderService extends IService<Order> {

    /**
     * 创建订单
     */
    Order createOrder(Order order);

    /**
     * 获取用户订单列表
     */
    Page<Order> getUserOrders(Long userId, Integer status, Integer current, Integer size);

    /**
     * 获取门店订单列表
     */
    Page<Order> getStoreOrders(Long storeId, Integer status, Integer current, Integer size);

    /**
     * 获取订单详情
     */
    Order getOrderDetail(Long orderId);

    /**
     * 确认订单
     */
    boolean confirmOrder(Long orderId, Long technicianId);

    /**
     * 开始服务
     */
    boolean startService(Long orderId);

    /**
     * 完成服务
     */
    boolean finishService(Long orderId);

    /**
     * 取消订单
     */
    boolean cancelOrder(Long orderId, String cancelReason);
}

