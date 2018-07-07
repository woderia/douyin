package root.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import root.bean.JsonData;
import root.model.User;
import root.service.UserService;

@RestController
public class RegistLoginController {

	private UserService userService;
	
	public JsonData regist(@RequestBody User user) {
		return JsonData.ok();
	}
}
