package com.lvgou.qdd.activity.signature;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.http.URLConst;
import com.lvgou.qdd.util.Logger;
import com.lvgou.qdd.util.TokenUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignatureListActivity extends BaseActivity {

    private Button backButton;

    private Button addSignatureButton;

    private ListView listView;

    private SignatureAdapter adapter;

    private List<Object> personalList;

    private List<Object> enterpriseList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sign_list);

        backButton = (Button) findViewById(R.id.SignatureListActivity_backButton);
        addSignatureButton = (Button) findViewById(R.id.SignatureListActivity_addSignature);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addSignatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void  setListView(){
        listView = (ListView) findViewById(R.id.SignatureListActivity_listview);

    }

    @Override
    protected void netRequest() {
        super.netRequest();

        request.url = URLConst.URL_LIST_SIGNATURE + TokenUtil.token;
        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("获取签章列表成功");

                Map<String,Object> map = JSON.parseObject(response,new HashMap<String,Object>().getClass());

                Map<String,String> data = (Map<String, String>) map.get("data");




            }

            @Override
            public void fail(String response) {
                gotoLoginActivity();
            }
        });
        Logger.getInstance(getApplicationContext()).info("获取签章列表：" + request.url);
        request.getRequest(getApplicationContext());
    }
}
