const app = getApp()

Page({
    data: {

    },
    doRegist: function(e) {
      // 表单数据
      var formObject = e.detail.value
      var username = formObject.username
      var password = formObject.password
       if (username.length == 0 || password.length == 0) {
          // alert
          wx.showToast({
            title: '用户名或密码不能为空',
            icon: 'none',
            duration: 3000
          })
       } else {
         // 服务地址
         var serverUrl = app.serverUrl
         // loading..
         wx.showLoading({
           title: '请等待...',
         })
         // ajax
         wx.request({
           url: serverUrl + '/regist',
           method: "POST",
           data:{
             username,
             password
           },
           header: {
             'Content-Type': 'application/json' // 默认值
           },
           success: function(res) {
              console.log(res)
              var status  = res.data.status
              if (status == 200) {
                // close loading..
                wx.hideLoading()
                // alert
                wx.showToast({
                  title: '用户注册成功',
                  icon: 'none',
                  duration: 3000
                }),
                app.userInfo = res.data.data
              } else if(status == 500) {
                // alert
                wx.showToast({
                  title: res.data.msg,
                  icon: 'none',
                  duration: 3000
                })
              }
           }
         })
       }
    },
    goLoginPage: function(e) {
      // 跳转页面...
      wx.navigateTo({
        url: '../userLogin/login',
      })
    }
})