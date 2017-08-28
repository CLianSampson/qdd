package com.lvgou.qdd.http;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by sampson on 2017/8/28.
 */

public class BitmapCache implements ImageLoader.ImageCache {
    private static final String TAG = "BitmapCache";


    //LruCache对象
    private static LruCache<String, Bitmap> lruCache ;
    //设置最大缓存为100Mb，大于这个值会启动自动回收
    private int max = 100*1024*1024;

    public BitmapCache(){
        if (lruCache==null){
            //初始化 LruCache
            lruCache = new LruCache<String, Bitmap>(max){
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes()*value.getHeight();
                }
            };
        }
    }

    @Override
    public Bitmap getBitmap(String url) {

        Log.i(TAG,"获取缓存成功  url :" + url);

        Bitmap bitmap = lruCache.get(url);
        Log.i(TAG,"获取缓存成功 :" + bitmap);

        return lruCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {

        Log.i(TAG,"设置缓存成功  url :" + url);
        Log.i(TAG,"设置缓存成功  :" + bitmap);

        lruCache.put(url, bitmap);
    }

//    作者：食梦兽
//    链接：http://www.jianshu.com/p/d9072d3d00be
//    來源：简书
//    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
}
