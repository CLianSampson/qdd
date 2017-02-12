package com.lvgou.qdd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.lvgou.qdd.R;
import com.lvgou.qdd.http.RequestCallback;

public class MainActivity extends BaseActivity implements RequestCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        Log.i("test", "nnnnnnnnnnnnnnnnnn");

        String[] testStr = {"a","b"};
//        String str = testStr[2];
//        Log.i("test", str);

        Log.i("test", "aaaaaaaaaaaaaaaaaa");



        netRequest();

    }

    @Override
    protected  void netRequest(){
        super.netRequest();
        request.setCallback(this);
        request.getMethod(getApplicationContext());
    }

    @Override
    public void sucess(Object object) {
        String string = (String )object;
    }

    @Override
    public void fail(Object object) {
        Log.i("test", object.toString());

        Intent intent = new Intent(this,SecondActivity.class);
        startActivity(intent);

    }
}
