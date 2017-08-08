package com.lvgou.qdd.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.http.URLConst;
import com.lvgou.qdd.util.Logger;
import com.lvgou.qdd.util.StringUtil;
import com.lvgou.qdd.util.TokenUtil;

public class UserAccountActivity extends BaseActivity {

    private Button backButton;

    private TextView nameFront;
    private TextView nameTextView;

    private TextView accountFront;
    private TextView accountTextView;

    private TextView idNumFront;
    private TextView idNumTextView;

    private TextView mailFront;
    private TextView mailTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_account);

        backButton = (Button) findViewById(R.id.UserAccountActivity_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nameFront = (TextView) findViewById(R.id.UserAccountActivity_name_front);
        nameTextView = (TextView) findViewById(R.id.UserAccountActivity_name);

        accountFront = (TextView) findViewById(R.id.UserAccountActivity_account_front);
        accountTextView = (TextView) findViewById(R.id.UserAccountActivity_account);

        idNumFront = (TextView) findViewById(R.id.UserAccountActivity_idNum_front);
        idNumTextView = (TextView) findViewById(R.id.UserAccountActivity_idNum);

        mailFront = (TextView) findViewById(R.id.UserAccountActivity_mail_front);
        mailTextView = (TextView) findViewById(R.id.UserAccountActivity_mail);




        netRequest();
    }

    @Override
    protected void netRequest() {
        super.netRequest();

        request.url = URLConst.URL_GET_ACCOUNT_INFO + TokenUtil.token;
        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("获取个人信息成功");

                JSONObject responseData = JSON.parseObject(response,JSONObject.class);

                JSONObject data = (JSONObject) responseData.get("data");

                String idNum = (String) data.get("sfz");
                String checkStatus =(String)  data.get("cherk");
                String name = (String) data.get("name");
                String account = (String) data.get("idname");
                String mail = (String) data.get("mail");
                String phone =(String)  data.get("tel");


                if (TokenUtil.account_flag.equals(TokenUtil.USER_ACCOUNT)){
                    if (StringUtil.isNullOrBlank(mail)){
                        mailTextView.setText("未绑定 >");
                    }else {
                        mailTextView.setText(mail + " " + ">");
                    }

                    nameFront.setText("名称");
                    accountFront.setText("账号");
                    idNumFront.setText("身份证号");
                    mailFront.setText("绑定邮箱");

                    nameTextView.setText(name);
                    accountTextView.setText(account);
                    idNumTextView.setText(idNum);

                    mailTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getApplicationContext(),BindMailActivity.class));
                        }
                    });

                }else {
                    if (StringUtil.isNullOrBlank(phone)){
                        mailTextView.setText("未绑定 >");
                    }else {
                        mailTextView.setText(phone + " " + ">");
                    }

                    nameFront.setText("企业名称");
                    accountFront.setText("账号");
                    idNumFront.setText("法人身份证号");
                    mailFront.setText("法人手机号");

                    nameTextView.setText(name);
                    accountTextView.setText(account);
                    idNumTextView.setText(idNum);


                    mailTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                }

            }

            @Override
            public void fail(String response) {
                gotoLoginActivity();
            }
        });
        Logger.getInstance(getApplicationContext()).info("获取个人信息的url is ："  + request.url);
        request.getRequest(getApplicationContext());
    }
}
