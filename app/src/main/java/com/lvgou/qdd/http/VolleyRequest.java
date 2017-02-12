package com.lvgou.qdd.http;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by apple on 16/12/17.
 */
public class VolleyRequest {
    private  RequestCallback callback;

    private RequestQueue mQueue;

    private  final  static String url = "http://192.168.1.228:8080/LiveVideo/chenlian";

    public void setCallback(RequestCallback callback){
        this.callback=callback;
    }

    public  void  getMethod(Context context){
        mQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(url,
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

        mQueue.add(stringRequest);
    }



}
