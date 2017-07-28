package com.lvgou.qdd.activity.signature;

import android.os.Bundle;

import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;

public class AddSignatureActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_signature);
    }
}
