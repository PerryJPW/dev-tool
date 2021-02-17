package com.perry.devtool.service.strategy.redis_operate;

import com.perry.devtool.bo.ComChangeData;
import com.perry.devtool.bo.ComSetData;
import com.perry.devtool.vo.EntrySet;
import com.perry.devtool.vo.TableDataVO;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Perry
 * @date 2021/2/2
 */

@Component
public class RedisOperateForSet implements IRedisOperateStrategy {

    @Override
    public TableDataVO<EntrySet> queryFromRedis(RedisTemplate<String, String> redisTemplate, String queryKey) {
        //TODO 模板模式

        TableDataVO<EntrySet> res = new TableDataVO<>(queryKey);
        Set<String> members = redisTemplate.opsForSet().members(queryKey);

        if (members==null|| members.size() == 0) {
            return null;
        } else {
            List<EntrySet> values = new ArrayList<>();
            int id = 1;
            for (String member : members) {

                values.add(new EntrySet(id, 0.0, member));
                id++;
            }
            res.setValues(values);
            res.setTtl(redisTemplate.getExpire(queryKey));
            res.setCount(members.size());
            res.setCode(0);
            return res;
        }
    }

    @Override
    public void changeValue(RedisTemplate<String, String> redisTemplate, ComChangeData data) {
        forceExistKey(redisTemplate,data.getDataKey());
        //使用事务
        SessionCallback<Object> callback = new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.opsForSet().remove(data.getDataKey(), data.getValue());
                operations.opsForSet().add(data.getDataKey(), data.getValue());
                return operations.exec();
            }
        };
        redisTemplate.execute(callback);
    }

    @Override
    public void setKV(RedisTemplate<String, String> redisTemplate, ComSetData data) {
        forceExistKey(redisTemplate,data.getDataKey());
        redisTemplate.opsForSet().add(data.getDataKey(), data.getV());
    }
}
