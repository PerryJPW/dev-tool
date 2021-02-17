package com.perry.devtool.utils;

import com.perry.devtool.bo.RedisConnectionSettings;
import com.perry.devtool.vo.KeysVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Perry
 * @date 2021/2/1
 */
@Component
public class InitializeUtil {
    public static List<KeysVO> initRedisKeyList() {
        List<KeysVO> keysVOList = new ArrayList<>();
        KeysVO stringParent = new KeysVO("string", "-1", "root", 0);
        List<KeysVO> stringChildren = new ArrayList<>();
        stringParent.setChildren(stringChildren);
        KeysVO hashParent = new KeysVO("hash", "-2", "root", 0);
        List<KeysVO> hashChildren = new ArrayList<>();
        stringParent.setChildren(hashChildren);
        KeysVO listParent = new KeysVO("list", "-3", "root", 0);
        List<KeysVO> listChildren = new ArrayList<>();
        stringParent.setChildren(listChildren);
        KeysVO setParent = new KeysVO("set", "-4", "root", 0);
        List<KeysVO> setChildren = new ArrayList<>();
        stringParent.setChildren(setChildren);
        KeysVO zsetParent = new KeysVO("zset", "-5", "root", 0);
        List<KeysVO> zsetChildren = new ArrayList<>();
        stringParent.setChildren(zsetChildren);

        keysVOList.add(stringParent);
        keysVOList.add(hashParent);
        keysVOList.add(listParent);
        keysVOList.add(setParent);
        keysVOList.add(zsetParent);
        return keysVOList;
    }


//    @Autowired
//    RedisTemplate<String, String> redisTemplate;
//    public  RedisTemplate<String, String> initRedisTemplate() {
//        LettuceConnectionFactory lettuceConnectionFactory = (LettuceConnectionFactory) this.redisTemplate.getConnectionFactory();
//        assert lettuceConnectionFactory != null;
////        lettuceConnectionFactory.setDatabase(0);
//        RedisConnectionSettings settings = RedisConnectionSettings.getInstance();
//        lettuceConnectionFactory.setHostName(settings.getHost());
//        lettuceConnectionFactory.setPort(settings.getPort());
//        if (settings.getPassword() != null) {
//            lettuceConnectionFactory.setPassword(settings.getPassword());
//        }
//        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
//        lettuceConnectionFactory.afterPropertiesSet();
//        return redisTemplate;
//    }
}
