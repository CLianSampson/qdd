package com.lvgou.qdd.util;

/**
 * Created by sampson on 2017/7/14.
 */

public class StringUtil {

    public static boolean isNullOrBlank(String string){
        if (string == null){
            return  true;
        }

        if (string.equals(" ")){
            return  true;
        }

        if (string.equals("")){
            return  true;
        }

        return  false;
    }
}
