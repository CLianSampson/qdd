package com.lvgou.qdd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lvgou.qdd.R;
import com.lvgou.qdd.fragment.CalendarFragment;
import com.lvgou.qdd.fragment.ProfileFragment;
import com.lvgou.qdd.fragment.SettingsFragment;
import com.lvgou.qdd.http.RequestCallback;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

public class MainActivity extends BaseActivity implements RequestCallback,View.OnClickListener{
    private  static  String TAG = "MainActivity";

    private ResideMenu resideMenu;
    private MainActivity mContext;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemProfile;
    private ResideMenuItem itemCalendar;
    private ResideMenuItem itemSettings;

    private TextView waitForMe;
    private TextView waitForOther;
    private TextView haveComplete;
    private TextView timeOut;

    private  View  firstPageSepreate; //button点击平移

//    private UserIconView userIconView;

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
        setButton();
        setUpMenu();
//        if( savedInstanceState == null )
//            changeFragment(new HomeFragment());


//        netRequest();

    }

    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setUse3D(false);
        //设置背景图片
//        resideMenu.setBackground(R.mipmap.menu);
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

//        userIconView = new UserIconView(this);

        itemHome.setOnClickListener(this);
        itemProfile.setOnClickListener(this);
        itemCalendar.setOnClickListener(this);
        itemSettings.setOnClickListener(this);

