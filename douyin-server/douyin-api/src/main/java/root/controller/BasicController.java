package root.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RestController;

import root.redis.RedisOperator;

@RestController
public class BasicController {
	
	@Resource
	public RedisOperator redis;
	
	public static final String USER_REDIS_SESSION = "user_redis_session"; 
	// 文件根路径
	public static final String FILE_SPACE = "C:/douyin/uploadresource";
	
	public static final String FFMPEG_EXE = "D:\\ffmpeg\\bin\\ffmpeg.exe";
		
}	
