// pages/index/index.js
const request = require('../../utils/request')

Page({
  data: {
    banners: [
      '/images/banner1.jpg',
      '/images/banner2.jpg',
      '/images/banner3.jpg'
    ],
    categories: [
      { id: 1, name: '保养', icon: '/images/category1.png' },
      { id: 2, name: '维修', icon: '/images/category2.png' },
      { id: 3, name: '美容', icon: '/images/category3.png' },
      { id: 4, name: '改装', icon: '/images/category4.png' },
      { id: 5, name: '其他', icon: '/images/category5.png' }
    ],
    stores: []
  },

  onLoad() {
    this.loadStores()
  },

  onShow() {
    // 每次显示页面时检查登录状态
    const app = getApp()
    if (!app.globalData.token) {
      app.wxLogin()
    }
  },

  // 加载推荐门店
  loadStores() {
    wx.showLoading({ title: '加载中...' })
    
    request.get('/store/list', {
      current: 1,
      size: 5
    }).then(res => {
      this.setData({
        stores: res.records
      })
    }).finally(() => {
      wx.hideLoading()
    })
  },

  // 搜索
  goToSearch() {
    wx.navigateTo({
      url: '/pages/store/list'
    })
  },

  // 选择分类
  selectCategory(e) {
    const categoryId = e.currentTarget.dataset.id
    wx.navigateTo({
      url: `/pages/store/list?category=${categoryId}`
    })
  },

  // 前往门店列表
  goToStoreList() {
    wx.navigateTo({
      url: '/pages/store/list'
    })
  },

  // 前往门店详情
  goToStoreDetail(e) {
    const storeId = e.currentTarget.dataset.id
    wx.navigateTo({
      url: `/pages/store/detail?id=${storeId}`
    })
  }
})

