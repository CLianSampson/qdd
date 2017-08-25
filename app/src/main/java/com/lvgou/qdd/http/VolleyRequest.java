package com.lvgou.qdd.http;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lvgou.qdd.util.Logger;
import com.lvgou.qdd.util.StringUtil;
import com.lvgou.qdd.util.ToastUtil;

import java.util.Map;


/**
 * Created by apple on 16/12/17.
 */
public class VolleyRequest {
    private  RequestCallback callback;

    private RequestQueue mQueue;

    public  static String url = "http://192.168.1.228:8080/LiveVideo/chenlian";

    private String TAG = "VolleyRequest";

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
                     if (StringUtil.isNullOrBlank(responseFailObject.getInfo())){
                         ToastUtil.showToast(context,"请求失败");
                         return;
                     }

                     ToastUtil.showToast(context,responseFailObject.getInfo());

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

    public void downPicture(final Context context, ImageView imageView,String url){
        Log.i(TAG,"下载图片  url ：" + url);

        mQueue = Volley.newRequestQueue(context);

        ImageLoader imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
            }

            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });

        //第二和第三个参数分别设置默认图片  和 加载失败的参数
        //ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,
//                R.drawable.message_read, R.drawable.message_unread);
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,
                0, 0);

        imageLoader.get(url,listener);
    }

    public void uploadPicture(){

    }


    private static String JSESSIONID; //定义一个静态的字段，保存sessionID

    //如果参数 响应头 中有SET_COOKIT，说明是第一次连接，需要根据SET_COOKIT建立新的Cookit保存在sharePreference中
    public void setCookieFromHeader(Map<String, String> responseHeaders) {

        Map<String, String> responseHeaders

        if (responseHeaders.containsKey(SET_COOKIE)
                && responseHeaders.get(SET_COOKIE).startsWith(JSESSIONID)) {
            String cookie = responseHeaders.get(SET_COOKIE);
            if (cookie.length() > 0) {
                String[] splitCookie = cookie.split(";");
                String[] splitSessionId = splitCookie[0].split("=");
                cookie = splitSessionId[1];
                SharedPreferences.Editor prefEditor = preferences.edit();
                prefEditor.putString(JSESSIONID, cookie);
                prefEditor.apply();
            }
        }
    }
}
