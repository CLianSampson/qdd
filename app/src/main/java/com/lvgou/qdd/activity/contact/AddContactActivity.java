package com.lvgou.qdd.activity.contact;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.util.Logger;

public class AddContactActivity extends BaseActivity implements NoContactFragment.CallBackValue{

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
                finish();
            }
        });

        setDefaultFragment();
    }

    private  void  setDefaultFragment(){

        FragmentManager fm = getFragmentManager();

        FragmentTransaction transaction = fm.beginTransaction();
        NoContactFragment noContactFragment = new NoContactFragment();
        transaction.add(R.id.AddContactActivity_fragment_container,noContactFragment);
        transaction.commit();
    }

    @Override
    public void doListener(JSONObject jsonObject) {
        Logger.getInstance(getApplicationContext()).info("++++++++++++++++++++++fragment 接口回调成功");
        Logger.getInstance(getApplicationContext()).info("++++++++++++++++++++++fragment 接口回调成功");

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        HaveContactFragment haveContactFragment = new HaveContactFragment();

        Bundle bundle = new Bundle();
        bundle.putString("userInfo", JSON.toJSONString(jsonObject));
        haveContactFragment.setArguments(bundle);

        transaction.replace(R.id.AddContactActivity_fragment_container, haveContactFragment);
        transaction.commit();
    }
}
