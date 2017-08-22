package com.lvgou.qdd.activity.sign;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;

public class SignMobileVerifyActivity extends BaseActivity {

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sign_mobile_verify);

        backButton = (Button) findViewById(R.id.SignMobileVerifyActivity_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



}
