package com.perry.devtool.enums;

/**
 * @author Perry
 * @date 2021/2/1
 */
public enum RedisTypeEnum {
    none(-1,"none"),
    string(0,"string"),
    hash(1,"hash"),
    list(2,"list"),
    set(3,"set"),
    zset(4,"zset");

    private Integer typeId;
    private String typeName;

    RedisTypeEnum(Integer typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
