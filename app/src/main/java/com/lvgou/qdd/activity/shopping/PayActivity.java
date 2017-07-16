package com.lvgou.qdd.activity.shopping;

import android.os.Bundle;

import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;

public class PayActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        childImpl(savedInstanceState);
    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {

    }
}
