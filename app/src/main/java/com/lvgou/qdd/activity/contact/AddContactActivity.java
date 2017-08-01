package com.lvgou.qdd.activity.contact;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;

public class AddContactActivity extends BaseActivity {

    private Button backButton;

    private TextView nameTextView;

    private TextView accountTextview;

    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_contact);

        backButton = (Button) findViewById(R.id.AddContactActivity_backButton);
        nameTextView = (TextView) findViewById(R.id.AddContactActivity_account);
        accountTextview = (TextView) findViewById(R.id.AddContactActivity_name);
        addButton = (Button) findViewById(R.id.AddContactActivity_add);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();;
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
