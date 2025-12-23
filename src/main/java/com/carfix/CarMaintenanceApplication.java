package com.carfix;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 车辆养修服务管理平台启动类
 * 
 * @author CarFix Team
 * @since 2024
 */
@SpringBootApplication
@MapperScan("com.carfix.mapper")
public class CarMaintenanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarMaintenanceApplication.class, args);
        System.out.println("===========================================");
        System.out.println("车辆养修服务管理平台启动成功！");
        System.out.println("===========================================");
    }
}

