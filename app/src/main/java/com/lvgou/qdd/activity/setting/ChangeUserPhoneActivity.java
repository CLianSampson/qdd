package com.lvgou.qdd.activity.setting;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;

public class ChangeUserPhoneActivity extends BaseActivity {

    private Button backButton;

    private Button completeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_change_user_phone);


        backButton = (Button) findViewById(R.id.ChangeUserPhoneActivity_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        completeButton = (Button) findViewById(R.id.ChangeUserPhoneActivity_complete);
        completeButton.setBackgroundColor(Color.TRANSPARENT);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }


    @Override
    protected void netRequest() {
        super.netRequest();

    }




}
