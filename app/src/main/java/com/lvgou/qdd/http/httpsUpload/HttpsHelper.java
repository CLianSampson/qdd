package com.lvgou.qdd.http.httpsUpload;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by sampson on 2017/8/24.
 */

public class HttpsHelper {

    public static final String PREFIX = "--";
    public static final String LINE_END = "\r\n";
    public static final String BOUNDARY = "*****";

    private HttpsURLConnection connection = null;
    private StringBuilder response = null;

    public void prepareHttpsConnection(String url) throws KeyManagementException, NoSuchAlgorithmException, IOException {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[]{new MyTrustManager()}, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());
        connection = (HttpsURLConnection) new URL(url).openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setConnectTimeout(10000);
    }

    public int uploadFile(String filename) throws IOException {
        int result = 0;
        if (filename == null)
            return result;
        connection.setUseCaches(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Charset", "utf-8");
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);
        DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
        dos.writeBytes(PREFIX + BOUNDARY + LINE_END);
        dos.writeBytes("Content-Disposition: form-data; " +
                "name=\"file1\";filename=\"" +
                filename.substring(filename.lastIndexOf("/")) + "\"" + LINE_END);
        dos.writeBytes(LINE_END);
        int len;
        InputStream ins = new FileInputStream(new File(filename));
        byte[] buffer = new byte[1024];
        while ((len = ins.read(buffer)) != -1) {
            dos.write(buffer, 0, len);
        }
        ins.close();
        dos.writeBytes(LINE_END);
        dos.writeBytes(PREFIX + BOUNDARY + PREFIX + LINE_END);
        dos.close();
        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            ins = connection.getInputStream();
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = ins.read()) != -1) {
                b.append((char) ch);
            }
            Log.i("bbb", b.toString());
        }
        return responseCode;
    }

    /**
     *
     * @param despath 存储的位置
     * @throws IOException
     */
    public void downloadFile(String despath) throws IOException {
        connection.connect();
        InputStream ins = connection.getInputStream();
        File file = new File(despath);
        if (file.exists()){
            IOException e = new IOException("File exists");
            throw e;
        }
        OutputStream out = null;
        byte[] buffer = new byte[1024];
        int len;
        try {
            out = new FileOutputStream(file);
            while ((len = ins.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (Exception e){

        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e){

                }
            }
        }
    }

    private static class MyHostnameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static class MyTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }


    public int uploadBitmap(Bitmap bitmap) throws IOException {
        int result = 0;
        if (bitmap == null)
            return result;
        connection.setUseCaches(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Charset", "utf-8");
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);
        DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
        dos.writeBytes(PREFIX + BOUNDARY + LINE_END);
        dos.writeBytes("Content-Disposition: form-data; " +
                "name=\"sign\";filename=\"" +
                "12345.png" + "\"" + LINE_END);
        dos.writeBytes(LINE_END);
//        int len;
//        InputStream ins = new FileInputStream(new File(filename));
//        byte[] buffer = new byte[1024];
//        while ((len = ins.read(buffer)) != -1) {
//            dos.write(buffer, 0, len);
//        }

        byte[] bytes = getValue(bitmap);
        dos.write(bytes, 0, bytes.length);


//        ins.close();
        dos.writeBytes(LINE_END);
        dos.writeBytes(PREFIX + BOUNDARY + PREFIX + LINE_END);
        dos.close();
        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            InputStream ins = connection.getInputStream();
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = ins.read()) != -1) {
                b.append((char) ch);
            }
            Log.i("bbb", b.toString());
        }
        return responseCode;
    }

    //对图片进行二进制转换
    public static byte[] getValue(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,bos) ;
        return bos.toByteArray();
    }


}
