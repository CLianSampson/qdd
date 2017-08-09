package com.lvgou.qdd.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;

public class UserPhoneActivity extends BaseActivity {

    private Button backButton;

    private TextView userPhone;

    private Button changePhoneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_phone);

        backButton = (Button) findViewById(R.id.UserPhoneActivity_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        userPhone = (TextView) findViewById(R.id.UserPhoneActivity_phone);
        userPhone.setText(getIntent().getStringExtra("phone"));


        changePhoneButton = (Button) findViewById(R.id.UserPhoneActivity_changePhone);
        changePhoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ChangeUserPhoneActivity.class));
            }
        });

    }




}
