package com.carfix.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体类
 * 
 * @author CarFix Team
 */
@Data
@TableName("tb_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 车辆ID
     */
    private Long vehicleId;

    /**
     * 门店ID
     */
    private Long storeId;

    /**
     * 服务项目ID
     */
    private Long serviceItemId;

    /**
     * 预约时间
     */
    private LocalDateTime appointmentTime;

    /**
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 实付金额
     */
    private BigDecimal actualAmount;

    /**
     * 订单状态：0-待确认，1-已确认，2-服务中，3-已完成，4-已取消，5-已评价
     */
    private Integer status;

    /**
     * 技师ID
     */
    private Long technicianId;

    /**
     * 开始服务时间
     */
    private LocalDateTime startTime;

    /**
     * 完成服务时间
     */
    private LocalDateTime finishTime;

    /**
     * 车主备注
     */
    private String remark;

    /**
     * 取消原因
     */
    private String cancelReason;

    /**
     * 支付状态：0-未支付，1-已支付，2-已退款
     */
    private Integer payStatus;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

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

