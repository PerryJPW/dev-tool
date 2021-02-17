package com.perry.devtool.vo;

import java.util.List;
import java.util.Map;

/**
 * @author Perry
 * @date 2021/2/1
 */
public class TableDataVO<V> {
    private String key;
    private List<V> values;
    private Long ttl;
    private Integer count;
    private Integer code;


    public TableDataVO(String key) {
        this.key = key;
    }

    public TableDataVO() {
    }

    public TableDataVO(String key, List<V> values, Long ttl) {
        this.key = key;
        this.values = values;
        this.ttl = ttl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<V> getValues() {
        return values;
    }

    public void setValues(List<V> values) {
        this.values = values;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
