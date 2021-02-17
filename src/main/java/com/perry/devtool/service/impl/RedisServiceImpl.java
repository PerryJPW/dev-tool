package com.perry.devtool.service.impl;

import com.perry.devtool.bo.ComChangeData;
import com.perry.devtool.bo.ComSetData;
import com.perry.devtool.bo.ConnectionDataBO;
import com.perry.devtool.bo.RedisConnectionSettings;
import com.perry.devtool.enums.ExceptionEnum;
import com.perry.devtool.enums.RedisTypeEnum;
import com.perry.devtool.exception.CommonException;
import com.perry.devtool.service.strategy.redis_operate.IRedisOperateStrategy;
import com.perry.devtool.service.strategy.redis_operate.RedisOperateStrategyFactory;
import com.perry.devtool.utils.InitializeUtil;
import com.perry.devtool.utils.RedisTemplateUtil;
import com.perry.devtool.vo.*;
import com.perry.devtool.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Perry
 * @date 2021/2/1
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplateUtil redisTemplateUtil;


    private RedisTemplate<String, String> redisTemplate;

    //        private final RedisTemplate<String, String> redisTemplate=InitializeUtil.initRedisTemplate();
//    static  {
//        InitializeUtil.initRedisTemplate(redisTemplate);
//    }

    @Override
    public List<KeysVO> getKeys() {
        redisTemplate = redisTemplateUtil.getRedisTemplate();
        Set<String> keySet = redisTemplate.keys("*");
        if (keySet == null) {
            return null;
        }
        List<KeysVO> keyList = InitializeUtil.initRedisKeyList();
        Map<String, Integer> nsKeyIndexMap = new HashMap<>();
        for (String key : keySet) {
            //遍历每一个key，解析为前端tree的数据格式
            DataType type = redisTemplate.type(key);
            assert type != null;
            KeysVO keysVo = new KeysVO(key, key, type.code(), 0);
//            keysVo.setTitle(key);
//            keysVo.setId(key);
//            keysVo.setType(type.code());
//            keysVo.setChildCount(0);

            Integer typeId = RedisTypeEnum.valueOf(type.code()).getTypeId();
            KeysVO root = keyList.get(typeId);

            if (root.getChildren() == null) {
                List<KeysVO> childrenList = new ArrayList<>();
                root.setChildren(childrenList);
            }

            List<KeysVO> children = root.getChildren();

            int index = key.indexOf(':');
            if (index != -1) {
                //key 有namespace(:)，进行解析
                String nsKey = key.substring(0, index);
                if (!nsKeyIndexMap.containsKey(nsKey)) {
                    nsKeyIndexMap.put(nsKey, children.size());
                    KeysVO namespace = new KeysVO(nsKey, nsKey, "namespace", 0);
                    List<KeysVO> namespaceChildren = new ArrayList<>();
                    namespace.setChildren(namespaceChildren);
                    children.add(namespace);
                }
                //再Map中查询namespace的存储位置，并将key添加到namespace中
                Integer namespaceIndex = nsKeyIndexMap.get(nsKey);
                KeysVO namespaceNode = children.get(namespaceIndex);
                namespaceNode.setChildCount(namespaceNode.getChildCount() + 1);
                namespaceNode.setTitle(namespaceNode.getId() + "::" + namespaceNode.getChildCount());
                namespaceNode.getChildren().add(keysVo);

            } else {
                children.add(keysVo);
            }


            root.setChildCount(root.getChildCount() + 1);
        }
        for (KeysVO rootKey : keyList) {
            rootKey.setTitle(rootKey.getTitle() + "(" + rootKey.getChildCount() + ")");
        }
        return keyList;
    }

    @Override
    public StringVO getStringByKey(String key) {
        redisTemplate = redisTemplateUtil.getRedisTemplate();
        StringVO res = new StringVO(key);
        String value = redisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(value)) {
            return null;
        } else {
            res.setValue(value);
            res.setTtl(redisTemplate.getExpire(key));
        }
        return res;
    }

    @Override
    public TableDataVO getValueByKey(String key, String type) {
        redisTemplate = redisTemplateUtil.getRedisTemplate();
        IRedisOperateStrategy redisQuery = RedisOperateStrategyFactory.getRedisQueryStrategy(type);
        if (redisQuery == null) {
            throw new CommonException(ExceptionEnum.TYPE_ERROR);
        } else {
            return redisQuery.queryFromRedis(redisTemplate, key);
        }

    }

    @Override
    public void renameKey(String oldKey, String newKey) {
        redisTemplate = redisTemplateUtil.getRedisTemplate();
        Boolean existKey = redisTemplate.hasKey(newKey);

        if (existKey == null || existKey) {
            throw new CommonException(ExceptionEnum.EXIST_KEY_ERROR);
        } else {
            redisTemplate.rename(oldKey, newKey);
        }
    }

    @Override
    public void expireKey(String key, Long timeout) {
        redisTemplate = redisTemplateUtil.getRedisTemplate();
        Boolean expireSuccess;
        if (timeout == -1) {
            expireSuccess = redisTemplate.persist(key);
        } else {
            expireSuccess = redisTemplate.expire(key, timeout, TimeUnit.SECONDS);

        }

        if (expireSuccess == null || !expireSuccess) {
            throw new CommonException(ExceptionEnum.SET_ERROR);
        }

    }

    @Override
    public List<DBCountVO> getDbCount() {
        redisTemplate = redisTemplateUtil.getRedisTemplate();
        RedisConnectionSettings settings = RedisConnectionSettings.getInstance();

        Jedis jedis = new Jedis(settings.getHost(), settings.getPort());
        List<String> databases = jedis.configGet("databases");
        int dbCount = Integer.parseInt(databases.get(1));
        List<DBCountVO> res = new ArrayList<>(dbCount);
        for (int i = 0; i < dbCount; i++) {
            jedis.select(i);
            DBCountVO dbCountVO = new DBCountVO("db" + i + ":" + jedis.dbSize(), i);
            if (i == settings.getDbNum()) {
                dbCountVO.setChecked(true);
            }
            res.add(dbCountVO);
        }
        jedis.close();
        return res;
    }

    @Override
    public void changeValue(ComChangeData data, String type) {
        redisTemplate = redisTemplateUtil.getRedisTemplate();
        IRedisOperateStrategy redisChange = RedisOperateStrategyFactory.getRedisQueryStrategy(type);
        if (redisChange == null) {
            throw new CommonException(ExceptionEnum.TYPE_ERROR);
        } else {
            redisChange.changeValue(redisTemplate, data);
        }
    }

    @Override
    public void setKV(ComSetData data, String type) {
        redisTemplate = redisTemplateUtil.getRedisTemplate();
        IRedisOperateStrategy redisSet = RedisOperateStrategyFactory.getRedisQueryStrategy(type);
        if (redisSet == null) {
            throw new CommonException(ExceptionEnum.TYPE_ERROR);
        } else {
            redisSet.setKV(redisTemplate, data);
        }
    }

    @Override
    public void connectionRedis(ConnectionDataBO connectionData) {

        RedisConnectionSettings settings = RedisConnectionSettings.getInstance();
        try (Jedis jedis = new Jedis(connectionData.getHost(), connectionData.getPort())) {
            if (!StringUtils.isEmpty(connectionData.getPassword())) {
                jedis.auth(connectionData.getPassword());
            }
            jedis.set("test", "connection yes");
            jedis.del("test");
            settings.setHostPort(connectionData.getHost(), connectionData.getPort());
            if (StringUtils.isEmpty(connectionData.getName())) {
                settings.setName("默认");
            } else {
                settings.setName(connectionData.getName());
            }
            if (!StringUtils.isEmpty(connectionData.getPassword())) {
                settings.setPassword(connectionData.getPassword());
            }
            settings.setConnection(true);
            redisTemplate = redisTemplateUtil.refreshRedisTemplate();
        } catch (Exception e) {
            settings.setConnection(false);
            throw new CommonException(ExceptionEnum.CONNECTION_ERROR);
        }
    }

    @Override
    public void changeDatabase(Integer database) {
        RedisConnectionSettings settings = RedisConnectionSettings.getInstance();
        settings.setDbNum(database);
        redisTemplate = redisTemplateUtil.refreshRedisTemplate();

    }
}
