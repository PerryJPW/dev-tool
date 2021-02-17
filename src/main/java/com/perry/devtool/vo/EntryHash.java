package com.perry.devtool.vo;

/**
 * @author Perry
 * @date 2021/2/2
 */
public class EntryHash {
    private Integer id;
    private Object field;
    private Object value;

    public EntryHash(Object field, Object value) {
        this.field = field;
        this.value = value;
    }

    public EntryHash(Integer id, Object field, Object value) {
        this.field = field;
        this.value = value;
        this.id = id;
    }

    public EntryHash() {
    }

    public Object getField() {
        return field;
    }

    public void setField(Object field) {
        this.field = field;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
