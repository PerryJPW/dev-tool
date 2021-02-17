package com.perry.devtool.config;

import com.perry.devtool.filter.RedisConnectionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Perry
 * @date 2021/2/14
 */
//@Configuration
public class RedisFilterConfig {
//    @Bean
    public RedisConnectionFilter redisFilter() {
        return new RedisConnectionFilter();
    }

//    @Bean(name = "redisFilterConf")
    public FilterRegistrationBean adminFilterRegister() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(redisFilter());
        filterRegistrationBean.addUrlPatterns("/redis/*");
//        filterRegistrationBean.addUrlPatterns("/user/update/*");
//        filterRegistrationBean.addUrlPatterns("/book/*");
//        filterRegistrationBean.addUrlPatterns("/account/*");
//        filterRegistrationBean.addUrlPatterns("/type/*");
//        filterRegistrationBean.addUrlPatterns("/admin/order/*");
        filterRegistrationBean.setName("redisFilterConf");
        return filterRegistrationBean;
    }
}
