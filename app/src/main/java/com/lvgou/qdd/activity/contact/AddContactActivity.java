package com.lvgou.qdd.activity.contact;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;

public class AddContactActivity extends BaseActivity {

    private Button backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_contact);

        backButton = (Button) findViewById(R.id.AddContactActivity_backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();;
            }
        });





    }
}
