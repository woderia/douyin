App({
  serverUrl: "http://192.168.31.28:8090",
  userInfo: null,
  setGlobalUserInfo: function(user) {
    wx.setStorageSync("userInfo", user)
  },
  getGlobalUserInfo: function() {
    return wx.getStorageSync("userInfo")
  },
  removeGlobalUserInfo: function() {
    return wx.removeStorageSync("userInfo")
  }
})