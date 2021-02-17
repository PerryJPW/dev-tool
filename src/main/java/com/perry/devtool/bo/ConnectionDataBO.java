package com.perry.devtool.bo;

import javax.validation.constraints.NotNull;

/**
 * @author Perry
 * @date 2021/2/14
 */
public class ConnectionDataBO {


    private String name;
    @NotNull
    private String host;
    @NotNull
    private Integer port;
    private String password;
    private Boolean connection=false;

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
}
