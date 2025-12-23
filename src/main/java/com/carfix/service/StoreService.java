package com.carfix.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.carfix.entity.Store;

/**
 * 门店服务接口
 * 
 * @author CarFix Team
 */
public interface StoreService extends IService<Store> {

    /**
     * 分页查询门店列表
     */
    Page<Store> getStoreList(Integer current, Integer size, String city, String keyword);

    /**
     * 获取门店详情
     */
    Store getStoreDetail(Long storeId);

    /**
     * 更新门店评分
     */
    void updateStoreRating(Long storeId, Integer rating);
}

