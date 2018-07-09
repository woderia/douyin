package root.interceptor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import root.bean.JsonData;
import root.redis.RedisOperator;
import root.utils.JsonUtils;

public class MiniInterceptor implements HandlerInterceptor{

	@Resource
	private RedisOperator redis;
	public static final String USER_REDIS_SESSION = "user-redis-session";
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String userId = request.getHeader("headerUserId");
		String userToken = request.getHeader("headerUserToken");
		if (StringUtils.isNoneBlank(userId) && StringUtils.isNotBlank(userToken)) {
			String uniqueToken = redis.get(USER_REDIS_SESSION + ":" + userId);
			if (StringUtils.isEmpty(uniqueToken) && StringUtils.isBlank(uniqueToken)) {
				// 未登录
				returnErrorResponse(response, new JsonData().errorTokenMsg("请登录..."));
				return false;
			} else {
				
			} if(!uniqueToken.equals(userToken)) {
				returnErrorResponse(response, new JsonData().errorTokenMsg("账号被挤出..."));
				return false;
			}
		} else {
			returnErrorResponse(response, new JsonData().errorTokenMsg("请登录..."));
			return false;
		}
		return true;
	}
	private void returnErrorResponse(HttpServletResponse response, JsonData result) throws UnsupportedEncodingException, IOException {
		OutputStream out=null;
		try{
		    response.setCharacterEncoding("utf-8");
		    response.setContentType("text/json");
		    out = response.getOutputStream();
		    out.write(JsonUtils.objectToJson(result).getBytes("utf-8"));
		    out.flush();
		} finally{
		    if(out!=null){
		        out.close();
		    }
		}
	}
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
	
	
}
