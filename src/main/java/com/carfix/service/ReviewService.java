package com.carfix.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.carfix.entity.Review;

/**
 * 评价服务接口
 * 
 * @author CarFix Team
 */
public interface ReviewService extends IService<Review> {

    /**
     * 创建评价
     */
    boolean createReview(Review review);

    /**
     * 获取门店评价列表
     */
    Page<Review> getStoreReviews(Long storeId, Integer current, Integer size);

    /**
     * 门店回复评价
     */
    boolean replyReview(Long reviewId, String reply);
}

