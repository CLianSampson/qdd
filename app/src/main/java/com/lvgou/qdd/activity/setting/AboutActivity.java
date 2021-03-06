package com.lvgou.qdd.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;

public class AboutActivity extends BaseActivity {

    private Button backButton;

    private RelativeLayout helpButton;

    private RelativeLayout commentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_about);

        backButton = (Button) findViewById(R.id.AboutActivity_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        helpButton = (RelativeLayout) findViewById(R.id.AboutActivity_help);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HelpListActivity.class);
                startActivity(intent);
            }
        });


        commentButton = (RelativeLayout) findViewById(R.id.AboutActivity_comment);
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CommentActivity.class);
                startActivity(intent);
            }
        });


    }
}
