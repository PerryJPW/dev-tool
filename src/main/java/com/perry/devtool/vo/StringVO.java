package com.perry.devtool.vo;

/**
 * @author Perry
 * @date 2021/2/1
 */
public class StringVO {
    private String key;
    private String value;
    private Long ttl;

    public StringVO(String key) {
        this.key = key;
    }

    public StringVO() {
    }

    public StringVO(String key, String value, Long ttl) {
        this.key = key;
        this.value = value;
        this.ttl = ttl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }
}
