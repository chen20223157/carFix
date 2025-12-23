package com.carfix.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.carfix.entity.ServiceItem;
import com.carfix.mapper.ServiceItemMapper;
import com.carfix.service.ServiceItemService;
import org.springframework.stereotype.Service;

/**
 * 服务项目服务实现类
 * 
 * @author CarFix Team
 */
@Service
public class ServiceItemServiceImpl extends ServiceImpl<ServiceItemMapper, ServiceItem> implements ServiceItemService {

    @Override
    public Page<ServiceItem> getStoreServiceItems(Long storeId, Integer category, Integer current, Integer size) {
        Page<ServiceItem> page = new Page<>(current, size);
        LambdaQueryWrapper<ServiceItem> queryWrapper = new LambdaQueryWrapper<>();
        
        queryWrapper.eq(ServiceItem::getStoreId, storeId);
        queryWrapper.eq(ServiceItem::getStatus, 0); // 只查询上架的服务
        
        if (category != null) {
            queryWrapper.eq(ServiceItem::getCategory, category);
        }
        
        queryWrapper.orderByAsc(ServiceItem::getPrice);
        
        return this.page(page, queryWrapper);
    }

    @Override
    public ServiceItem getServiceItemDetail(Long itemId) {
        return this.getById(itemId);
    }
}

