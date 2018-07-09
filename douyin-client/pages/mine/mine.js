const app = getApp()

Page({
  data: {
    // 默认头像
    faceUrl: "../resource/images/noneface.png"
  },
  onLoad: function () {
    // 加载个人信息
    var self = this
    var user = app.getGlobalUserInfo()
    var serverUrl = app.serverUrl
    wx.showLoading({
      title: '请等待...',
    })
    wx.request({
      url: serverUrl + '/user/query?userId=' + user.id,
      method: 'POST',
      header: {
        'content-type': 'application/json', // 默认值
        'headerUserId': user.id,
        'headerUserToken': user.userToken
      },
      success: function (res) {
        wx.hideLoading()  
        if (res.data.status == 200) {
          var userInfo = app.getGlobalUserInfo()
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
    var user = app.getGlobalUserInfo()
    var serverUrl = app.serverUrl;
    wx.showLoading({
      title: '请等待...',
    });
    // 注销
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
          app.removeGlobalUserInfo()
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
    var userInfo = app.getGlobalUserInfo()
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
          url: serverUrl + '/user/uploadFace?userId='+userInfo.id,
          filePath: tempFilePaths[0],
          name: 'file',
          header: {
            'content-type': 'application/json', // 默认值
            'headerUserId': userInfo.id,
            'headerUserToken': userInfo.userToken
          },
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
  },
  uploadVideo: function() {
    // 上传视频
    var self = this
    wx.chooseVideo({
      sourceType: ['album'],
      success: function(res) {
        console.log(res)
        var duration = res.duration
        var tmpWidth = res.width
        var tmpHeight = res.height
        var tmpVideoUrl = res.tempFilePath
        var tmpCoverUrl = res.humbTempFilePath
        // 时间视频是否太长
        if (duration > 11) {
          wx.showToast({
            title: '视频长度不能超过十秒..',
            icon: 'none',
            duration: 2500
          })
        } else if(duration < 1) {
          wx.showToast({
            title: '视频长度太短了..',
            icon: 'none',
            duration: 2500
          })
        }else {
          // 选择BGM，跳转页面，携带参数
          wx.navigateTo({
            url: '../chooseBgm/chooseBgm?duration=' + duration
            + "&tmpHeight=" + tmpHeight
            + "&tmpWidth=" + tmpWidth
            + "&tmpVideoUrl=" + tmpVideoUrl
            + "&tmpCoverUrl=" + tmpCoverUrl
          })
        }
      }
    })
  },
  goIndex: function() {
    wx.redirectTo({
      url: '../index/index',
    })
  } 
})
