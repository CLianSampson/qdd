package com.lvgou.qdd.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.shopping.ShoppingActivity;
import com.lvgou.qdd.http.URLConst;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;


public class HomeActivity extends BaseActivity implements View.OnClickListener{

    private ResideMenu resideMenu;
    private ResideMenuItem shopping;
    private ResideMenuItem mySignature;
    private ResideMenuItem contact;
    private ResideMenuItem myOrder;
    private ResideMenuItem settings;
    private HomeActivity mContext;

    private Button waitForMeButton;

    private Button waitOtherButton;

    private Button completeButton;

    private Button timeOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {

        setContentView(R.layout.activity_home);

        mContext = this;
        setButton();
        setUpMenu();
//        if( savedInstanceState == null )
//            changeFragment(new HomeFragment());


        netRequest();

    }

    private  void  setButton(){
        waitForMeButton =(Button) findViewById(R.id.waitForMeButton);
        waitForMeButton.setOnClickListener(this);
        waitOtherButton =(Button) findViewById(R.id.waitOtherButton);
        waitOtherButton.setOnClickListener(this);
        completeButton =(Button) findViewById(R.id.completeButton);
        completeButton.setOnClickListener(this);
        timeOutButton =(Button) findViewById(R.id.timeOutButton);
        timeOutButton.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
      if (view == shopping){
          Intent intent = new Intent(getApplicationContext(), ShoppingActivity.class);
          startActivity(intent);
          return;
      }

      switch (view.getId()){
          case R.id.waitForMeButton:
              //设置背景
              waitForMeButton.setBackgroundResource(R.drawable.shape_nav_indicator);

              waitOtherButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              completeButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              timeOutButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);

              //设置字体颜色
              waitForMeButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));

              waitOtherButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));
              completeButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));
              timeOutButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));

              break;
          case R.id.waitOtherButton:
              waitOtherButton.setBackgroundResource(R.drawable.shape_nav_indicator);

              waitForMeButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              completeButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              timeOutButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);

              //设置字体颜色
              waitOtherButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));

              waitForMeButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));
              completeButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));
              timeOutButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));

              break;

          case R.id.completeButton:
              completeButton.setBackgroundResource(R.drawable.shape_nav_indicator);

              waitOtherButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              waitForMeButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              timeOutButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);

              //设置字体颜色
              completeButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));

              waitOtherButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));
              waitForMeButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));
              timeOutButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));

              break;
          case R.id.timeOutButton:
              timeOutButton.setBackgroundResource(R.drawable.shape_nav_indicator);

              waitOtherButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              completeButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              waitForMeButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);

              //设置字体颜色
              timeOutButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));

              waitOtherButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));
              completeButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));
              waitForMeButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));

              break;

      }


    }

    /********************************** 抽屉效果开始 ************************************************/
    private  void  setUpMenu(){
        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setUse3D(false);
        //设置背景图片
        //resideMenu.setBackground(R.mipmap.menu);
//        resideMenu.setBackgroundColor(getResources().getColor(R.color.systemBlue));
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        shopping     = new ResideMenuItem(this, 0,     "购买套餐");
        mySignature  = new ResideMenuItem(this, 0,  "我的签名");
        contact = new ResideMenuItem(this, 0, "联系人");
        myOrder = new ResideMenuItem(this, 0, "我的订单");
        settings = new ResideMenuItem(this, 0, "设置");




//                userIconView = new UserIconView(this);

        shopping.setOnClickListener(this);
        mySignature.setOnClickListener(this);
        contact.setOnClickListener(this);
        myOrder.setOnClickListener(this);
        settings.setOnClickListener(this);

        //        resideMenu.addUserIcon(userIconView, ResideMenu.DIRECTION_LEFT);


        resideMenu.addMenuItem(shopping, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(mySignature, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(contact, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(myOrder, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(settings, ResideMenu.DIRECTION_LEFT);


        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);


        findViewById(R.id.leftButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
                //                resideMenu.setBackground(R.mipmap.menu);
            }
        });
        findViewById(R.id.rightButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
                //                resideMenu.setBackground(R.mipmap.menu);
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
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

    /********************************** 抽屉效果结束 ************************************************/



    protected void  netRequest() {
        super.netRequest();
        request.url = URLConst.URL_REGISTER;

//        MessageQueue

    }
}
