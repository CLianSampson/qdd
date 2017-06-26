package com.lvgou.qdd.util;


import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by yaotaxi on 2017/6/23.
 */

public class ScreenUtil {
    private static Activity activity = new Activity();

    public  static int getScreenWidth(){
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）
        float density = metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）

        return width;
    }

    public  static int getScreenHeight(){
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);

        int height = metric.heightPixels;   // 屏幕高度（像素）

        return height;
    }

    public  static float getScreenDensity(){
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);

        float density = metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5）

        return density;
    }

    public  static  int getScreenDpi(){
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);

        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）

        return densityDpi;
    }
}

