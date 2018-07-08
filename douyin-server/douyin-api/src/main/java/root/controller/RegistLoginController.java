package root.controller;

import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import root.bean.JsonData;
import root.dto.UserDto;
import root.model.User;
import root.service.UserService;
import root.utils.MD5Util;

@RestController
@Api(value = "用户登录注册的接口", tags = { "注册和登录的controller" })
public class RegistLoginController extends BasicController {

	@Resource
	private UserService userService;

	@ApiOperation(value = "用户注册", notes = "用户注册的接口")
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
		user.setPassword("");
		UserDto dto = setUserRedisSessionToken(user);
		return JsonData.ok(dto);
	}

	@ApiOperation(value = "用户登录", notes = "用户登录的接口")
	@PostMapping("/login")
	public JsonData login(@RequestBody User user) throws Exception {
		String username = user.getUsername();
		String password = user.getPassword();
		// 检查字段
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			return JsonData.ok("用户名或密码不能为空...");
		}
		// 是否存在
		User userResult = userService.queryUserForLogin(username, MD5Util.encrypt(user.getPassword()));
		if (userResult != null) {
			userResult.setPassword("");
			UserDto dto = setUserRedisSessionToken(userResult);
			return JsonData.ok(dto);
		} else {
			return JsonData.errorMsg("用户名或密码不正确, 请重试...");
		}
	}

	public UserDto setUserRedisSessionToken(User userModule) {
		String uniqueToken = UUID.randomUUID().toString();
		// 30分钟到期的token,redis已:分文件夹
		redis.set(USER_REDIS_SESSION + ":" + userModule.getId(), uniqueToken, 1000 * 60 * 30);
		UserDto dto = UserDto.adapt(userModule);
		dto.setUserToken(uniqueToken);
		return dto;
	}

	@ApiOperation(value="用户注销", notes="用户注销的接口")
	@ApiImplicitParam(name="userId", value="用户id", required=true, 
						dataType="String", paramType="query")
	@PostMapping("/logout")
	public JsonData logout(String userId) {
		redis.del(USER_REDIS_SESSION + ":" + userId);
		return JsonData.ok();
	}
}
