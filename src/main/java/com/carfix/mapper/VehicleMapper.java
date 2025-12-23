package com.carfix.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carfix.entity.Vehicle;
import org.apache.ibatis.annotations.Mapper;

/**
 * 车辆Mapper接口
 * 
 * @author CarFix Team
 */
@Mapper
public interface VehicleMapper extends BaseMapper<Vehicle> {
}

