package com.carfix.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 服务项目实体类
 * 
 * @author CarFix Team
 */
@Data
@TableName("tb_service_item")
public class ServiceItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 服务项目ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属门店ID
     */
    private Long storeId;

    /**
     * 服务项目名称
     */
    private String itemName;

    /**
     * 服务分类：1-保养，2-维修，3-美容，4-改装，5-其他
     */
    private Integer category;

    /**
     * 服务描述
     */
    private String description;

    /**
     * 服务价格
     */
    private BigDecimal price;

    /**
     * 预计服务时长（分钟）
     */
    private Integer duration;

    /**
     * 服务图片
     */
    private String image;

    /**
     * 服务状态：0-上架，1-下架
     */
    private Integer status;

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