//        resideMenu.addUserIcon(userIconView, ResideMenu.DIRECTION_LEFT);


        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_RIGHT);

        resideMenu.addMenuItem(test, ResideMenu.DIRECTION_LEFT);

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
    public void onClick(View view) {

        Log.i(TAG,"button click..........");

        if (view == itemHome){
//            changeFragment(new HomeFragment());
            Intent intent = new Intent(this,SecondActivity.class);
            startActivity(intent);
            resideMenu.closeMenu();
        }else if (view == itemProfile){
            changeFragment(new ProfileFragment());
            resideMenu.closeMenu();
        }else if (view == itemCalendar){
            changeFragment(new CalendarFragment());
            resideMenu.closeMenu();
        }else if (view == itemSettings){
            changeFragment(new SettingsFragment());
            resideMenu.closeMenu();
        }else if (view == waitForMe){
            waitForMe();
        }else if (view == waitForOther){
            waitForOther();
        }else if (view == haveComplete){
            haveComplete();
        }else if (view == timeOut){
            timeOut();
        }


    }

    private void waitForMe(){

        //View代表方法传入的控件
        int[] firstPageSepreateLocation = new int[2];
        firstPageSepreate.getLocationInWindow(firstPageSepreateLocation);
        int fromX = firstPageSepreateLocation[0]; // x 坐标
        int fromY = firstPageSepreateLocation[1]; // y 坐标


        int[] waitForMeLocation = new int[2];
        waitForMe.getLocationInWindow(waitForMeLocation);
        int toX = waitForMeLocation[0]; // x 坐标
        int toY = waitForMeLocation[1]; // y 坐标

        TranslateAnimation translateAnimation  = new TranslateAnimation(fromX,toX-fromX ,0,0);
        translateAnimation.setDuration(1000);
        //如果fillAfter的值为真的话，动画结束后，控件停留在执行后的状态
        translateAnimation.setFillAfter(true);

        firstPageSepreate.startAnimation(translateAnimation);

        setLocation(toX-fromX);

    }


    private void waitForOther(){

        int[] firstPageSepreateLocation = new int[2];
        firstPageSepreate.getLocationInWindow(firstPageSepreateLocation);
        int fromX = firstPageSepreateLocation[0]; // x 坐标
        int fromY = firstPageSepreateLocation[1]; // y 坐标


        int[] waitForOtherLocation = new int[2];
        waitForOther.getLocationInWindow(waitForOtherLocation);
        int toX = waitForOtherLocation[0]; // x 坐标
        int toY = waitForOtherLocation[1]; // y 坐标


        TranslateAnimation translateAnimation  = new TranslateAnimation(fromX,toX-fromX ,0,0);
        translateAnimation.setDuration(1000);
        //如果fillAfter的值为真的话，动画结束后，控件停留在执行后的状态
        translateAnimation.setFillAfter(true);

        firstPageSepreate.startAnimation(translateAnimation);

        setLocation(toX-fromX);

    }

    private void haveComplete(){
        //View代表方法传入的控件
        int[] firstPageSepreateLocation = new int[2];
        firstPageSepreate.getLocationInWindow(firstPageSepreateLocation);
        int fromX = firstPageSepreateLocation[0]; // x 坐标
        int fromY = firstPageSepreateLocation[1]; // y 坐标


        int[] haveCompleteLocation = new int[2];
        haveComplete.getLocationInWindow(haveCompleteLocation);
        int toX = haveCompleteLocation[0]; // x 坐标
        int toY = haveCompleteLocation[1]; // y 坐标

        TranslateAnimation translateAnimation  = new TranslateAnimation(fromX,toX-fromX ,0,0);
        translateAnimation.setDuration(1000);
        //如果fillAfter的值为真的话，动画结束后，控件停留在执行后的状态
        translateAnimation.setFillAfter(true);

        firstPageSepreate.startAnimation(translateAnimation);

        setLocation(toX-fromX);
    }

    private void timeOut(){
        //View代表方法传入的控件
        int[] firstPageSepreateLocation = new int[2];
        firstPageSepreate.getLocationInWindow(firstPageSepreateLocation);
        int fromX = firstPageSepreateLocation[0]; // x 坐标
        int fromY = firstPageSepreateLocation[1]; // y 坐标


        int[] timeOutLocation = new int[2];
        timeOut.getLocationInWindow(timeOutLocation);
        int toX = timeOutLocation[0]; // x 坐标
        int toY = timeOutLocation[1]; // y 坐标

        setLocation(toX-fromX);

    }

    private  void  setLocation(int offset){
        //克隆view的width、height、margin的值生成margin对象
        ViewGroup.MarginLayoutParams margin=new ViewGroup.MarginLayoutParams(firstPageSepreate.getLayoutParams());
        Log.i(TAG,"left is"+ margin.leftMargin);
        Log.i(TAG,"top is"+ margin.topMargin);
        Log.i(TAG,"right is"+ margin.rightMargin);
        Log.i(TAG,"bottom is"+ margin.bottomMargin);
        //设置新的边距
        margin.setMargins(margin.leftMargin + offset,margin.topMargin,margin.rightMargin,margin.bottomMargin);
        //把新的边距生成layoutParams对象
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
        //设制view的新的位置
        firstPageSepreate.setLayoutParams(layoutParams);
    }

    private  void  setButton(){
        waitForMe = (TextView) findViewById(R.id.waitForMe);
        waitForOther = (TextView) findViewById(R.id.waitForOther);
        haveComplete = (TextView) findViewById(R.id.haveComplete);
        timeOut = (TextView) findViewById(R.id.timeOut);

        waitForMe.setOnClickListener(this);
        waitForOther.setOnClickListener(this);
        haveComplete.setOnClickListener(this);
        timeOut.setOnClickListener(this);

        //android中不能在oncreate方法中 通过 getwidth() 直接获取控件宽高，因为此时，控件只是加载，但是还没有绘制
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        waitForMe.measure(w, h);
        int height = waitForMe.getMeasuredHeight();
        int width = waitForMe.getMeasuredWidth();


        //将分割线的宽度设置为与button等宽
        firstPageSepreate = findViewById(R.id.firstPageSepreate);
        Log.i(TAG,"width is" + width);
        RelativeLayout.LayoutParams relativeParams =(RelativeLayout.LayoutParams) firstPageSepreate.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
        relativeParams.width = width;// 控件的宽强制设成30
        firstPageSepreate.setLayoutParams(relativeParams); //使设置好的布局参数应用到控件


    }


    @Override
    protected  void netRequest(){
        super.netRequest();
        request.setCallback(this);
        request.getRequest(getApplicationContext());
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
