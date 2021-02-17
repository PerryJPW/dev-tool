package com.perry.devtool.vo;

/**
 * @author Perry
 * @date 2021/2/4
 */
public class DBCountVO {
    String title;
    Integer id;
    Boolean checked;

    public DBCountVO(String title, Integer id, Boolean checked) {
        this.title = title;
        this.id = id;
        this.checked = checked;
    }

    public DBCountVO() {
    }

    public DBCountVO(String title, Integer id) {
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
