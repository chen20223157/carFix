package com.carfix.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carfix.entity.Store;
import org.apache.ibatis.annotations.Mapper;

/**
 * 门店Mapper接口
 * 
 * @author CarFix Team
 */
@Mapper
public interface StoreMapper extends BaseMapper<Store> {
}

