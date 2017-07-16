package com.lvgou.qdd.http;

/**
 * Created by sampson on 2017/7/14.
 */

public class ResponseFailObject {

    private  int status;

    private  String referer;

    private  String state;

    private  String info;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "ResponseFailObject{" +
                "status=" + status +
                ", referer='" + referer + '\'' +
                ", state='" + state + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
