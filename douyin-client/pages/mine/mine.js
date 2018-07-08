const app = getApp()

Page({
  data: {
    // 默认头像
    faceUrl: "../resource/images/noneface.png"
  },
  onLoad: function () {
    var self = this
    var user = app.userInfo
    var serverUrl = app.serverUrl
    wx.showLoading({
      title: '请等待...',
    })
    wx.request({
      url: serverUrl + '/user/query?userId=' + user.id,
      method: 'POST',
      success: function (res) {
        wx.hideLoading()  
        if (res.data.status == 200) {
            var userInfo = res.data.data
            var faceUrl = "../resource/images/noneface.png" 
            if (userInfo.faceImage != null && userInfo.faceImage != '' && userInfo.faceImage!=undefined) {
              faceUrl = serverUrl + userInfo.faceImage
            }
            self.setData({
              faceUrl: faceUrl,
              fansCounts: userInfo.fansCounts,
              followCounts: userInfo.followCounts,
              receiveLikeCounts: userInfo.receiveLikeCounts
            })
        }else if (res.data.status == 500) {

        }
      }
    })
  },
  logout: function(param) {
    var user = app.userInfo
    var serverUrl = app.serverUrl;
    wx.showLoading({
      title: '请等待...',
    });
    // 调用后端
    wx.request({
      url: serverUrl + '/logout?userId=' + user.id,
      method: "POST",
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        console.log(res.data);
        wx.hideLoading();
        if (res.data.status == 200) {
          wx.showToast({
            title: '注销成功',
            icon: 'success',
            duration: 2000
          });
          // 清除内容
          app.userInfo = null
          // 页面跳转
          wx.redirectTo({
            url: '../userLogin/login',
          })
        }
      }
    })
  },
  changeFace: function () {
    var self = this
    // 找本地相册
    wx.chooseImage({
      count: 1,
      sizeType: ['compressed'],
      sourceType: ['album'],
      success: function (res) {
         // 成功获取文件
        var tempFilePaths = res.tempFilePaths       
        wx.showLoading({
          title: '上传中...',
        })
        var serverUrl = app.serverUrl
        wx.uploadFile({
          url: serverUrl + '/user/uploadFace?userId='+app.userInfo.id,
          filePath: tempFilePaths[0],
          name: 'file',
          success: function(res) {
            // 返回的时字符串而不是json
            var data = JSON.parse(res.data)
            wx.hideLoading()
            console.log(data)
            if (data.status == 200) {
              wx.showToast({
                title: '上传成功!~~',
                icon: "success"
              })
              var imageUrl = data.data
              self.setData({
                // tomcat配置的静态资源路径 + 图片路径
                faceUrl: serverUrl + imageUrl
              })
            } else if (data.status == 500) {
              wx.showToast({
                title: data.msg
              })
            } 
          }
        })
      },
    })
  }
})
