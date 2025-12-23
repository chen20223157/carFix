-- 车辆养修服务管理平台数据库初始化脚本

-- 创建数据库
CREATE DATABASE IF NOT EXISTS car_maintenance DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE car_maintenance;

-- 用户表
CREATE TABLE `tb_user` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `openid` VARCHAR(100) NOT NULL COMMENT '微信openid',
  `nickname` VARCHAR(50) DEFAULT NULL COMMENT '微信昵称',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
  `real_name` VARCHAR(50) DEFAULT NULL COMMENT '真实姓名',
  `user_type` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '用户类型：0-车主，1-技师，2-门店管理员',
  `store_id` BIGINT(20) DEFAULT NULL COMMENT '所属门店ID',
  `status` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '账号状态：0-正常，1-禁用',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_openid` (`openid`),
  KEY `idx_store_id` (`store_id`),
  KEY `idx_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 门店表
CREATE TABLE `tb_store` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '门店ID',
  `store_name` VARCHAR(100) NOT NULL COMMENT '门店名称',
  `logo` VARCHAR(255) DEFAULT NULL COMMENT '门店logo',
  `description` TEXT COMMENT '门店简介',
  `phone` VARCHAR(20) NOT NULL COMMENT '联系电话',
  `province` VARCHAR(50) NOT NULL COMMENT '省份',
  `city` VARCHAR(50) NOT NULL COMMENT '城市',
  `district` VARCHAR(50) NOT NULL COMMENT '区县',
  `address` VARCHAR(255) NOT NULL COMMENT '详细地址',
  `longitude` DECIMAL(10, 7) DEFAULT NULL COMMENT '经度',
  `latitude` DECIMAL(10, 7) DEFAULT NULL COMMENT '纬度',
  `business_hours` VARCHAR(100) DEFAULT NULL COMMENT '营业时间',
  `qualification_images` TEXT COMMENT '门店资质图片',
  `rating` DECIMAL(3, 2) DEFAULT 5.00 COMMENT '门店评分',
  `review_count` INT(11) DEFAULT 0 COMMENT '评价数量',
  `status` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '门店状态：0-正常营业，1-暂停营业，2-已关闭',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_city` (`city`),
  KEY `idx_rating` (`rating`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='门店表';

-- 车辆表
CREATE TABLE `tb_vehicle` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '车辆ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '车主用户ID',
  `plate_number` VARCHAR(20) NOT NULL COMMENT '车牌号',
  `brand` VARCHAR(50) NOT NULL COMMENT '品牌',
  `model` VARCHAR(100) NOT NULL COMMENT '车型',
  `color` VARCHAR(20) DEFAULT NULL COMMENT '颜色',
  `vin` VARCHAR(50) DEFAULT NULL COMMENT 'VIN码',
  `purchase_date` DATETIME DEFAULT NULL COMMENT '购车日期',
  `mileage` INT(11) DEFAULT 0 COMMENT '当前里程数',
  `is_default` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否默认车辆：0-否，1-是',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_plate_number` (`plate_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车辆表';

-- 服务项目表
CREATE TABLE `tb_service_item` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '服务项目ID',
  `store_id` BIGINT(20) NOT NULL COMMENT '所属门店ID',
  `item_name` VARCHAR(100) NOT NULL COMMENT '服务项目名称',
  `category` TINYINT(1) NOT NULL COMMENT '服务分类：1-保养，2-维修，3-美容，4-改装，5-其他',
  `description` TEXT COMMENT '服务描述',
  `price` DECIMAL(10, 2) NOT NULL COMMENT '服务价格',
  `duration` INT(11) DEFAULT 60 COMMENT '预计服务时长（分钟）',
  `image` VARCHAR(255) DEFAULT NULL COMMENT '服务图片',
  `status` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '服务状态：0-上架，1-下架',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_store_id` (`store_id`),
  KEY `idx_category` (`category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务项目表';

-- 订单表
CREATE TABLE `tb_order` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_no` VARCHAR(50) NOT NULL COMMENT '订单编号',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `vehicle_id` BIGINT(20) NOT NULL COMMENT '车辆ID',
  `store_id` BIGINT(20) NOT NULL COMMENT '门店ID',
  `service_item_id` BIGINT(20) NOT NULL COMMENT '服务项目ID',
  `appointment_time` DATETIME NOT NULL COMMENT '预约时间',
  `amount` DECIMAL(10, 2) NOT NULL COMMENT '订单金额',
  `actual_amount` DECIMAL(10, 2) DEFAULT NULL COMMENT '实付金额',
  `status` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '订单状态：0-待确认，1-已确认，2-服务中，3-已完成，4-已取消，5-已评价',
  `technician_id` BIGINT(20) DEFAULT NULL COMMENT '技师ID',
  `start_time` DATETIME DEFAULT NULL COMMENT '开始服务时间',
  `finish_time` DATETIME DEFAULT NULL COMMENT '完成服务时间',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '车主备注',
  `cancel_reason` VARCHAR(500) DEFAULT NULL COMMENT '取消原因',
  `pay_status` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '支付状态：0-未支付，1-已支付，2-已退款',
  `pay_time` DATETIME DEFAULT NULL COMMENT '支付时间',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_store_id` (`store_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 评价表
CREATE TABLE `tb_review` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '评价ID',
  `order_id` BIGINT(20) NOT NULL COMMENT '订单ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `store_id` BIGINT(20) NOT NULL COMMENT '门店ID',
  `rating` TINYINT(1) NOT NULL COMMENT '服务评分（1-5星）',
  `content` TEXT COMMENT '评价内容',
  `images` TEXT COMMENT '评价图片',
  `reply` TEXT COMMENT '门店回复',
  `reply_time` DATETIME DEFAULT NULL COMMENT '回复时间',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_id` (`order_id`),
  KEY `idx_store_id` (`store_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评价表';

-- 插入测试数据

-- 插入门店数据
INSERT INTO `tb_store` (`store_name`, `logo`, `description`, `phone`, `province`, `city`, `district`, `address`, `longitude`, `latitude`, `business_hours`, `rating`, `review_count`, `status`) VALUES
('车之家汽车服务中心', 'https://example.com/logo1.jpg', '专业汽车保养维修，20年老店，值得信赖', '13800138001', '广东省', '广州市', '天河区', '天河路123号', 113.324520, 23.146760, '08:00-20:00', 4.8, 156, 0),
('速修汽车养护店', 'https://example.com/logo2.jpg', '快速保养，专业维修，价格实惠', '13800138002', '广东省', '广州市', '越秀区', '中山路456号', 113.264530, 23.129110, '09:00-21:00', 4.6, 89, 0),
('豪车专修中心', 'https://example.com/logo3.jpg', '专注高端品牌汽车维修保养', '13800138003', '广东省', '深圳市', '南山区', '科技园南路789号', 113.953720, 22.537680, '08:30-19:30', 4.9, 234, 0);

-- 插入用户数据（车主）
INSERT INTO `tb_user` (`openid`, `nickname`, `avatar`, `phone`, `real_name`, `user_type`, `status`) VALUES
('wx_openid_001', '张三', 'https://example.com/avatar1.jpg', '13900139001', '张三', 0, 0),
('wx_openid_002', '李四', 'https://example.com/avatar2.jpg', '13900139002', '李四', 0, 0);

-- 插入技师和管理员数据
INSERT INTO `tb_user` (`openid`, `nickname`, `avatar`, `phone`, `real_name`, `user_type`, `store_id`, `status`) VALUES
('wx_openid_tech_001', '王师傅', 'https://example.com/tech1.jpg', '13700137001', '王建国', 1, 1, 0),
('wx_openid_admin_001', '陈经理', 'https://example.com/admin1.jpg', '13700137002', '陈明', 2, 1, 0);

-- 插入服务项目数据
INSERT INTO `tb_service_item` (`store_id`, `item_name`, `category`, `description`, `price`, `duration`, `status`) VALUES
(1, '常规保养套餐', 1, '更换机油机滤，检查车辆基础项目', 299.00, 60, 0),
(1, '深度保养套餐', 1, '更换机油三滤，全车检查，清洗节气门', 599.00, 120, 0),
(1, '刹车系统维修', 2, '更换刹车片，检查刹车系统', 450.00, 90, 0),
(1, '全车精洗', 3, '外观清洗+内饰清洁+打蜡', 188.00, 90, 0),
(2, '机油保养', 1, '更换全合成机油+机滤', 350.00, 45, 0),
(2, '轮胎更换', 2, '更换四条轮胎+动平衡', 1200.00, 120, 0),
(3, '豪车专属保养', 1, '原厂机油保养+全车检测', 1500.00, 150, 0);

