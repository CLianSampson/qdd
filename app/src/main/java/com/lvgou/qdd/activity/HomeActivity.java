package com.lvgou.qdd.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lvgou.qdd.R;


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


//        netRequest();

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
              waitForMeButton.setBackgroundResource(R.drawable.shape_nav_indicator);
              waitForMeButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));

              waitOtherButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              completeButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              timeOutButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              break;
          case R.id.waitOtherButton:
              waitOtherButton.setBackgroundResource(R.drawable.shape_nav_indicator);
              waitOtherButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));

              waitForMeButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              completeButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              timeOutButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);

              break;

          case R.id.completeButton:
              completeButton.setBackgroundResource(R.drawable.shape_nav_indicator);
              completeButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));

              waitOtherButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              waitForMeButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              timeOutButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);

              break;
          case R.id.timeOutButton:
              timeOutButton.setBackgroundResource(R.drawable.shape_nav_indicator);
              timeOutButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));

              waitOtherButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              completeButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              waitForMeButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);

              break;

      }


    }


    private  void  setUpMenu(){

    }
}
