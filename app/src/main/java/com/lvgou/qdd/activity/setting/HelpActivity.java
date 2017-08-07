package com.lvgou.qdd.activity.setting;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.http.URLConst;
import com.lvgou.qdd.util.Logger;

public class HelpActivity extends BaseActivity {

    private Button backButton;

    private WebView webView;

    private String helpId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_help);

        backButton = (Button) findViewById(R.id.HelpActivity_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        webView = (WebView) findViewById(R.id.HelpActivity_webView);

        helpId = getIntent().getStringExtra("helpId");

        netRequest();

    }


    @Override
    protected void netRequest() {
        super.netRequest();

        request.url = URLConst.URL_HELP_DETAIL + helpId;
        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("获取帮助详细内容成功");

                JSONObject responseData = JSON.parseObject(response,JSONObject.class);

                JSONObject data = (JSONObject) responseData.get("data");
                String content = (String) data.get("content");


                webView.loadDataWithBaseURL(null, content, "text/html", "utf-8",
                        null);
            }

            @Override
            public void fail(String response) {
                gotoLoginActivity();
            }
        });
        Logger.getInstance(getApplicationContext()).info("获取帮助详细内容的url is ："  + request.url);
        request.getRequest(getApplicationContext());

    }



}
