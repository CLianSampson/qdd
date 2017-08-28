package com.lvgou.qdd.http.volleyUpload;

/**
 * Created by sampson on 2017/7/31.
 */

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moon.zhong on 2015/3/3.
 */
public class UploadApi {

    /**
     * 上传图片接口
     * @param bitmap 需要上传的图片
     * @param listener 请求回调
     */
    public static void uploadImg(Bitmap bitmap, ResponseListener listener, Context context, String url){
        List<FormImage> imageList = new ArrayList<FormImage>() ;
        imageList.add(new FormImage(bitmap)) ;
        Request request = new PostUploadRequest(url,imageList,listener) ;
        VolleyUtil.getRequestQueue(context).add(request) ;
    }
}
