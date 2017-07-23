package com.lvgou.qdd.activity.message;

import android.os.Bundle;

import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;

public class MessageDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        childImpl(savedInstanceState);
    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {

    }

    @Override
    protected void netRequest() {
        super.netRequest();
    }
}
