package com.yh.monitor.domain;

import java.util.List;
import java.util.Map;

/**
 * Created by tianhao on 2018/5/17.
 */
public class JdbcVo implements java.io.Serializable {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private String dbaName;
    private List<Map> resutData;

    public JdbcVo() {

    }

    public List<Map> getResutData() {
        return resutData;
    }

    public void setResutData(List<Map> resutData) {
        this.resutData = resutData;
    }
    public JdbcVo(String url, String username, String password, String driverClassName,List<Map> resutData,String dbaName) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driverClassName = driverClassName;
        this.resutData = resutData;
        this.dbaName = dbaName;
    }

    public String getDbaName() {
        return dbaName;
    }
    public void setDbaName(String dbaName) {
        this.dbaName = dbaName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }
}
