package com.perry.devtool.service.strategy.redis_operate;

import com.perry.devtool.enums.ExceptionEnum;
import com.perry.devtool.exception.CommonException;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Perry
 * @date 2021/2/2
 */
public class RedisOperateStrategyFactory {

    private static final Map<String, IRedisOperateStrategy> strategyMap = new HashMap<>();


    static {
        strategyMap.put("string", new RedisOperateForString());
        strategyMap.put("hash", new RedisOperateForHash());
        strategyMap.put("list", new RedisOperateForList());
        strategyMap.put("set", new RedisOperateForSet());
        strategyMap.put("zset", new RedisOperateForZset());
    }

    public static IRedisOperateStrategy getRedisQueryStrategy(String type){
        if (StringUtils.isEmpty(type)){
            throw new CommonException(ExceptionEnum.TYPE_ERROR);
        }else {
            return strategyMap.get(type);
        }
    }
}
