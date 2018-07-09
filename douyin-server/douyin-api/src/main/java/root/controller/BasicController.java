package root.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RestController;

import root.redis.RedisOperator;

@RestController
public class BasicController {
	
	@Resource
	public RedisOperator redis;
	// 存储在redis中Session的KEY
	public static final String USER_REDIS_SESSION = "user_redis_session"; 
	// 文件根路径
	public static final String FILE_SPACE = "C:/douyin/uploadresource";
	// ffmpeg执行文件位置
	public static final String FFMPEG_EXE = "D:\\ffmpeg\\bin\\ffmpeg.exe";
	// 每页显示数
	public static final Integer PAGE_SIZE = 5;
		
}	
