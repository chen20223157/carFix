package com.carfix.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.carfix.entity.Vehicle;
import com.carfix.mapper.VehicleMapper;
import com.carfix.service.VehicleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 车辆服务实现类
 * 
 * @author CarFix Team
 */
@Service
public class VehicleServiceImpl extends ServiceImpl<VehicleMapper, Vehicle> implements VehicleService {

    @Override
    public List<Vehicle> getUserVehicles(Long userId) {
        LambdaQueryWrapper<Vehicle> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Vehicle::getUserId, userId);
        queryWrapper.orderByDesc(Vehicle::getIsDefault);
        queryWrapper.orderByDesc(Vehicle::getCreateTime);
        return this.list(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addVehicle(Vehicle vehicle) {
        // 如果是第一辆车，自动设为默认
        LambdaQueryWrapper<Vehicle> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Vehicle::getUserId, vehicle.getUserId());
        long count = this.count(queryWrapper);
        
        if (count == 0) {
            vehicle.setIsDefault(1);
        } else if (vehicle.getIsDefault() != null && vehicle.getIsDefault() == 1) {
            // 如果设置为默认，需要将其他车辆的默认状态取消
            LambdaUpdateWrapper<Vehicle> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Vehicle::getUserId, vehicle.getUserId());
            updateWrapper.set(Vehicle::getIsDefault, 0);
            this.update(updateWrapper);
        }
        
        return this.save(vehicle);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setDefaultVehicle(Long userId, Long vehicleId) {
        // 取消其他车辆的默认状态
        LambdaUpdateWrapper<Vehicle> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Vehicle::getUserId, userId);
        updateWrapper.set(Vehicle::getIsDefault, 0);
        this.update(updateWrapper);
        
        // 设置当前车辆为默认
        Vehicle vehicle = this.getById(vehicleId);
        if (vehicle != null && vehicle.getUserId().equals(userId)) {
            vehicle.setIsDefault(1);
            return this.updateById(vehicle);
        }
        
        return false;
    }

    @Override
    public boolean deleteVehicle(Long vehicleId, Long userId) {
        Vehicle vehicle = this.getById(vehicleId);
        if (vehicle != null && vehicle.getUserId().equals(userId)) {
            return this.removeById(vehicleId);
        }
        return false;
    }
}

