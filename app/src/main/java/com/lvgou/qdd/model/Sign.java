package com.lvgou.qdd.model;

/**
 * Created by sampson on 2017/7/21.
 */

public class Sign {
    private String id;
    private String title;
    private String etime;
    private String stime;
    private String sendname;
    private String toname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getSendname() {
        return sendname;
    }

    public void setSendname(String sendname) {
        this.sendname = sendname;
    }

    public String getToname() {
        return toname;
    }

    public void setToname(String toname) {
        this.toname = toname;
    }
}
