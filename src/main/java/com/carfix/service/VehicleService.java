package com.carfix.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.carfix.entity.Vehicle;

import java.util.List;

/**
 * 车辆服务接口
 * 
 * @author CarFix Team
 */
public interface VehicleService extends IService<Vehicle> {

    /**
     * 获取用户的车辆列表
     */
    List<Vehicle> getUserVehicles(Long userId);

    /**
     * 添加车辆
     */
    boolean addVehicle(Vehicle vehicle);

    /**
     * 设置默认车辆
     */
    boolean setDefaultVehicle(Long userId, Long vehicleId);

    /**
     * 删除车辆
     */
    boolean deleteVehicle(Long vehicleId, Long userId);
}

