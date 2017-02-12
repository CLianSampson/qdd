package com.lvgou.qdd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lvgou.qdd.R;
import com.lvgou.qdd.http.RequestCallback;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

public class MainActivity extends BaseActivity implements RequestCallback,View.OnClickListener{
    private ResideMenu resideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);


        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);

        // create menu items;
        String titles[] = { "Home", "Profile", "Calendar", "Settings" };
        int icon[] = { R.drawable.icon_home, R.drawable.icon_profile, R.drawable.icon_calendar, R.drawable.icon_settings };

        for (int i = 0; i < titles.length; i++){
            ResideMenuItem item = new ResideMenuItem(this, icon[i], titles[i]);
            item.setOnClickListener(this);
            resideMenu.addMenuItem(item,  ResideMenu.DIRECTION_LEFT); // or  ResideMenu.DIRECTION_RIGHT
        }



        Log.i("test", "nnnnnnnnnnnnnnnnnn");

        String[] testStr = {"a","b"};
//        String str = testStr[2];
//        Log.i("test", str);

        Log.i("test", "aaaaaaaaaaaaaaaaaa");



//        netRequest();

    }

    @Override
    public void onClick(View v){

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
