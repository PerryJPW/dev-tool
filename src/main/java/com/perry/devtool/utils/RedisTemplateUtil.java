package com.perry.devtool.utils;

import com.perry.devtool.bo.RedisConnectionSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Perry
 * @date 2021/2/13
 */
@Component
public class RedisTemplateUtil {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public RedisTemplate<String, String> refreshRedisTemplate() {
        LettuceConnectionFactory lettuceConnectionFactory = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
        assert lettuceConnectionFactory != null;
        RedisConnectionSettings settings=RedisConnectionSettings.getInstance();
        lettuceConnectionFactory.setDatabase(settings.getDbNum());
        lettuceConnectionFactory.setHostName(settings.getHost());
        lettuceConnectionFactory.setPort(settings.getPort());
        lettuceConnectionFactory.setPassword(settings.getPassword());
        lettuceConnectionFactory.resetConnection();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        lettuceConnectionFactory.afterPropertiesSet();
        redisTemplate.opsForValue().set("test","connection yes");
        redisTemplate.delete("test");
        return redisTemplate;
    }
    public RedisTemplate<String,String> getRedisTemplate(){
        return  redisTemplate;
    }

}
