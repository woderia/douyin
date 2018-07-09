var videoUtil = require('../../utils/videoUtil.js')
const app = getApp()

Page({
  data: {
    cover: "cover",
    videoId: "",
    src: "",
    videoInfo: {},
  },
  videoCtx: {},
  onLoad: function (params) {
    this.videoCtx = wx.createVideoContext("myVideo", this);
    var serverUrl = app.serverUrl
    var videoInfo = JSON.parse(params.videoInfo);

    this.setData({
      videoId: videoInfo.id,
      serverUrl,
      src: serverUrl + videoInfo.videoPath,
      videoInfo: videoInfo
    })
  },
  showSearch: function(params) {
    wx.navigateTo({
      url: '../searchVideo/searchVideo',
    })
  },
  onShow: function () {
    this.videoCtx.play()
  },

  onHide: function () {
    var me = this;
    this.videoCtx.pause();
  },
  showIndex: function() {
    wx.redirectTo({
      url: '../index/index',
    })    
  },
  showMine: function() {
    var user = app.getGlobalUserInfo()
    if (!user) {
      wx.navigateTo({
        url: '../userLogin/login',
      })
    } else {
      wx.redirectTo({
        url: '../mine/mine',
      })
    }
  },
  upload: function() {
    var user = app.getGlobalUserInfo()
    if (!user) {
      wx.navigateTo({
        url: '../userLogin/login',
      })
    } else {
      videoUtil.uploadVideo()
    }
  }
})