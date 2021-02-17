package com.perry.devtool.vo;

/**
 * @author Perry
 * @date 2021/2/2
 */
public class EntryList {
    private Integer id;
    private String value;

    public EntryList(Integer id, String value) {
        this.id = id;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
