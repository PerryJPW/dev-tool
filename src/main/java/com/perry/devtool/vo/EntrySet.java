package com.perry.devtool.vo;

/**
 * @author Perry
 * @date 2021/2/2
 */
public class EntrySet {
    private Integer id;
    private Double score;
    private Object value;

    public EntrySet(Integer id, Object value) {
        this.id = id;
        this.value = value;
    }

    public EntrySet(Integer id, Double score, Object value) {
        this.id = id;
        this.score = score;
        this.value = value;
    }

    public EntrySet() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
