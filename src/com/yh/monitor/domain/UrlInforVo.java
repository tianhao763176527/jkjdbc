package com.yh.monitor.domain;

/**
 * Created by tianhao on 2018/5/16.
 */
public class UrlInforVo implements java.io.Serializable{
    private String url;
    private String state;
    private Long respondTime;
    private String name;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UrlInforVo(String url, String state, Long respondTime) {
        this.url = url;
        this.state = state;
        this.respondTime = respondTime;
        this.name = name;
    }

    public UrlInforVo(String url, String state, Long respondTime,String name) {
        this.url = url;
        this.state = state;
        this.respondTime = respondTime;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UrlInforVo(){

    }
    public void setUrl(String url) {
        this.url = url;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setRespondTime(Long respondTime) {
        this.respondTime = respondTime;
    }

    public String getUrl() {
        return url;
    }

    public String getState() {
        return state;
    }

    public Long getRespondTime() {
        return respondTime;
    }
}
