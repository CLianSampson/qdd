package com.lvgou.qdd.http;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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

    public  void getRequest(Context context){
        mQueue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", response);

                        callback.sucess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);

                callback.fail(error);
            }
        });

        mQueue.add(request);
    }

    public  void  psotRequest(Context context, final Map<String,String> params){
        mQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {

                 Log.i("aa", "post请求成功" + response);

                 callback.sucess(response);

             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 Log.i("aa", "post请求失败" + error.toString());

                 callback.fail(error);
             }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
//                Map<String, String > map = new HashMap<String, String >();
//                map.put("name1", "value1");
//                map.put("name2", "value2");

                return params;
            }
        };


//        JSONObject jsonObject=new JSONObject(params);
//        //第二个参数我们传了user=zhangqi。则请求方法就为post
//        JsonObjectRequest request = new JsonObjectRequest(url, jsonObject,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject obj) {
//                        Log.i("aa", "post请求成功" + obj);
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                  Log.i("aa", "post请求失败" + error.toString());
//
//
//            }
//        });
        mQueue.add(request);
    }

}
