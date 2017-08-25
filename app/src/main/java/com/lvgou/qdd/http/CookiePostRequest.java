package com.lvgou.qdd.http;

import android.content.SharedPreferences;
import android.util.Log;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sampson on 2017/8/25.
 */

public class CookiePostRequest extends Request<com.alibaba.fastjson.JSONObject> {

    private Map<String, String> mMap;
    private Response.Listener<JSONObject> mListener;
    private String mHeader;
    private Map<String, String> sendHeader=new HashMap<String, String>(1);

    public CookiePostRequest(String url, Map map, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener){
        super(Method.POST, url, errorListener);
        mListener = listener;
        mMap = map;
    }

    //当http请求是post时，则需要该使用该函数设置往里面添加的键值对    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mMap;
    }

    @Override
    protected Response<JSONObject>parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =   new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            //获取头部信息
            mHeader = response.headers.toString();
            Log.w("LOG","get headers in parseNetworkResponse "+response.headers.toString());
            //获取cookie头部信息
            Map<String, String> responseHeaders = response.headers;
            String rawCookies = responseHeaders.get("Set-Cookie");

            Log.i("CookiePostRequest",rawCookies);

            //;分隔获取sessionid
            String[] splitCookie = rawCookies.split(";");
            //使用SharedPreferences本地存储
            SharedPreferences sp = BnxApplication.getInstance().getSharedPreferences("CookiePrefsFile",0);
            SharedPreferences.Editor prefsWriter = sp.edit();
            prefsWriter.putString("cookie_",splitCookie[0]);
            prefsWriter.commit();//
            //将cookie字符串添加到jsonObject中，该jsonObject会被deliverResponse递交，调用请求时则能在onResponse中得到
            JSONObject jsonObject = new JSONObject(jsonString);
            //    jsonObject.put("Cookie",rawCookies);    //自行添加
            Log.w("LOG","jsonObject "+ response.toString());
            return Response.success(jsonObject,  HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        mListener.onResponse(response);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return sendHeader;
    }

//    作者：PokerMman181
//    链接：http://www.jianshu.com/p/8305a5021fef
//    來源：简书
//    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

}
