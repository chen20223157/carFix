package com.carfix.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.carfix.entity.Store;
import com.carfix.mapper.StoreMapper;
import com.carfix.service.StoreService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 门店服务实现类
 * 
 * @author CarFix Team
 */
@Service
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements StoreService {

    @Override
    public Page<Store> getStoreList(Integer current, Integer size, String city, String keyword) {
        Page<Store> page = new Page<>(current, size);
        LambdaQueryWrapper<Store> queryWrapper = new LambdaQueryWrapper<>();
        
        // 只查询正常营业的门店
        queryWrapper.eq(Store::getStatus, 0);
        
        // 城市筛选
        if (StringUtils.isNotBlank(city)) {
            queryWrapper.eq(Store::getCity, city);
        }
        
        // 关键词搜索
        if (StringUtils.isNotBlank(keyword)) {
            queryWrapper.and(wrapper -> wrapper
                .like(Store::getStoreName, keyword)
                .or()
                .like(Store::getDescription, keyword)
            );
        }
        
        // 按评分降序排列
        queryWrapper.orderByDesc(Store::getRating);
        
        return this.page(page, queryWrapper);
    }

    @Override
    public Store getStoreDetail(Long storeId) {
        return this.getById(storeId);
    }

    @Override
    public void updateStoreRating(Long storeId, Integer rating) {
        Store store = this.getById(storeId);
        if (store != null) {
            int reviewCount = store.getReviewCount();
            BigDecimal currentRating = store.getRating();
            
            // 计算新的平均评分
            BigDecimal totalScore = currentRating.multiply(new BigDecimal(reviewCount));
            totalScore = totalScore.add(new BigDecimal(rating));
            
            reviewCount++;
            BigDecimal newRating = totalScore.divide(new BigDecimal(reviewCount), 2, RoundingMode.HALF_UP);
            
            store.setRating(newRating);
            store.setReviewCount(reviewCount);
            this.updateById(store);
        }
    }
}

