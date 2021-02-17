package com.perry.devtool.bo;

/**
 * @author Perry
 * @date 2021/2/8
 */
public class ComChangeData {
    private String dataKey;
    private String value;
    private String hashField;
    private Long index;
    private Long score;

    public ComChangeData(String dataKey, String value) {
        this.dataKey = dataKey;
        this.value = value;
    }

    public ComChangeData(String dataKey) {
        this.dataKey = dataKey;
    }

    public ComChangeData() {
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getHashField() {
        return hashField;
    }

    public void setHashField(String hashField) {
        this.hashField = hashField;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }
}
