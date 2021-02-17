package com.perry.devtool.service.strategy.redis_operate;

import com.perry.devtool.bo.ComChangeData;
import com.perry.devtool.bo.ComSetData;
import com.perry.devtool.vo.TableDataVO;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Perry
 * @date 2021/2/8
 */
public class RedisOperateForString implements IRedisOperateStrategy{
    @Override
    public TableDataVO queryFromRedis(RedisTemplate<String, String> redisTemplate, String queryKey) {
        return null;
    }

    @Override
    public void changeValue(RedisTemplate<String, String> redisTemplate, ComChangeData data) {
        forceExistKey(redisTemplate,data.getDataKey());
        redisTemplate.opsForValue().set(data.getDataKey(),data.getValue());
    }

    @Override
    public void setKV(RedisTemplate<String, String> redisTemplate, ComSetData data) {
        forceExistKey(redisTemplate,data.getDataKey());
        redisTemplate.opsForValue().set(data.getDataKey(), data.getV());
    }
}
