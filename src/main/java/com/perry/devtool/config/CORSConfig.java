package com.perry.devtool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 配置跨域，并在需要跨域的controller中添加注解
 * @author Perry
 * @date 2020/12/29
 *
 */
@Configuration
public class CORSConfig {
    public CORSConfig() {
    }
    @Bean
    public CorsFilter corsFilter(){
        //1. 添加cors配置信息
        CorsConfiguration corsConfig =new CorsConfiguration();
//        corsConfig.addAllowedOrigin("http://localhost:8080");
//        corsConfig.addAllowedOrigin("http://localhost:8080/redis");
//        corsConfig.addAllowedOrigin("http://localhost:5500");
//        corsConfig.addAllowedOrigin("http://127.0.0.1:8080");
//        corsConfig.addAllowedOrigin("http://127.0.0.1:5500");
//        corsConfig.addAllowedOrigin("http://127.0.0.1:8080/redis");
        corsConfig.addAllowedOrigin("*");
        // 设置是否发送cookie信息
        corsConfig.setAllowCredentials(true);
        // 设置允许请求的方式
        corsConfig.addAllowedMethod("*");

        // 设置允许的header
        corsConfig.addAllowedHeader("*");
        //2. url添加映射路径
        UrlBasedCorsConfigurationSource corsSource =new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**",corsConfig);

        return new CorsFilter(corsSource);
        //3. controller添加 @CrossOrigin
        //4. 可能会和filter冲突，需要配置处理顺序，或者使用interceptor
    }

}
