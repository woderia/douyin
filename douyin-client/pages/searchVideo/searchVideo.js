
var WxSearch = require('../../wxSearchView/wxSearchView.js');
const app = getApp()

Page({
  data: {

  },
  onLoad: function() {
    var self = this
    // 查询热搜词
    var serverUrl = app.serverUrl
    wx.request({
      url: serverUrl + '/video/hot',
      method: "POST",
      success:function(res) {
        var hotList = res.data.data
        WxSearch.init(
          self, // 本页面的一个引用
          hotList, // 热门搜索
          hotList,// 搜索匹配
          self.mySearchFunction,  // 搜索后回调函数
          self.myGobackFunction   // 返回本页面回调函数
        )
      }
    })
  },
  wxSearchInput: WxSearch.wxSearchInput,
  wxSearchKeyTap: WxSearch.wxSearchKeyTap,
  wxSearchDeleteAll: WxSearch.wxSearchDeleteAll,
  wxSearchConfirm: WxSearch.wxSearchConfirm,
  wxSearchClear: WxSearch.wxSearchClear,
  mySearchFunction: function(value) {
    wx.redirectTo({
      // 搜索后跳转到主页,可以额外创建一个搜索结果
      url: '../index/index?isSaveRecord=1&search=' + value
    })
  },
  myGobackFunction: function () {
    // 示例：返回本页面后跳转到主页
    wx.redirectTo({
      url: '../index/index'
    })
  }
})