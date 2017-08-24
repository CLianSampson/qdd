package com.lvgou.qdd.http.httpsUpload;

import android.graphics.Bitmap;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by sampson on 2017/8/24.
 */

public class UploadBitmap extends Thread{

    Bitmap bitmap;
    String url;

    public UploadBitmap(Bitmap bitmap, String url) {
        this.bitmap = bitmap;
        this.url = url;
    }


    @Override
    public void run() {
        HttpsHelper httpsHelper = new HttpsHelper();
        try {
            httpsHelper.prepareHttpsConnection(url);
            httpsHelper.uploadBitmap(bitmap);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
