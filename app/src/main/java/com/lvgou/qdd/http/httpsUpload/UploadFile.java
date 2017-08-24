package com.lvgou.qdd.http.httpsUpload;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by sampson on 2017/8/24.
 */

public class UploadFile extends Thread{
    String path;
    String url;

    public UploadFile(String path, String url) {
        this.path = path;
        this.url = url;
    }


    @Override
    public void run() {
        HttpsHelper httpsHelper = new HttpsHelper();
        try {
            httpsHelper.prepareHttpsConnection(url);
            httpsHelper.uploadFile(path);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

