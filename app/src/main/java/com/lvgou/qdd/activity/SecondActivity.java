package com.lvgou.qdd.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lvgou.qdd.R;
import com.lvgou.qdd.view.NavigtionView;

public class SecondActivity extends AppCompatActivity {

    private NavigtionView navigtionView;

    private Button leftButton;

    private  Button rightButton;

    private TextView middleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

//        navigtionView = (NavigtionView)findViewById(R.id.nav);
//
//        navigtionView.setMessage("我的acitivity");
//
//        navigtionView.setLeftButtonImage(R.drawable.icon_home);
//
//        navigtionView.setRightButtonImage(R.drawable.icon_calendar);

        leftButton = (Button)findViewById(R.id.leftButton);
//        leftButton.setBackgroundResource(R.mipmap.menu);

        middleText = (TextView)findViewById(R.id.middleMessage);
//        middleText.setText("我的列表");
        TextPaint tp = middleText.getPaint();
        tp.setFakeBoldText(true);

        rightButton = (Button)findViewById(R.id.rightButton);
//        rightButton.setBackgroundResource(R.mipmap.menu);

        setListener();

    }


    private  void  setListener(){
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info","kkkkkkkkkkkk");
                finish();
            }
        });



    }
}
