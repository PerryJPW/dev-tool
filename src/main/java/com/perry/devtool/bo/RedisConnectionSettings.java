package com.perry.devtool.bo;

/**
 * 单例模式保存redis连接信息
 *
 * @author Perry
 * @date 2021/2/10
 */
public class RedisConnectionSettings {
    private String name;
    private String host;
    private Integer port;
    private String password=null;
    private Boolean connection=false;
    private Integer dbNum;

    private RedisConnectionSettings() {
        name="default";
        host="192.168.247.132";
//        host="127.0.0.1";
        port=6379;
        dbNum=0;
    }

    private static class SingletonHolder {
        private static final RedisConnectionSettings instance = new RedisConnectionSettings();
    }


    public static RedisConnectionSettings getInstance() {
        return SingletonHolder.instance;
    }
    public void setHostPort(String host,Integer port){
        this.host=host;
        this.port=port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getConnection() {
        return connection;
    }

    public void setConnection(Boolean connection) {
        this.connection = connection;
    }

    public Integer getDbNum() {
        return dbNum;
    }

    public void setDbNum(Integer dbNum) {
        this.dbNum = dbNum;
    }
}
