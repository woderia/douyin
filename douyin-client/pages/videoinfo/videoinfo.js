
const app = getApp()

Page({
  data: {
    cover: true
  },
  showSearch: function() {
    wx.navigateTo({
      url: '../searchVideo/searchVideo',
    })
  }
})