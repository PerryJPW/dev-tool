package com.perry.devtool.config;

import com.perry.devtool.bo.RedisConnectionSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Perry
 * @date 2021/2/10
 */
@Configuration
public class RedisConfig {
//    @Value("${user.redis.host}")
//    private String hostName;
//    @Value("${user.redis.port}")
//    private int port;
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisConnectionSettings settings=RedisConnectionSettings.getInstance();

        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(settings.getHost(), settings.getPort()));
    }


}
