const app = getApp()

Page({
  data: {

  },
  doLogin: function(e) {
    var formObject = e.detail.value
    var username = formObject.username
    var password = formObject.password
    if (username.length == 0 || password.length == 0) {
      wx.showToast({
        title: '用户名或密码不能为空',
        icon: 'none',
        duration: 3000
      })
    } else {
      var serverUrl = app.serverUrl
      // loading..
      wx.showLoading({
        title: '请等待...',
      })
      // ajax
      wx.request({
        url: serverUrl + '/login',
        method: "POST",
        data: {
          username,
          password
        },
        header: {
          'content-type': 'application/json' // 默认值
        },
        success: function(res) {
          console.log(res)
          if (res.data.status == 200) {
            // close loading..
            wx.hideLoading()
            // 登录成功
            wx.showToast({
              title: '登录成功',
              icon: 'none',
              duration: 3000
            })
            app.userInfo = res.data.data
          } else if (res.data.status = 500 ) {
            // 失败
            wx.showToast({
              title: '检查用户名与密码',
              icon: 'none',
              duration: 3000
            })
          }
        }
      })
    }
  },
  goRegistPage: function(e) {
    // 跳转页面...
    wx.navigateTo({
      url: '../userRegist/regist',
    })
  }
})