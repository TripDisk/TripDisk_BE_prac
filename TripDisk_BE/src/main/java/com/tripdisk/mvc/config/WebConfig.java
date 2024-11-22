package com.tripdisk.mvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//import com.tripdisk.mvc.interceptor.AdminInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{

//	@Autowired
//	AdminInterceptor adminInterceptor;
//	
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(adminInterceptor).addPathPatterns("/api-user/users");
//	}
//}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // 모든 경로에 대해
				.allowedOrigins("http://localhost:5173") // 허용할 Origin
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH") // 허용할 HTTP 메서드
				.allowedHeaders("*") // 허용할 요청 헤더
				.allowCredentials(true); // 쿠키 허용
	}

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("classpath:/static/img/");
    }
}

