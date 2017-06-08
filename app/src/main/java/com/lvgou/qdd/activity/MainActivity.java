package com.lvgou.qdd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.lvgou.qdd.R;
import com.lvgou.qdd.fragment.CalendarFragment;
import com.lvgou.qdd.fragment.HomeFragment;
import com.lvgou.qdd.fragment.ProfileFragment;
import com.lvgou.qdd.fragment.SettingsFragment;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.view.UserIconView;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements RequestCallback,View.OnClickListener{
    private ResideMenu resideMenu;
    private MainActivity mContext;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemProfile;
    private ResideMenuItem itemCalendar;
    private ResideMenuItem itemSettings;

    private UserIconView userIconView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
//        setContentView(R.layout.activity_main);
//        setContentView(R.layout.main);
        setContentView(R.layout.activity_second);

        mContext = this;
        setUpMenu();
        if( savedInstanceState == null )
            changeFragment(new HomeFragment());



        Log.i("test", "nnnnnnnnnnnnnnnnnn");

        String[] testStr = {"a","b"};
//        String str = testStr[2];
//        Log.i("test", str);

        Log.i("test", "aaaaaaaaaaaaaaaaaa");

//        netRequest();

    }

    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setUse3D(true);
        resideMenu.setBackground(R.mipmap.menu);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        itemHome     = new ResideMenuItem(this, R.mipmap.menu,     "Home");
        itemProfile  = new ResideMenuItem(this, R.mipmap.menu,  "Profile");
        itemCalendar = new ResideMenuItem(this, R.mipmap.menu, "Calendar");
        itemSettings = new ResideMenuItem(this, R.mipmap.menu, "Settings");

        ResideMenuItem test = new ResideMenuItem(this,R.mipmap.menu,"test");

        userIconView = new UserIconView(this);

        itemHome.setOnClickListener(this);
        itemProfile.setOnClickListener(this);
        itemCalendar.setOnClickListener(this);
        itemSettings.setOnClickListener(this);

        resideMenu.addUserIcon(userIconView, ResideMenu.DIRECTION_LEFT);


        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_RIGHT);

        resideMenu.addMenuItem(test, ResideMenu.DIRECTION_LEFT);

        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
                resideMenu.setBackground(R.mipmap.menu);
            }
        });
        findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
                resideMenu.setBackground(R.mipmap.menu);
            }
        });
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {

        Log.i("info","button click..........");

        if (view == itemHome){
//            changeFragment(new HomeFragment());
            Intent intent = new Intent(this,SecondActivity.class);
            startActivity(intent);
        }else if (view == itemProfile){
            changeFragment(new ProfileFragment());
        }else if (view == itemCalendar){
            changeFragment(new CalendarFragment());
        }else if (view == itemSettings){
            changeFragment(new SettingsFragment());
        }else if (view == userIconView){
            Intent intent = new Intent(this,SecondActivity.class);
            startActivity(intent);
        }

//        resideMenu.closeMenu();
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
            Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };

    private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    // What good method is to access resideMenu？
    public ResideMenu getResideMenu(){
        return resideMenu;
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
