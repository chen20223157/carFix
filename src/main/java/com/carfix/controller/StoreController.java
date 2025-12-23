package com.carfix.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.carfix.common.PageResult;
import com.carfix.common.Result;
import com.carfix.entity.Store;
import com.carfix.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 门店控制器
 * 
 * @author CarFix Team
 */
@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    /**
     * 获取门店列表
     */
    @GetMapping("/list")
    public Result<PageResult<Store>> getStoreList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String keyword) {
        
        Page<Store> page = storeService.getStoreList(current, size, city, keyword);
        PageResult<Store> pageResult = new PageResult<>(
                page.getRecords(),
                page.getTotal(),
                page.getCurrent(),
                page.getSize()
        );
        
        return Result.success(pageResult);
    }

    /**
     * 获取门店详情
     */
    @GetMapping("/detail/{storeId}")
    public Result<Store> getStoreDetail(@PathVariable Long storeId) {
        Store store = storeService.getStoreDetail(storeId);
        return store != null ? Result.success(store) : Result.error("门店不存在");
    }
}

