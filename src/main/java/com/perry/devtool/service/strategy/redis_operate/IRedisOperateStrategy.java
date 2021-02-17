package com.perry.devtool.service.strategy.redis_operate;

import com.perry.devtool.bo.ComChangeData;
import com.perry.devtool.bo.ComSetData;
import com.perry.devtool.enums.ExceptionEnum;
import com.perry.devtool.exception.CommonException;
import com.perry.devtool.vo.TableDataVO;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 使用策略模式，封装 redis 的查询、变更，设置操作，让不同类型的数据使用统一的接口
 * @author Perry
 * @date 2021/2/2
 */

public interface IRedisOperateStrategy {
    public TableDataVO queryFromRedis(RedisTemplate<String, String> redisTemplate,String queryKey);

    public void changeValue(RedisTemplate<String, String> redisTemplate, ComChangeData data);

    default void forceExistKey(RedisTemplate<String, String> redisTemplate, String key) {
        Boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey == null || !hasKey) {
            throw new CommonException(ExceptionEnum.OPERATE_ERROR);
        }
    }

    void setKV(RedisTemplate<String, String> redisTemplate, ComSetData data);
}
