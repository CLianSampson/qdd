package com.lvgou.qdd.activity.signature;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;

public class AddSignatureActivity extends BaseActivity {

    private Button backButton;
    private Button completeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_signature);

        backButton = (Button) findViewById(R.id.AddSignatureActivity_backButton);
        completeButton = (Button) findViewById(R.id.AddSignatureActivity_complete);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        completeButton.setBackgroundColor(Color.TRANSPARENT);
    }
}
