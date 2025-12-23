// app.js
App({
  globalData: {
    userInfo: null,
    token: null,
    baseUrl: 'http://localhost:8080/api' // 后端API地址
  },

  onLaunch() {
    // 检查登录状态
    const token = wx.getStorageSync('token')
    if (token) {
      this.globalData.token = token
      this.getUserInfo()
    }
  },

  // 微信登录
  wxLogin(callback) {
    wx.login({
      success: res => {
        if (res.code) {
          // 发送 res.code 到后台换取 token
          wx.request({
            url: `${this.globalData.baseUrl}/user/login`,
            method: 'POST',
            data: {
              code: res.code
            },
            success: response => {
              if (response.data.code === 200) {
                const { token, userInfo } = response.data.data
                this.globalData.token = token
                this.globalData.userInfo = userInfo
                wx.setStorageSync('token', token)
                callback && callback(true)
              } else {
                wx.showToast({
                  title: '登录失败',
                  icon: 'none'
                })
                callback && callback(false)
              }
            },
            fail: () => {
              wx.showToast({
                title: '网络错误',
                icon: 'none'
              })
              callback && callback(false)
            }
          })
        }
      }
    })
  },

  // 获取用户信息
  getUserInfo() {
    wx.request({
      url: `${this.globalData.baseUrl}/user/info`,
      method: 'GET',
      header: {
        'Authorization': this.globalData.token
      },
      success: res => {
        if (res.data.code === 200) {
          this.globalData.userInfo = res.data.data
        }
      }
    })
  },

  // 检查登录状态
  checkLogin(callback) {
    if (this.globalData.token) {
      callback && callback(true)
    } else {
      this.wxLogin(callback)
    }
  },

  // 退出登录
  logout() {
    this.globalData.token = null
    this.globalData.userInfo = null
    wx.removeStorageSync('token')
    wx.reLaunch({
      url: '/pages/index/index'
    })
  }
})

