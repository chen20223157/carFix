// pages/store/detail.js
const request = require('../../utils/request')

Page({
  data: {
    storeId: null,
    store: {},
    currentCategory: 0,
    categories: [
      { id: 1, name: '保养' },
      { id: 2, name: '维修' },
      { id: 3, name: '美容' },
      { id: 4, name: '改装' },
      { id: 5, name: '其他' }
    ],
    services: [],
    reviews: []
  },

  onLoad(options) {
    this.setData({
      storeId: options.id
    })
    this.loadStoreDetail()
    this.loadServices()
    this.loadReviews()
  },

  // 加载门店详情
  loadStoreDetail() {
    request.get(`/store/detail/${this.data.storeId}`).then(res => {
      this.setData({
        store: res
      })
    })
  },

  // 加载服务项目
  loadServices(category = null) {
    const params = {
      storeId: this.data.storeId,
      current: 1,
      size: 20
    }
    if (category) {
      params.category = category
    }

    request.get('/service/list', params).then(res => {
      this.setData({
        services: res.records
      })
    })
  },

  // 加载评价
  loadReviews() {
    request.get('/review/list', {
      storeId: this.data.storeId,
      current: 1,
      size: 3
    }).then(res => {
      this.setData({
        reviews: res.records
      })
    })
  },

  // 切换分类
  switchCategory(e) {
    const categoryId = parseInt(e.currentTarget.dataset.id)
    this.setData({
      currentCategory: categoryId
    })
    this.loadServices(categoryId === 0 ? null : categoryId)
  },

  // 选择服务
  selectService(e) {
    const serviceId = e.currentTarget.dataset.id
    wx.navigateTo({
      url: `/pages/order/create?storeId=${this.data.storeId}&serviceId=${serviceId}`
    })
  },

  // 拨打电话
  callPhone() {
    wx.makePhoneCall({
      phoneNumber: this.data.store.phone
    })
  },

  // 打开地图导航
  openLocation() {
    wx.openLocation({
      latitude: parseFloat(this.data.store.latitude),
      longitude: parseFloat(this.data.store.longitude),
      name: this.data.store.storeName,
      address: this.data.store.address
    })
  },

  // 前往评价列表
  goToReviewList() {
    wx.navigateTo({
      url: `/pages/review/list?storeId=${this.data.storeId}`
    })
  },

  // 前往预约页面
  goToBook() {
    if (this.data.services.length > 0) {
      wx.navigateTo({
        url: `/pages/order/create?storeId=${this.data.storeId}`
      })
    } else {
      wx.showToast({
        title: '暂无可预约服务',
        icon: 'none'
      })
    }
  }
})

