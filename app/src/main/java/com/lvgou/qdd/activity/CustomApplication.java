package com.lvgou.qdd.activity;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.RequestQueue;

import java.util.Map;

/**
 * Created by sampson on 2017/8/28.
 *
 * volley session问题详见   http://blog.csdn.net/qq_16051379/article/details/50358254
 */

public class CustomApplication extends Application {

    private static final  String TAG = "CustomApplication";

    /************************************以下为session处理相关*********************************************/
    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String COOKIE_KEY = "Cookie";
//    private static final String SESSION_COOKIE = "JSESSIONID";
    private static final String SESSION_COOKIE = "PHPSESSID";

    private static CustomApplication instance;
//    private RequestQueue requestQueue;
    private SharedPreferences preferences;

    public static CustomApplication newInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        requestQueue = Volley.newRequestQueue(this);
    }

    public RequestQueue getRequestQueue() {
//        return requestQueue;
        return null;
    }


    /**
     * 检查返回的Response header中有没有session
     * @param responseHeaders Response Headers.
     */
    public final void checkSessionCookie(Map<String, String> responseHeaders) {

        Log.i(TAG,"requestHeaders is : " + responseHeaders.toString());

        if (responseHeaders.containsKey(SET_COOKIE_KEY)
                && responseHeaders.get(SET_COOKIE_KEY).startsWith(SESSION_COOKIE)) {
            String cookie = responseHeaders.get(SET_COOKIE_KEY);
            if (cookie.length() > 0) {
                String[] splitCookie = cookie.split(";");
                String[] splitSessionId = splitCookie[0].split("=");
                cookie = splitSessionId[1];
                SharedPreferences.Editor prefEditor = preferences.edit();
                prefEditor.putString(SESSION_COOKIE, cookie);
                prefEditor.commit();
            }
        }
    }

    /**
     * 添加session到Request header中
     */
    public final void addSessionCookie(Map<String, String> requestHeaders) {
        String sessionId = preferences.getString(SESSION_COOKIE, "");

        Log.i(TAG,"sessionId is : " + sessionId);

        if (sessionId.length() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append(SESSION_COOKIE);
            builder.append("=");
            builder.append(sessionId);
            if (requestHeaders.containsKey(COOKIE_KEY)) {
                builder.append("; ");
                builder.append(requestHeaders.get(COOKIE_KEY));
            }
            requestHeaders.put(COOKIE_KEY, builder.toString());

            Log.i(TAG,"requestHeaders is : " + requestHeaders);
        }
    }
    /************************************以下为session处理相关*********************************************/


}
