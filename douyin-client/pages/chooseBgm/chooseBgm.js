const app = getApp()

Page({
    data: {
      bgmList: [],
      serverUrl: "",
      videoParams: {}
    },
    onLoad: function(params) {
      // 接收由其他页面跳转而来的参数
      console.log(params)
      this.setData({
        videoParams: params
      })
      // 加载音乐列表
      var self = this
      wx.showLoading({
        title: '请等待...',
      });
      var serverUrl = app.serverUrl;
      // 调用后端
      wx.request({
        url: serverUrl + '/bgm/list',
        method: "POST",
        header: {
          'content-type': 'application/json', // 默认值
        },
        success: function (res) {
          console.log(res.data);
          wx.hideLoading();
          if (res.data.status == 200) {
            var bgmList = res.data.data
            self.setData({
              bgmList: bgmList,
              serverUrl: serverUrl
            })
            console.log(self.data.bgmList)
          } else if (res.data.status == 502) {
            
          }
        }
      })
    },

    upload: function(e) {
      // 获取表单数据
      var self = this
      var userInfo = app.getGlobalUserInfo()
      var serverUrl = app.serverUrl
      var bgmId = e.detail.value.bgmId
      var desc = e.detail.value.desc
      var duration = self.data.videoParams.duration
      var tmpHeight = self.data.videoParams.tmpHeight
      var tmpWidth = self.data.videoParams.tmpWidth
      var tmpVideoUrl = self.data.videoParams.tmpVideoUrl
      var tmpCoverUrl = self.data.videoParams.tmpCoverUrl
      wx.uploadFile({
        url: serverUrl + '/video/upload',
        formData: {
          userId: userInfo.id,
          bgmId: bgmId,
          desc: desc,
          videoSeconds: duration,
          videoHeight: tmpHeight,
          videoWidth: tmpWidth
        },
        filePath: tmpVideoUrl,
        name: 'file',
        header: {
          'headerUserId': userInfo.id,
          'headerUserToken': userInfo.userToken
        },
        success: function (res) {
          // 返回的时字符串而不是json
          var data = JSON.parse(res.data)
          wx.hideLoading()
          console.log(data)
          if (data.status == 200) {
            wx.showToast({
              title: '上传成功!~~',
              icon: "success"
            });
            // 上传成功后跳回之前的页面
            wx.navigateBack({
              delta: 1
            })   
          } else if (data.status == 500) {
            wx.showToast({
              title: data.msg
            })
          }
        }
      })
    }
})

