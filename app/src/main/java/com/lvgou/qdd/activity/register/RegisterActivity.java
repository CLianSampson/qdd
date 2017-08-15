package com.lvgou.qdd.activity.register;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;

public class RegisterActivity extends BaseActivity implements EnterpriseFragment.IEnterpriseFragment{

    private Button backButton;

    private Button userButton;

    private Button enterpriseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);

        backButton = (Button) findViewById(R.id.RegisterActivity_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        userButton = (Button) findViewById(R.id.RegisterActivity_user);
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               gotoUser();
            }
        });


        enterpriseButton = (Button) findViewById(R.id.RegisterActivity_enterprise);
        enterpriseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoEnterPrise();
            }
        });

        setDefaultFragment();
    }


    private  void  setDefaultFragment(){

        FragmentManager fm = getFragmentManager();

        FragmentTransaction transaction = fm.beginTransaction();
        UserFragment userFragment = new UserFragment();
        transaction.add(R.id.RegisterActivity_fragment_container,userFragment);
        transaction.commit();
    }

    private void gotoEnterPrise(){
        userButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
        enterpriseButton.setBackgroundResource(R.drawable.shape_nav_indicator);

        //设置字体颜色
        enterpriseButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));
        userButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));


        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        EnterpriseFragment enterpriseFragment = new EnterpriseFragment();


        transaction.replace(R.id.RegisterActivity_fragment_container, enterpriseFragment);
        transaction.commit();
    }


    private void gotoUser(){
        enterpriseButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
        userButton.setBackgroundResource(R.drawable.shape_nav_indicator);

        //设置字体颜色
        userButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));
        enterpriseButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        UserFragment userFragment = new UserFragment();


        transaction.replace(R.id.RegisterActivity_fragment_container, userFragment);
        transaction.commit();
    }

    @Override
    public void IEnterpriseFragmentMethod() {

    }
}
