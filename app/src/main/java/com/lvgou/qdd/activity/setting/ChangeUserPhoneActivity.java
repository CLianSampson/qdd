package com.lvgou.qdd.activity.setting;

import android.os.Bundle;

import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;

public class ChangeUserPhoneActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_change_user_phone);
    }


}
