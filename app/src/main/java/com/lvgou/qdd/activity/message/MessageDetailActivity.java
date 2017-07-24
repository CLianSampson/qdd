package com.lvgou.qdd.activity.message;

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
import com.lvgou.qdd.util.TokenUtil;

import java.util.HashMap;
import java.util.Map;

public class MessageDetailActivity extends BaseActivity {

    private Button backButton;

    private TextView timeTextview;

    private TextView titleTextView;

    private TextView contentTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_message_detail);

        backButton = (Button) findViewById(R.id.MessageDetailActivity_backButton);
        timeTextview = (TextView) findViewById(R.id.MessageDetailActivity_time);
        titleTextView = (TextView) findViewById(R.id.MessageDetailActivity_title);
        contentTextview = (TextView) findViewById(R.id.MessageDetailActivity_contnt);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        netRequest();

    }

    @Override
    protected void netRequest() {
        super.netRequest();

        request.url = URLConst.URL_MESSAGE_DETAIL + TokenUtil.token + "/id/" + getIntent().getStringExtra("messageId");

        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("获取消息详情成功");

                Map<String,Object> map = JSON.parseObject(response,new HashMap<String,Object>().getClass());

                Map<String,Object> data = (Map<String, Object>) map.get("data");
                String time = (String) data.get("ctime");
                String title = (String) data.get("title");
                String contents = (String) data.get("contents");

                timeTextview.setText(time);
                titleTextView.setText(title);
                contentTextview.setText(contents);
            }

            @Override
            public void fail(String response) {
                gotoLoginActivity();
            }
        });

        request.getRequest(getApplicationContext());
    }
}
