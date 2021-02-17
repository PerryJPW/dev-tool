package com.perry.devtool.enums;

/**
 * @author Perry
 * @date 2020/12/2
 * 异常枚举
 */
public enum ExceptionEnum {

    REQUEST_PARAM_ERROR(10011, "参数异常"),
    NO_DATA_ERROR(10002, "无数据"),
    TYPE_ERROR(10003, "类型错误"),
    EXIST_KEY_ERROR(10004, "已存在key，非法操作"),
    SET_ERROR(10005, "设置错误"),
    OPERATE_ERROR(10006, "非法操作"),
    CONNECTION_ERROR(10007, "连接异常"),

    SYSTEM_ERROR(20000, "系统异常"),
    INIT_ERROR(99999, "系统异常");
    private Integer code;
    private String msg;

    ExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
