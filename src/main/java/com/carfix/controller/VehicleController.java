package com.carfix.controller;

import com.carfix.common.Result;
import com.carfix.entity.Vehicle;
import com.carfix.service.VehicleService;
import com.carfix.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 车辆控制器
 * 
 * @author CarFix Team
 */
@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    /**
     * 获取用户的车辆列表
     */
    @GetMapping("/list")
    public Result<List<Vehicle>> getUserVehicles(@RequestHeader("Authorization") String token) {
        Long userId = JwtUtil.getUserIdFromToken(token);
        List<Vehicle> vehicles = vehicleService.getUserVehicles(userId);
        return Result.success(vehicles);
    }

    /**
     * 添加车辆
     */
    @PostMapping("/add")
    public Result<String> addVehicle(
            @RequestHeader("Authorization") String token,
            @RequestBody Vehicle vehicle) {
        
        Long userId = JwtUtil.getUserIdFromToken(token);
        vehicle.setUserId(userId);
        
        boolean result = vehicleService.addVehicle(vehicle);
        return result ? Result.success("添加成功") : Result.error("添加失败");
    }

    /**
     * 设置默认车辆
     */
    @PutMapping("/setDefault/{vehicleId}")
    public Result<String> setDefaultVehicle(
            @RequestHeader("Authorization") String token,
            @PathVariable Long vehicleId) {
        
        Long userId = JwtUtil.getUserIdFromToken(token);
        boolean result = vehicleService.setDefaultVehicle(userId, vehicleId);
        
        return result ? Result.success("设置成功") : Result.error("设置失败");
    }

    /**
     * 删除车辆
     */
    @DeleteMapping("/delete/{vehicleId}")
    public Result<String> deleteVehicle(
            @RequestHeader("Authorization") String token,
            @PathVariable Long vehicleId) {
        
        Long userId = JwtUtil.getUserIdFromToken(token);
        boolean result = vehicleService.deleteVehicle(vehicleId, userId);
        
        return result ? Result.success("删除成功") : Result.error("删除失败");
    }
}

