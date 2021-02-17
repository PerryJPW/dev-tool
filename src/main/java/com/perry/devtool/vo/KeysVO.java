package com.perry.devtool.vo;

import java.util.List;

/**
 * @author Perry
 * @date 2021/2/1
 */
public class KeysVO {
    private String title;
    private String id;
    private String type;
    private Integer childCount;
    private List<KeysVO> children;


    public KeysVO(String title, String id, String type, Integer childCount) {
        this.title = title;
        this.id = id;
        this.type = type;
        this.childCount = childCount;
    }

    public KeysVO(String title, String id, String type, List<KeysVO> children, Integer childCount) {
        this.title = title;
        this.id = id;
        this.type = type;
        this.children = children;
        this.childCount = childCount;
    }

    public KeysVO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<KeysVO> getChildren() {
        return children;
    }

    public void setChildren(List<KeysVO> children) {
        this.children = children;
    }

    public Integer getChildCount() {
        return childCount;
    }

    public void setChildCount(Integer childCount) {
        this.childCount = childCount;
    }
}
