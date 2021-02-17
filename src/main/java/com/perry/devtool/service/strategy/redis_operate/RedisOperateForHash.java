package com.perry.devtool.service.strategy.redis_operate;

import com.perry.devtool.bo.ComChangeData;
import com.perry.devtool.bo.ComSetData;
import com.perry.devtool.vo.EntryHash;
import com.perry.devtool.vo.TableDataVO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Perry
 * @date 2021/2/2
 */

@Component
public class RedisOperateForHash implements IRedisOperateStrategy {

    @Override
    public TableDataVO<EntryHash> queryFromRedis(RedisTemplate<String, String> redisTemplate, String queryKey) {


        TableDataVO<EntryHash> res = new TableDataVO<>(queryKey);
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(queryKey);
        if (entries.size() == 0) {
            return null;
        } else {
            List<EntryHash> values = new ArrayList<>();
            int id = 1;
            for (Object field : entries.keySet()) {
                values.add(new EntryHash(id, field, entries.get(field)));
                id++;
            }
            res.setValues(values);
            res.setTtl(redisTemplate.getExpire(queryKey));
            res.setCount(entries.size());
            res.setCode(0);
            return res;
        }
    }

    @Override
    public void changeValue(RedisTemplate<String, String> redisTemplate, ComChangeData data) {
        forceExistKey(redisTemplate,data.getDataKey());
        redisTemplate.opsForHash().put(data.getDataKey(), data.getHashField(), data.getValue());
    }

    @Override
    public void setKV(RedisTemplate<String, String> redisTemplate, ComSetData data){
        forceExistKey(redisTemplate, data.getDataKey());
        redisTemplate.opsForHash().putAll(data.getDataKey(), data.getHashFV());
    }
}
