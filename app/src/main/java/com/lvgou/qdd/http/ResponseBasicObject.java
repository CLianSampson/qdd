package com.lvgou.qdd.http;

/**
 * Created by sampson on 2017/7/14.
 */

public class ResponseBasicObject {
    private  int status;

    private  String referer;

    private  String state;

    private  Object object;

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

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "ResponseBasicObject{" +
                "status=" + status +
                ", referer='" + referer + '\'' +
                ", state='" + state + '\'' +
                ", object=" + object +
                '}';
    }
}
