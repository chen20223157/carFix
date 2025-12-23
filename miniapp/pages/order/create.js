// pages/order/create.js
const request = require('../../utils/request')
const app = getApp()

Page({
  data: {
    storeId: null,
    serviceId: null,
    today: '',
    appointmentDate: '',
    appointmentTime: '',
    remark: '',
    selectedVehicle: null,
    selectedService: null,
    vehicles: [],
    services: [],
    showVehicleModal: false,
    showServiceModal: false
  },

  onLoad(options) {
    // 获取今天的日期
    const today = new Date()
    const dateStr = this.formatDate(today)
    
    this.setData({
      storeId: options.storeId,
      serviceId: options.serviceId,
      today: dateStr
    })

    // 检查登录状态
    app.checkLogin(isLogin => {
      if (isLogin) {
        this.loadVehicles()
        this.loadServices()
        
        // 如果传入了服务ID，自动选择该服务
        if (this.data.serviceId) {
          this.loadServiceDetail()
        }
      }
    })
  },

  // 加载车辆列表
  loadVehicles() {
    request.get('/vehicle/list').then(res => {
      this.setData({
        vehicles: res
      })
      // 自动选择默认车辆
      const defaultVehicle = res.find(v => v.isDefault === 1)
      if (defaultVehicle) {
        this.setData({
          selectedVehicle: defaultVehicle
        })
      }
    })
  },

  // 加载服务列表
  loadServices() {
    request.get('/service/list', {
      storeId: this.data.storeId,
      current: 1,
      size: 100
    }).then(res => {
      this.setData({
        services: res.records
      })
    })
  },

  // 加载服务详情
  loadServiceDetail() {
    request.get(`/service/detail/${this.data.serviceId}`).then(res => {
      this.setData({
        selectedService: res
      })
    })
  },

  // 选择车辆
  selectVehicle() {
    this.setData({
      showVehicleModal: true
    })
  },

  // 选择服务
  selectService() {
    this.setData({
      showServiceModal: true
    })
  },

  // 车辆选择确认
  onVehicleSelect(e) {
    const vehicle = e.currentTarget.dataset.vehicle
    this.setData({
      selectedVehicle: vehicle,
      showVehicleModal: false
    })
  },

  // 服务选择确认
  onServiceSelect(e) {
    const service = e.currentTarget.dataset.service
    this.setData({
      selectedService: service,
      showServiceModal: false
    })
  },

  // 关闭车辆弹窗
  closeVehicleModal() {
    this.setData({
      showVehicleModal: false
    })
  },

  // 关闭服务弹窗
  closeServiceModal() {
    this.setData({
      showServiceModal: false
    })
  },

  // 阻止事件冒泡
  stopPropagation() {},

  // 前往添加车辆页面
  goToAddVehicle() {
    wx.navigateTo({
      url: '/pages/vehicle/add'
    })
  },

  // 日期选择
  onDateChange(e) {
    this.setData({
      appointmentDate: e.detail.value
    })
  },

  // 时间选择
  onTimeChange(e) {
    this.setData({
      appointmentTime: e.detail.value
    })
  },

  // 备注输入
  onRemarkInput(e) {
    this.setData({
      remark: e.detail.value
    })
  },

  // 提交订单
  submitOrder() {
    // 验证必填项
    if (!this.data.selectedVehicle) {
      wx.showToast({
        title: '请选择车辆',
        icon: 'none'
      })
      return
    }

    if (!this.data.selectedService) {
      wx.showToast({
        title: '请选择服务项目',
        icon: 'none'
      })
      return
    }

    if (!this.data.appointmentDate || !this.data.appointmentTime) {
      wx.showToast({
        title: '请选择预约时间',
        icon: 'none'
      })
      return
    }

    // 组装预约时间
    const appointmentTime = `${this.data.appointmentDate} ${this.data.appointmentTime}:00`

    // 提交订单
    wx.showLoading({ title: '提交中...' })
    
    request.post('/order/create', {
      vehicleId: this.data.selectedVehicle.id,
      storeId: this.data.storeId,
      serviceItemId: this.data.selectedService.id,
      appointmentTime: appointmentTime,
      remark: this.data.remark
    }).then(res => {
      wx.hideLoading()
      wx.showToast({
        title: '预约成功',
        icon: 'success'
      })
      
      setTimeout(() => {
        wx.redirectTo({
          url: `/pages/order/detail?id=${res.id}`
        })
      }, 1500)
    }).catch(() => {
      wx.hideLoading()
    })
  },

  // 格式化日期
  formatDate(date) {
    const year = date.getFullYear()
    const month = String(date.getMonth() + 1).padStart(2, '0')
    const day = String(date.getDate()).padStart(2, '0')
    return `${year}-${month}-${day}`
  }
})

