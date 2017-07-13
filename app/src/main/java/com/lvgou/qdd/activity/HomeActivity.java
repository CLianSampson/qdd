package com.lvgou.qdd.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lvgou.qdd.R;
import com.lvgou.qdd.http.URLConst;


public class HomeActivity extends BaseActivity implements View.OnClickListener{

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


    private  void  setUpMenu(){

    }

    protected void  netRequest() {
        super.netRequest();
        request.url = URLConst.URL_REGISTER;

//        MessageQueue

    }
}
