package com.lvgou.qdd.http.session;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.lvgou.qdd.activity.CustomApplication;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sampson on 2017/8/28.
 */

public class PostStringRequest extends Request<String>{

    private static String TAG = "PostStringRequest";

    private final Response.Listener<String> mListener;
    private Map<String, String> mParams;

    public PostStringRequest(int method, String url, Response.Listener<String> listener,
                             Response.ErrorListener errorListener)
    {
        super(method, url, errorListener);
        mListener = listener;
    }

    public PostStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        this(Method.GET, url, listener, errorListener);
    }

    public PostStringRequest(String url, Map<String, String> mParams, Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        this(Method.POST, url, listener, errorListener);
        this.mParams =mParams;

    }


    @Override
    protected void deliverResponse(String response)
    {
        mListener.onResponse(response);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response)
    {
        CustomApplication.newInstance().checkSessionCookie(response.headers);
        String parsed;
        try
        {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e)
        {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

//    @Override
//    public Map<String, String> getParams() {
//        return mParams;
//    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError
    {
        Map<String, String> headers = super.getHeaders();

        if (headers == null
                || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }

        CustomApplication.newInstance().addSessionCookie(headers);

        Log.i(TAG,"header is :" +headers.toString() );

        return headers;
    }


}
