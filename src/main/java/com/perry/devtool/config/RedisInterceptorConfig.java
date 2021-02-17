package com.perry.devtool.config;

import com.perry.devtool.filter.RedisInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Perry
 * @date 2021/2/14
 */
@Configuration
public class RedisInterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RedisInterceptor())
                .addPathPatterns("/redis/*")
                .excludePathPatterns("/redis/get-connection","/redis/connect-redis");
    }
}
