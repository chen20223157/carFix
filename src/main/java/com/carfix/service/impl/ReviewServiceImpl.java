package com.carfix.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.carfix.entity.Order;
import com.carfix.entity.Review;
import com.carfix.mapper.ReviewMapper;
import com.carfix.service.OrderService;
import com.carfix.service.ReviewService;
import com.carfix.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 评价服务实现类
 * 
 * @author CarFix Team
 */
@Service
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review> implements ReviewService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private StoreService storeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createReview(Review review) {
        // 检查订单是否已完成
        Order order = orderService.getById(review.getOrderId());
        if (order == null || order.getStatus() != 3) {
            throw new RuntimeException("订单未完成，无法评价");
        }
        
        // 检查是否已评价
        LambdaQueryWrapper<Review> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Review::getOrderId, review.getOrderId());
        if (this.count(queryWrapper) > 0) {
            throw new RuntimeException("该订单已评价");
        }
        
        // 保存评价
        boolean result = this.save(review);
        
        if (result) {
            // 更新订单状态
            order.setStatus(5); // 已评价
            orderService.updateById(order);
            
            // 更新门店评分
            storeService.updateStoreRating(review.getStoreId(), review.getRating());
        }
        
        return result;
    }

    @Override
    public Page<Review> getStoreReviews(Long storeId, Integer current, Integer size) {
        Page<Review> page = new Page<>(current, size);
        LambdaQueryWrapper<Review> queryWrapper = new LambdaQueryWrapper<>();
        
        queryWrapper.eq(Review::getStoreId, storeId);
        queryWrapper.orderByDesc(Review::getCreateTime);
        
        return this.page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean replyReview(Long reviewId, String reply) {
        Review review = this.getById(reviewId);
        if (review == null) {
            return false;
        }
        
        review.setReply(reply);
        review.setReplyTime(LocalDateTime.now());
        
        return this.updateById(review);
    }
}

