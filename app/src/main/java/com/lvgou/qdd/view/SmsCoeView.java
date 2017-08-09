package com.lvgou.qdd.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.qdd.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sampson on 2017/8/9.
 */

public class SmsCoeView extends RelativeLayout {

    private EditText editText;

    private TextView getSmsCodeButton;

    private  static  int flag = 60;

    public SmsCoeView(Context context, AttributeSet attrs, int defStyle) {
        super(context,attrs,defStyle);

        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sms_code, this);
        editText = (EditText) findViewById(R.id.inputSmsCode);
        getSmsCodeButton = (TextView) findViewById(R.id.getSmsCode);

        getSmsCodeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        flag--;
                        getSmsCodeButton.setText(flag + " " + "s");
                        getSmsCodeButton.setBackgroundColor(Color.GRAY);
                        getSmsCodeButton.setClickable(false);


                        if (flag>0){
                            return;
                        }

                        getSmsCodeButton.setText("获取验证码");
                        getSmsCodeButton.setBackgroundColor(Color.BLUE);
                        getSmsCodeButton.setClickable(true);

                    }
                },1000,1000); //1s后  每隔1s执行一次
            }
        });


    }





}
