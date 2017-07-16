package com.lvgou.qdd.http;

/**
 * Created by apple on 16/12/18.
 */
public interface RequestCallback {

    public  void sucess(String response);

    public  void fail(String response);
}
