const app = getApp()

Page({
  data: {
    // 用于分页的属性  
    totalPage: 1,
    page: 1,
    videoList: [],
    screenWidth: 350,
    serverUrl: ""
  },
  onLoad: function(param) {
    var self = this
    // 获得屏幕的宽度,同步方式
    var screenWidth = wx.getSystemInfoSync().screenWidth
    self.setData({
      screenWidth: screenWidth 
    })
    // 当前页
    var page = self.data.page
    self.getAllVideoList(page)
  },
  getAllVideoList: function(page) {
    var self = this
    var serverUrl = app.serverUrl
    wx.showLoading({
      title: '请等待,加载中..',
    })
    wx.request({
      url: serverUrl + '/video/showAll?page=' + page,
      method: "POST",
      success: function (res) {
        // 停止加载图
        wx.hideLoading()
        wx.hideNavigationBarLoading()
        wx.stopPullDownRefresh()
        console.log(res)
        if (page == 1) {
          self.setData({
            videoList: []
          })
        }
        var videoList = res.data.data.rows
        var newVideoList = self.data.videoList
        self.setData({
          videoList: newVideoList.concat(videoList),
          page: page,
          totalPage: res.data.data.total,
          serverUrl: serverUrl
        })
      }
    })
  },
  onReachBottom: function() {
    // 上拉刷新
    var self = this
    var currentPage = self.data.page
    var totalPage = self.data.totalPage
    // 当前页数是否是总页数,是则无需查询
    if (currentPage === totalPage) {
      wx.showToast({
        title: '已经没有视频了...',
        icon: "none"
      })
      return
    }
    var page = currentPage + 1
    self.getAllVideoList(page)
  },
  onPullDownRefresh: function() {
    // 下拉刷新整个页面,json文件中配置"enablePullDownRefresh": true
    wx.showNavigationBarLoading()
    this.getAllVideoList(1)
  },
  showVideoInfo: function(e) {
    // 跳转到视频详情页
    var self = this
    var videoList = self.data.videoList
    var arrindex = e.target.dataset.arrindex
    // 取得视频信息
    var videoInfo = JSON.stringify(videoList[arrindex])
    wx.redirectTo({
      url: '../videoinfo/videoinfo?videoInfo=' + videoInfo,
    })
  }
})
