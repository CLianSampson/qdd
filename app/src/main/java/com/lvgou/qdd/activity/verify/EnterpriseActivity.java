package com.lvgou.qdd.activity.verify;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;

public class EnterpriseActivity extends BaseActivity {

    private Button backbutton;

    private Button completeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_enterprise);

        setView();
    }


    private void setView(){
        backbutton = (Button) findViewById(R.id.EnterpriseActivity_backButton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        completeButton = (Button) findViewById(R.id.EnterpriseActivity_complete);
        completeButton.setBackgroundColor(Color.TRANSPARENT);


    }
}
