package com.lvgou.qdd.activity.sign;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.http.URLConst;
import com.lvgou.qdd.util.Logger;
import com.lvgou.qdd.util.StringUtil;
import com.lvgou.qdd.util.TokenUtil;

import java.util.HashMap;
import java.util.Map;

public class SignDetailActivity extends BaseActivity {

    private Button backButton;

    private String signId;

    private TextView sendMobile;

    private TextView sendMail;

    private TextView toMobile;

    private TextView toMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sign_detail);

        backButton = (Button) findViewById(R.id.SignDetailActivity_backButton);

        sendMail = (TextView) findViewById(R.id.SignDetailActivity_send_mail);
        sendMobile = (TextView) findViewById(R.id.SignDetailActivity_send_phone);

        toMobile = (TextView) findViewById(R.id.SignDetailActivity_to_phone);
        toMail = (TextView) findViewById(R.id.SignDetailActivity_to_mail);

        sendMobile.setText("合同发送方:");
        sendMail.setText("邮箱:");

        toMobile.setText("合同接受方:");
        toMail.setText("邮箱:");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signId = getIntent().getStringExtra("signId");

        netRequest();

    }

    @Override
    protected void netRequest() {
        super.netRequest();

        request.url = URLConst.URL_SIGN_DETAIL + TokenUtil.token +"/id/" + signId;
        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("获取合同详情成功");

                Map<String,Object> map = JSON.parseObject(response,new HashMap<String,Object>().getClass());

                Map<String,String> data = (Map<String, String>) map.get("data");


                if (StringUtil.isNullOrBlank(data.get("smobile"))){
                    sendMobile.setText("合同发送方: ");
                }else {
                    sendMobile.setText("合同发送方: " + data.get("smobile"));
                }

                if (StringUtil.isNullOrBlank(data.get("smail"))){
                    sendMail.setText("邮箱: ");
                }else {
                    sendMail.setText("邮箱: " + data.get("smail"));
                }

                if (StringUtil.isNullOrBlank(data.get("tmobile"))){
                    toMobile.setText("合同接受方: ");
                }else {
                    toMobile.setText("合同接受方: " +data.get("tmobile"));
                }

               if (StringUtil.isNullOrBlank(data.get("tmail"))) {
                   toMail.setText("邮箱: ");
               }else {
                   toMail.setText("邮箱: " + data.get("tmail"));
               }


            }

            @Override
            public void fail(String response) {
                gotoLoginActivity();
            }
        });
        Logger.getInstance(getApplicationContext()).info("获取合同详情：" + request.url);
        request.getRequest(getApplicationContext());
    }


}
