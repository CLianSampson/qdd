package com.lvgou.qdd.util;

/**
 * Created by sampson on 2017/7/14.
 */

public class StringUtil {

    public static boolean isNullOrBlank(String string) {
        if (string == null) {
            return true;
        }

        if (string.equals(" ")) {
            return true;
        }

        if (string.equals("")) {
            return true;
        }

        return false;
    }

    public static boolean isPhoneNum(String mobileNum) {
        String firstnumber = null;

        if (mobileNum.length() != 0) {
            firstnumber = mobileNum.substring(0, 1);
        }

        if (mobileNum.contains("@")) {
            return false;
        }


        if (mobileNum.length() == 11 && firstnumber.equals("1")) {
            return true;
        } else {
            return false;
        }


    }
}