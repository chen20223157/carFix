package com.carfix.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.carfix.entity.ServiceItem;

/**
 * 服务项目服务接口
 * 
 * @author CarFix Team
 */
public interface ServiceItemService extends IService<ServiceItem> {

    /**
     * 获取门店的服务项目列表
     */
    Page<ServiceItem> getStoreServiceItems(Long storeId, Integer category, Integer current, Integer size);

    /**
     * 获取服务项目详情
     */
    ServiceItem getServiceItemDetail(Long itemId);
}

