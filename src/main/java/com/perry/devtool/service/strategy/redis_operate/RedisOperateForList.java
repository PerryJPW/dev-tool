package com.perry.devtool.service.strategy.redis_operate;

import com.perry.devtool.bo.ComChangeData;
import com.perry.devtool.bo.ComSetData;
import com.perry.devtool.vo.EntryList;
import com.perry.devtool.vo.TableDataVO;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Perry
 * @date 2021/2/2
 */
public class RedisOperateForList implements IRedisOperateStrategy {
    @Override
    public TableDataVO queryFromRedis( RedisTemplate<String, String> redisTemplate ,String queryKey) {
        TableDataVO<EntryList> res=new TableDataVO<>(queryKey);
        List<String> list = redisTemplate.opsForList().range(queryKey, 0, -1);
        if (list==null||list.size()==0){
            return null;
        }else {
            List<EntryList> values = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                values.add(new EntryList(i+1, list.get(i)));
            }
            res.setValues(values);
            res.setTtl(redisTemplate.getExpire(queryKey));
            res.setCount(list.size());
            res.setCode(0);
            return res;
        }
    }

    @Override
    public void changeValue(RedisTemplate<String, String> redisTemplate, ComChangeData data) {
        forceExistKey(redisTemplate,data.getDataKey());
        redisTemplate.opsForList().set(data.getDataKey(), data.getIndex(), data.getValue());
    }

    @Override
    public void setKV(RedisTemplate<String, String> redisTemplate, ComSetData data) {
        forceExistKey(redisTemplate,data.getDataKey());
        redisTemplate.opsForList().rightPushAll(data.getDataKey(), data.getListV());
    }
}
