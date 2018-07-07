package root.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import root.bean.JsonData;
import root.model.User;
import root.service.UserService;
import root.utils.MD5Util;

@RestController
@Api(value="用户登录注册的接口", tags={"注册和登录的controller"})
public class RegistLoginController {

	@Resource
	private UserService userService;
	
	@ApiOperation(value="用户注册", notes="用户注册的接口")
	@PostMapping("/regist")
	public JsonData regist(@RequestBody User user) {
		if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
			return JsonData.errorMsg("用户名和密码不允许为空");
		}
		if (!userService.queryUsernameIsExist(user.getUsername())) {
			user.setNickname(user.getUsername());
			user.setPassword(MD5Util.encrypt(user.getPassword()));
			user.setFansCounts(0);
			user.setReceiveLikeCounts(0);
			user.setFollowCounts(0);
			userService.saveUser(user);
		} else {
			return JsonData.errorMsg("用户名已存在");
		}
		return JsonData.ok();
	}
}
