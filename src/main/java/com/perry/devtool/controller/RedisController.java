package com.perry.devtool.controller;

import com.perry.devtool.bo.ComChangeData;
import com.perry.devtool.bo.ConnectionDataBO;
import com.perry.devtool.bo.RedisConnectionSettings;
import com.perry.devtool.common.ApiRestResponse;
import com.perry.devtool.enums.ExceptionEnum;
import com.perry.devtool.service.RedisService;
import com.perry.devtool.vo.DBCountVO;
import com.perry.devtool.vo.TableDataVO;
import com.perry.devtool.vo.KeysVO;
import com.perry.devtool.vo.StringVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Perry
 * @date 2021/2/1
 */
@RestController
@CrossOrigin(origins = "*" ,maxAge = 3600)
@RequestMapping("redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @GetMapping("/get-keys")
    public ApiRestResponse<Object> getKeys() throws InterruptedException {
        List<KeysVO> keys = redisService.getKeys();
//        Thread.sleep(10000);
        return ApiRestResponse.success(keys);

    }

    @GetMapping("/get-string")
    public ApiRestResponse<Object> getStringValueByKey(@RequestParam String key) {
        StringVO result = redisService.getStringByKey(key);
        if (result == null) {
            return ApiRestResponse.error(ExceptionEnum.NO_DATA_ERROR);
        }
        return ApiRestResponse.success(result);
    }


    @GetMapping("/get-value")
    public ApiRestResponse<Object> getValueByKey(@RequestParam String key, @RequestParam String type) {
        TableDataVO result = redisService.getValueByKey(key, type);
        if (result == null) {
            return ApiRestResponse.error(ExceptionEnum.NO_DATA_ERROR);
        }
        return ApiRestResponse.success(result);
    }

    @GetMapping("/rename-key")
    public ApiRestResponse<Object> renameKey(@RequestParam String oldKey, @RequestParam String newKey) {
        redisService.renameKey(oldKey, newKey);
        return ApiRestResponse.success();
    }

    @GetMapping("/expire-key")
    public ApiRestResponse<Object> expireKey(@RequestParam String key, @RequestParam Long timeout) {
        redisService.expireKey(key, timeout);
        return ApiRestResponse.success();
    }

    @GetMapping("/get-database")
    public ApiRestResponse<Object> getDatabase() {
        List<DBCountVO> dbCount = redisService.getDbCount();
        return ApiRestResponse.success(dbCount);
    }


    //TODO 前端
    @GetMapping("/change-string-value")
    public ApiRestResponse<Object> changeStringValue(@RequestParam String key, @RequestParam String value) {
        ComChangeData data = new ComChangeData(key);
        data.setValue(value);
        redisService.changeValue(data, "string");
        return ApiRestResponse.success();
    }

    @GetMapping("/change-hash-value")
    public ApiRestResponse<Object> changeHashValue(@RequestParam String key, @RequestParam String field, @RequestParam String value) {
        ComChangeData data = new ComChangeData(key);
        data.setValue(field);
        data.setValue(value);
        redisService.changeValue(data, "hash");
        return ApiRestResponse.success();
    }


    @GetMapping("/change-list-value")
    public ApiRestResponse<Object> changeListValue(@RequestParam String key, @RequestParam Long index, @RequestParam String value) {
        ComChangeData data = new ComChangeData(key);
        data.setValue(value);
        data.setIndex(index);
        redisService.changeValue(data, "list");
        return ApiRestResponse.success();
    }

    @GetMapping("/change-set-value")
    public ApiRestResponse<Object> changeSetValue(@RequestParam String key, @RequestParam String value) {
        ComChangeData data = new ComChangeData(key);
        data.setValue(value);
        redisService.changeValue(data, "set");
        return ApiRestResponse.success();
    }

    @GetMapping("/change-zset-value")
    public ApiRestResponse<Object> changeZsetValue(@RequestParam String key, @RequestParam String value
            , @RequestParam Long score) {
        ComChangeData data = new ComChangeData(key);
        data.setValue(value);
        data.setScore(score);
        redisService.changeValue(data, "zset");
        return ApiRestResponse.success();
    }
    //TODO setKV接口


    @PostMapping("/connect-redis")
    public ApiRestResponse<Object> connectRedis(@RequestBody ConnectionDataBO connectionData) {
        redisService.connectionRedis(connectionData);
        return ApiRestResponse.success();
    }

    @GetMapping("/get-connection")
    public ApiRestResponse<Object> getConnectionSettings() {
        RedisConnectionSettings settings=RedisConnectionSettings.getInstance();
        ConnectionDataBO connectionData=new ConnectionDataBO();
        BeanUtils.copyProperties(settings,connectionData);
        connectionData.setPassword("");
        return ApiRestResponse.success(connectionData);
    }

    @GetMapping("/change-database")
    public ApiRestResponse<Object> changeDatabase(@RequestParam Integer database) {
        redisService.changeDatabase(database);
        return ApiRestResponse.success();
    }
}
