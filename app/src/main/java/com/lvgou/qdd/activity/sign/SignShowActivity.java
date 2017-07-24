package com.lvgou.qdd.activity.sign;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.http.URLConst;
import com.lvgou.qdd.util.Logger;
import com.lvgou.qdd.util.TokenUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SignShowActivity extends BaseActivity {

    private String signId;

    private Button backButton;

    private TextView navTitle;

    private ListView listView;

    private SignShowAdapter adapter;

    private LinkedList<Object> linkedList;

    private Button refuseButton;

    private Button signButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sign_show);

        backButton = (Button) findViewById(R.id.SignShowActivity_backButton);
        navTitle = (TextView) findViewById(R.id.SignShowActivity_middleMessage);
        listView = (ListView)findViewById(R.id.SignShowActivity_listView);

        refuseButton = (Button) findViewById(R.id.SignShowActivity_refuseButton);
        signButton = (Button) findViewById(R.id.SignShowActivity_signbutton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        refuseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlertView("hello " ,"world");
            }
        });

        //获取合同id
        signId = getIntent().getStringExtra("signId");

        setListView();

        netRequest();
    }

    private void setListView(){
        linkedList = new LinkedList<>();
        adapter = new SignShowAdapter(getApplicationContext(),linkedList);
        listView.setAdapter(adapter);
    }

    @Override
    protected void netRequest() {
        super.netRequest();

        request.url = URLConst.URL_SIGN_SHOW + TokenUtil.token +"/id/" + signId + "/p/0";
        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("获取合同展示成功");

                Map<String,Object> map = JSON.parseObject(response,new HashMap<String,Object>().getClass());

                Map<String,Object> data = (Map<String, Object>) map.get("data");
                List<String> list = (List<String>) data.get("pic_name");

                for (String str: list) {
                    linkedList.add(str);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void fail(String response) {
                gotoLoginActivity();
            }
        });

        request.getRequest(getApplicationContext());

    }
}
