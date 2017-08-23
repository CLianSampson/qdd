package com.lvgou.qdd.activity.sign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.activity.HomeActivity;

public class SignSucessActivity extends BaseActivity {

    private Button backbutton;

    private Button gohomeButton;

    private TextView remainTimestext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sign_sucess);

        backbutton = (Button) findViewById(R.id.SignSucessActivity_backButton);
        gohomeButton = (Button) findViewById(R.id.SignSucessActivity_gohome);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        gohomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        remainTimestext = (TextView) findViewById(R.id.SignSucessActivity_remainTimes);
        remainTimestext.setText("您的套餐剩余次数" + getIntent().getStringExtra("remainTimes"));
    }


}
