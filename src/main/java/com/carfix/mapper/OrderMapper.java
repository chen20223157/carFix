package com.carfix.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carfix.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单Mapper接口
 * 
 * @author CarFix Team
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}

