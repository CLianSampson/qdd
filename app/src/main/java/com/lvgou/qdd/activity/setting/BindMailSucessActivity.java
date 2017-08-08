package com.lvgou.qdd.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.activity.HomeActivity;

public class BindMailSucessActivity extends BaseActivity{

    private Button backButton;

    private Button gohomeButton;

    private TextView mailAccount;

    private TextView resendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_bind_mail_sucess);

        backButton = (Button) findViewById(R.id.BindMailSucessActivity_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        gohomeButton = (Button) findViewById(R.id.BindMailSucessActivity_goto_home);
        gohomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });

        resendButton = (TextView) findViewById(R.id. BindMailSucessActivity_resend);
        resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        mailAccount = (TextView) findViewById(R.id.BindMailSucessActivity_mail_account);
        mailAccount.setText(getIntent().getStringExtra("mail"));

    }



}
