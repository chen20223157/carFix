package com.carfix.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.carfix.entity.Order;
import com.carfix.entity.ServiceItem;
import com.carfix.mapper.OrderMapper;
import com.carfix.service.OrderService;
import com.carfix.service.ServiceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 订单服务实现类
 * 
 * @author CarFix Team
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private ServiceItemService serviceItemService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(Order order) {
        // 生成订单编号
        String orderNo = generateOrderNo();
        order.setOrderNo(orderNo);
        
        // 获取服务项目信息
        ServiceItem serviceItem = serviceItemService.getById(order.getServiceItemId());
        if (serviceItem == null) {
            throw new RuntimeException("服务项目不存在");
        }
        
        // 设置订单金额
        order.setAmount(serviceItem.getPrice());
        order.setActualAmount(serviceItem.getPrice());
        
        // 设置订单状态
        order.setStatus(0); // 待确认
        order.setPayStatus(0); // 未支付
        
        this.save(order);
        return order;
    }

    @Override
    public Page<Order> getUserOrders(Long userId, Integer status, Integer current, Integer size) {
        Page<Order> page = new Page<>(current, size);
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        
        queryWrapper.eq(Order::getUserId, userId);
        
        if (status != null) {
            queryWrapper.eq(Order::getStatus, status);
        }
        
        queryWrapper.orderByDesc(Order::getCreateTime);
        
        return this.page(page, queryWrapper);
    }

    @Override
    public Page<Order> getStoreOrders(Long storeId, Integer status, Integer current, Integer size) {
        Page<Order> page = new Page<>(current, size);
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        
        queryWrapper.eq(Order::getStoreId, storeId);
        
        if (status != null) {
            queryWrapper.eq(Order::getStatus, status);
        }
        
        queryWrapper.orderByDesc(Order::getCreateTime);
        
        return this.page(page, queryWrapper);
    }

    @Override
    public Order getOrderDetail(Long orderId) {
        return this.getById(orderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean confirmOrder(Long orderId, Long technicianId) {
        Order order = this.getById(orderId);
        if (order == null || order.getStatus() != 0) {
            return false;
        }
        
        order.setStatus(1); // 已确认
        order.setTechnicianId(technicianId);
        
        return this.updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean startService(Long orderId) {
        Order order = this.getById(orderId);
        if (order == null || order.getStatus() != 1) {
            return false;
        }
        
        order.setStatus(2); // 服务中
        order.setStartTime(LocalDateTime.now());
        
        return this.updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean finishService(Long orderId) {
        Order order = this.getById(orderId);
        if (order == null || order.getStatus() != 2) {
            return false;
        }
        
        order.setStatus(3); // 已完成
        order.setFinishTime(LocalDateTime.now());
        
        return this.updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelOrder(Long orderId, String cancelReason) {
        Order order = this.getById(orderId);
        if (order == null || order.getStatus() >= 3) {
            return false;
        }
        
        order.setStatus(4); // 已取消
        order.setCancelReason(cancelReason);
        
        return this.updateById(order);
    }

    /**
     * 生成订单编号
     */
    private String generateOrderNo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        String random = String.valueOf((int) (Math.random() * 9000) + 1000);
        return "CF" + timestamp + random;
    }
}

