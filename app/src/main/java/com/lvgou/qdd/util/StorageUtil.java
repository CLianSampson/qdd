package com.lvgou.qdd.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * Created by sampson on 2017/7/14.
 */

public class StorageUtil {
    private static  String SHARED_PREFERENCES = "SharedPreferences";

    public static  String TOKEN = "token";

    public static  String PHONE = "phone";

    public static  String ACCOUNT_FLAG = "account_flag";

    public static  String VERIFY_STATE = "verify_state";

    public static void storeData(Context context,String key,String data){
       SharedPreferences settings = context.getSharedPreferences(StorageUtil.SHARED_PREFERENCES, context.MODE_PRIVATE);
       SharedPreferences.Editor editor = settings.edit();

        editor.putString(key,data);

       // 提交本次编辑
       editor.commit();
   }

    public static String getData(Context context,String key){
        SharedPreferences settings = context.getSharedPreferences(StorageUtil.SHARED_PREFERENCES, context.MODE_PRIVATE);
        Map<String,?> map = settings.getAll();
        return (String) map.get(key);
    }
}
