package com.carfix.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.carfix.common.PageResult;
import com.carfix.common.Result;
import com.carfix.entity.ServiceItem;
import com.carfix.service.ServiceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 服务项目控制器
 * 
 * @author CarFix Team
 */
@RestController
@RequestMapping("/service")
public class ServiceItemController {

    @Autowired
    private ServiceItemService serviceItemService;

    /**
     * 获取门店的服务项目列表
     */
    @GetMapping("/list")
    public Result<PageResult<ServiceItem>> getServiceItems(
            @RequestParam Long storeId,
            @RequestParam(required = false) Integer category,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        
        Page<ServiceItem> page = serviceItemService.getStoreServiceItems(storeId, category, current, size);
        PageResult<ServiceItem> pageResult = new PageResult<>(
                page.getRecords(),
                page.getTotal(),
                page.getCurrent(),
                page.getSize()
        );
        
        return Result.success(pageResult);
    }

    /**
     * 获取服务项目详情
     */
    @GetMapping("/detail/{itemId}")
    public Result<ServiceItem> getServiceItemDetail(@PathVariable Long itemId) {
        ServiceItem serviceItem = serviceItemService.getServiceItemDetail(itemId);
        return serviceItem != null ? Result.success(serviceItem) : Result.error("服务项目不存在");
    }
}

