package com.lvgou.qdd.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.activity.LoginActivity;
import com.lvgou.qdd.util.StorageUtil;

public class SettingActivity extends BaseActivity {

    private Button backButton;

    private Button logoutButton;

    private Button account;

    private Button security;

    private Button about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);

        backButton = (Button) findViewById(R.id.SettingActivity_backButton);
        logoutButton = (Button) findViewById(R.id.SettingActivity_logout);

        account = (Button) findViewById(R.id.SettingActivity_account);
        security = (Button) findViewById(R.id.SettingActivity_security);
        about = (Button) findViewById(R.id.SettingActivity_about);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AccountActivity.class));
            }
        });

        security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SecurityActivity.class));
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AboutActivity.class));
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = "";
                StorageUtil.storeData(getApplicationContext(),StorageUtil.TOKEN,token);

                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }




}
