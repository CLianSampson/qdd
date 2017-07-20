package com.lvgou.qdd.http;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lvgou.qdd.util.Logger;
import com.lvgou.qdd.util.ToastUtil;

import java.util.Map;


/**
 * Created by apple on 16/12/17.
 */
public class VolleyRequest {
    private  RequestCallback callback;

    private RequestQueue mQueue;

    public  static String url = "http://192.168.1.228:8080/LiveVideo/chenlian";

    public void setCallback(RequestCallback callback){
        this.callback=callback;
    }

    public  void getRequest(final Context context){
        mQueue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Logger.getInstance(context).info(response);

                        ResponseBasicObject responseBasicObject = JSON.parseObject(response,ResponseBasicObject.class);

                        if (responseBasicObject.getState().equals("success")){
                            callback.sucess(response);
                        }else {

                            ResponseFailObject responseFailObject = JSON.parseObject(response,ResponseFailObject.class);
                            ToastUtil.showToast(context,(String) responseFailObject.getInfo());

                            if (responseFailObject.getInfo().equals(ResponseConst.LOGIN_AUTH_FAIL)){
                               callback.fail(response);
                            }

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.getInstance(context).error(error.toString());

                ToastUtil.showToast(context,error.toString());

            }
        });

        mQueue.add(request);
    }

    public  void postRequest(final Context context, final Map<String,String> params){
        mQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {

                 Logger.getInstance(context).info(response);

                 ResponseBasicObject responseBasicObject = JSON.parseObject(response,ResponseBasicObject.class);

                 if (responseBasicObject.getState().equals("success")){
                     callback.sucess(response);
                 }else {
                     ResponseFailObject responseFailObject = JSON.parseObject(response,ResponseFailObject.class);
                     ToastUtil.showToast(context,(String) responseFailObject.getInfo());

                     Logger.getInstance(context).error("token auth result is : " + responseFailObject.getInfo());

                     if (responseFailObject.getInfo().equals(ResponseConst.LOGIN_AUTH_FAIL)){
                         callback.fail(response);
                     }
                 }
             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 Logger.getInstance(context).error(error.toString());

                 ToastUtil.showToast(context,error.toString());
             }
        }) {
            @Override
            protected Map<String, String> getParams() {

                return params;
            }
        };


        mQueue.add(request);
    }

    //针对支付宝单独一个接口
    public  void aliPayPost(final Context context, final Map<String,String> params){
        mQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Logger.getInstance(context).info(response);

                //支付宝返回的是字符串
                callback.sucess(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.getInstance(context).error(error.toString());

                ToastUtil.showToast(context,error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                return params;
            }
        };


        mQueue.add(request);
    }
}
