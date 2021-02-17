package com.perry.devtool.service;

import com.perry.devtool.bo.ComChangeData;
import com.perry.devtool.bo.ComSetData;
import com.perry.devtool.bo.ConnectionDataBO;
import com.perry.devtool.vo.DBCountVO;
import com.perry.devtool.vo.TableDataVO;
import com.perry.devtool.vo.KeysVO;
import com.perry.devtool.vo.StringVO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author Perry
 * @date 2021/2/1
 */
public interface RedisService {
    List<KeysVO> getKeys();

    StringVO getStringByKey(String key);


    TableDataVO getValueByKey(String key, String type);

    void renameKey(String oldKey, String newKey);

    void expireKey(String key, Long expireTime);

    List<DBCountVO> getDbCount();

    void changeValue(ComChangeData data, String type);


    void setKV(ComSetData data, String type);

    void connectionRedis(ConnectionDataBO connectionData);

    void changeDatabase(Integer base);
}
