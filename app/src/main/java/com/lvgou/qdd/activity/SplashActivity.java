package com.lvgou.qdd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.lvgou.qdd.R;
import com.lvgou.qdd.util.StorageUtil;
import com.lvgou.qdd.util.StringUtil;
import com.lvgou.qdd.util.TokenUtil;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        childImpl(savedInstanceState);
    }

    protected  void childImpl(Bundle savedInstanceState){

        setContentView(R.layout.activity_splash);

        //Android对UI主线程开启了实时监听，
        // Activity Manager和WindowManager系统服务一旦监听到主线程超过10秒没有响应操作，就会抛出ANR，
        // 所以，在UI主线程中不能直接调用Thread.sleep方法去延时，超过10秒就根本不会执行后面的操作，
        // 当有必要在主线程中增加延时处理时，可以通过开启子线程的方法：
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                enterHomeActivity();
            }
        }, 3000);
    }

    private void enterHomeActivity() {
        String token = StorageUtil.getData(getApplicationContext(),StorageUtil.TOKEN);
        TokenUtil.token = token;
        if (StringUtil.isNullOrBlank(token)){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }


    }
}
