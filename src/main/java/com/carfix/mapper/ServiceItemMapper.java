package com.carfix.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carfix.entity.ServiceItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * 服务项目Mapper接口
 * 
 * @author CarFix Team
 */
@Mapper
public interface ServiceItemMapper extends BaseMapper<ServiceItem> {
}

