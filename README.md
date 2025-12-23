# 车辆养修服务管理平台

## 项目简介

基于微信小程序的车辆养修服务管理平台，采用 Java + Spring Boot + MyBatis-Plus + 微信小程序技术栈实现。该平台旨在优化车辆养修服务流程，提升车主养修体验和养修门店的运营管理效率。

## 技术栈

### 后端技术
- **Java 1.8**
- **Spring Boot 2.7.14** - 核心框架
- **MyBatis-Plus 3.5.3.1** - ORM框架
- **MySQL 8.0** - 数据库
- **Redis** - 缓存
- **Druid** - 数据库连接池
- **JWT** - 身份认证
- **WxJava** - 微信小程序SDK

### 前端技术
- **微信小程序原生开发**
- **WXML/WXSS/JavaScript**

## 核心功能

### 车主端功能
1. **微信登录** - 一键授权登录
2. **门店管理**
   - 门店列表浏览
   - 门店详情查看
   - 门店搜索筛选
   - 门店评分查看
3. **车辆管理**
   - 添加车辆信息
   - 管理多辆车辆
   - 设置默认车辆
4. **服务预约**
   - 浏览服务项目
   - 选择服务时间
   - 在线预约下单
5. **订单管理**
   - 查看订单列表
   - 订单详情查看
   - 订单状态跟踪
   - 取消订单
6. **评价系统**
   - 服务评价
   - 查看门店评价

### 门店端功能
1. **订单管理**
   - 订单接收确认
   - 分配技师
   - 订单状态更新
2. **服务管理**
   - 服务项目管理
   - 价格设置
3. **评价管理**
   - 查看用户评价
   - 回复评价

## 项目结构

```
carFix/
├── src/                           # 后端源码
│   └── main/
│       ├── java/
│       │   └── com/carfix/
│       │       ├── config/        # 配置类
│       │       ├── controller/    # 控制器层
│       │       ├── service/       # 服务层
│       │       ├── mapper/        # 数据访问层
│       │       ├── entity/        # 实体类
│       │       ├── common/        # 公共类
│       │       └── utils/         # 工具类
│       └── resources/
│           └── application.yml    # 配置文件
├── sql/                           # 数据库脚本
│   └── car_maintenance.sql       # 初始化脚本
├── miniapp/                       # 小程序源码
│   ├── pages/                     # 页面
│   │   ├── index/                # 首页
│   │   ├── store/                # 门店相关
│   │   ├── order/                # 订单相关
│   │   ├── vehicle/              # 车辆相关
│   │   └── user/                 # 用户中心
│   ├── utils/                     # 工具类
│   ├── app.js                     # 小程序入口
│   ├── app.json                   # 小程序配置
│   └── app.wxss                   # 全局样式
├── pom.xml                        # Maven配置
└── README.md                      # 项目说明
```

## 数据库设计

### 核心数据表
- `tb_user` - 用户表
- `tb_store` - 门店表
- `tb_vehicle` - 车辆表
- `tb_service_item` - 服务项目表
- `tb_order` - 订单表
- `tb_review` - 评价表

## 快速开始

### 环境要求
- JDK 1.8+
- Maven 3.6+
- MySQL 8.0+
- Redis 5.0+
- 微信开发者工具

### 后端部署

1. **克隆项目**
```bash
git clone <repository-url>
cd carFix
```

2. **创建数据库**
```bash
mysql -u root -p
source sql/car_maintenance.sql
```

3. **修改配置**
编辑 `src/main/resources/application.yml`，修改以下配置：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/car_maintenance
    username: your_username
    password: your_password
  redis:
    host: localhost
    port: 6379

wx:
  miniapp:
    appid: your_appid
    secret: your_secret
```

4. **编译运行**
```bash
mvn clean install
mvn spring-boot:run
```

后端服务将在 `http://localhost:8080/api` 启动

### 小程序部署

1. **配置小程序**
编辑 `miniapp/project.config.json`，修改 `appid` 为你的小程序AppID

2. **配置后端地址**
编辑 `miniapp/app.js`，修改 `baseUrl` 为你的后端地址：
```javascript
globalData: {
  baseUrl: 'http://your-backend-url/api'
}
```

3. **导入项目**
- 打开微信开发者工具
- 导入 `miniapp` 目录
- 编译预览

## API接口文档

### 用户相关
- `POST /user/login` - 微信登录
- `GET /user/info` - 获取用户信息
- `PUT /user/update` - 更新用户信息

### 门店相关
- `GET /store/list` - 获取门店列表
- `GET /store/detail/{id}` - 获取门店详情

### 车辆相关
- `GET /vehicle/list` - 获取车辆列表
- `POST /vehicle/add` - 添加车辆
- `PUT /vehicle/setDefault/{id}` - 设置默认车辆
- `DELETE /vehicle/delete/{id}` - 删除车辆

### 服务相关
- `GET /service/list` - 获取服务列表
- `GET /service/detail/{id}` - 获取服务详情

### 订单相关
- `POST /order/create` - 创建订单
- `GET /order/user/list` - 获取用户订单列表
- `GET /order/store/list` - 获取门店订单列表
- `GET /order/detail/{id}` - 获取订单详情
- `PUT /order/confirm/{id}` - 确认订单
- `PUT /order/start/{id}` - 开始服务
- `PUT /order/finish/{id}` - 完成服务
- `PUT /order/cancel/{id}` - 取消订单

### 评价相关
- `POST /review/create` - 创建评价
- `GET /review/list` - 获取评价列表
- `PUT /review/reply/{id}` - 回复评价

## 项目特色

1. **便捷预约** - 车主可以随时随地通过小程序预约养修服务
2. **信息透明** - 门店资质、服务价格、用户评价一目了然
3. **订单追踪** - 实时跟踪订单状态，了解服务进度
4. **评价体系** - 完善的评价系统，促进服务质量提升
5. **车辆管理** - 支持管理多辆车辆，方便快捷
6. **智能推荐** - 根据评分和距离推荐优质门店

## 系统优势

1. **提升效率** - 减少线下沟通成本，提高预约效率
2. **优化体验** - 简化预约流程，提升用户体验
3. **数据管理** - 订单数据电子化，便于管理和统计
4. **服务透明** - 价格公开透明，避免信息不对称
5. **质量保障** - 评价体系促进服务质量提升

## 注意事项

1. 使用前需要在微信公众平台注册小程序并获取AppID和AppSecret
2. 需要配置服务器域名白名单
3. 生产环境建议使用HTTPS协议
4. 建议定期备份数据库
5. 注意保护用户隐私信息

## 开发团队

CarFix Team

## 许可证

本项目仅供学习交流使用

## 联系方式

如有问题或建议，欢迎联系开发团队

---

**祝您使用愉快！**

