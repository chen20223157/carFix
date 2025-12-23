// pages/user/index.js
const request = require('../../utils/request')
const app = getApp()

Page({
  data: {
    userInfo: {},
    orderCount: {
      pending: 0,
      confirmed: 0,
      processing: 0
    },
    vehicles: []
  },

  onLoad() {
    // 检查登录状态
    app.checkLogin(isLogin => {
      if (isLogin) {
        this.loadUserData()
      }
    })
  },

  onShow() {
    // 每次显示页面时刷新数据
    if (app.globalData.token) {
      this.loadUserData()
    }
  },

  // 加载用户数据
  loadUserData() {
    this.setData({
      userInfo: app.globalData.userInfo || {}
    })
    
    this.loadOrderCount()
    this.loadVehicles()
  },

  // 加载订单统计
  loadOrderCount() {
    // 待确认订单
    request.get('/order/user/list', {
      status: 0,
      current: 1,
      size: 1
    }).then(res => {
      this.setData({
        'orderCount.pending': res.total
      })
    })

    // 已确认订单
    request.get('/order/user/list', {
      status: 1,
      current: 1,
      size: 1
    }).then(res => {
      this.setData({
        'orderCount.confirmed': res.total
      })
    })

    // 服务中订单
    request.get('/order/user/list', {
      status: 2,
      current: 1,
      size: 1
    }).then(res => {
      this.setData({
        'orderCount.processing': res.total
      })
    })
  },

  // 加载车辆列表
  loadVehicles() {
    request.get('/vehicle/list').then(res => {
      this.setData({
        vehicles: res.slice(0, 3) // 只显示前3辆
      })
    })
  },

  // 前往用户信息页面
  goToUserInfo() {
    if (app.globalData.token) {
      wx.navigateTo({
        url: '/pages/user/info'
      })
    } else {
      app.wxLogin(() => {
        this.loadUserData()
      })
    }
  },

  // 前往订单列表
  goToOrderList(e) {
    const status = e.currentTarget.dataset.status
    const url = status !== undefined ? `/pages/order/list?status=${status}` : '/pages/order/list'
    wx.switchTab({
      url: '/pages/order/list'
    })
  },

  // 前往车辆列表
  goToVehicleList() {
    wx.navigateTo({
      url: '/pages/vehicle/list'
    })
  },

  // 前往添加车辆
  goToAddVehicle() {
    wx.navigateTo({
      url: '/pages/vehicle/add'
    })
  },

  // 前往关于我们
  goToAbout() {
    wx.showToast({
      title: '功能开发中',
      icon: 'none'
    })
  },

  // 前往帮助中心
  goToHelp() {
    wx.showToast({
      title: '功能开发中',
      icon: 'none'
    })
  },

  // 前往设置
  goToSettings() {
    wx.showToast({
      title: '功能开发中',
      icon: 'none'
    })
  },

  // 退出登录
  logout() {
    wx.showModal({
      title: '提示',
      content: '确定要退出登录吗？',
      success: res => {
        if (res.confirm) {
          app.logout()
        }
      }
    })
  }
})

