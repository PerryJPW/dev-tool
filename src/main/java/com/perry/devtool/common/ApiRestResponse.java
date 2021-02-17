package com.perry.devtool.common;

import com.perry.devtool.enums.ExceptionEnum;

/**
 * @author Perry
 * @date 2020/12/2
 * 通用返回对象
 */
public class ApiRestResponse<T> {
    private Integer state;
    private String msg;
    private T data;
    private static final Integer OK_CODE = 10000;
    private static final String OK_MSG = "SUCCESS";

    public ApiRestResponse(Integer state, String msg, T data) {
        this.state = state;
        this.msg = msg;
        this.data = data;
    }

    public ApiRestResponse(Integer state, String msg) {
        this.state = state;
        this.msg = msg;
    }

    public ApiRestResponse() {
        this(OK_CODE, OK_MSG);
    }
    public static <T> ApiRestResponse<T> success(){
        return new ApiRestResponse<>();
    }
    public static <T> ApiRestResponse<T> success(T result){
        ApiRestResponse<T> response =new ApiRestResponse<>();
        response.setData(result);
        return response;
    }
    public static <T> ApiRestResponse<T> error(ExceptionEnum ex){
        return new ApiRestResponse<T>(ex.getCode(),ex.getMsg());
    }
    public static <T> ApiRestResponse<T> error(Integer code,String msg){
        return new ApiRestResponse<>(code,msg);
    }


    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiRestResponse{" +
                "state=" + state +
                ", msg='" + msg + '\'' +
                ", date=" + data +
                '}';
    }
}
