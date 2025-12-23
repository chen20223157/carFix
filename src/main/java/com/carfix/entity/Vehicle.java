package com.carfix.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 车辆实体类
 * 
 * @author CarFix Team
 */
@Data
@TableName("tb_vehicle")
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 车辆ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 车主用户ID
     */
    private Long userId;

    /**
     * 车牌号
     */
    private String plateNumber;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 车型
     */
    private String model;

    /**
     * 颜色
     */
    private String color;

    /**
     * VIN码（车架号）
     */
    private String vin;

    /**
     * 购车日期
     */
    private LocalDateTime purchaseDate;

    /**
     * 当前里程数（公里）
     */
    private Integer mileage;

    /**
     * 是否默认车辆：0-否，1-是
     */
    private Integer isDefault;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 逻辑删除：0-未删除，1-已删除
     */
    @TableLogic
    private Integer deleted;
}

