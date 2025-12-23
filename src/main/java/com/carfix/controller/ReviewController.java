package com.carfix.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.carfix.common.PageResult;
import com.carfix.common.Result;
import com.carfix.entity.Review;
import com.carfix.service.ReviewService;
import com.carfix.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 评价控制器
 * 
 * @author CarFix Team
 */
@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    /**
     * 创建评价
     */
    @PostMapping("/create")
    public Result<String> createReview(
            @RequestHeader("Authorization") String token,
            @RequestBody Review review) {
        
        Long userId = JwtUtil.getUserIdFromToken(token);
        review.setUserId(userId);
        
        try {
            boolean result = reviewService.createReview(review);
            return result ? Result.success("评价成功") : Result.error("评价失败");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取门店评价列表
     */
    @GetMapping("/list")
    public Result<PageResult<Review>> getStoreReviews(
            @RequestParam Long storeId,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        
        Page<Review> page = reviewService.getStoreReviews(storeId, current, size);
        
        PageResult<Review> pageResult = new PageResult<>(
                page.getRecords(),
                page.getTotal(),
                page.getCurrent(),
                page.getSize()
        );
        
        return Result.success(pageResult);
    }

    /**
     * 门店回复评价
     */
    @PutMapping("/reply/{reviewId}")
    public Result<String> replyReview(
            @PathVariable Long reviewId,
            @RequestParam String reply) {
        
        boolean result = reviewService.replyReview(reviewId, reply);
        return result ? Result.success("回复成功") : Result.error("回复失败");
    }
}

