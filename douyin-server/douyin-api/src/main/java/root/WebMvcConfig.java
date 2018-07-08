package root;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	
		registry.addResourceHandler("/**")
				// swagger2访问资源
				.addResourceLocations("classpath:/META-INF/resources/")
				// 静态网址访问资源
				.addResourceLocations("file:C:/douyin/uploadresource/");
	}

	
}
