package root.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import root.bean.JsonData;
import root.service.BgmService;

@Api(value="背景音乐业务的接口", tags={"背景音乐业务的Controller"})
@RestController
@RequestMapping("/bgm")
public class BgmController {
	
	@Resource
	private BgmService bgmService;
	
	@ApiOperation(value="获取背景音乐列表", notes="获取背景音乐列表")
	@PostMapping("/list")
	public JsonData list() {
		return JsonData.ok(bgmService.queryBgmList());
	}
}
