package com.lvgou.qdd.activity.signature;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.http.URLConst;
import com.lvgou.qdd.http.VolleyRequest;
import com.lvgou.qdd.util.Logger;
import com.lvgou.qdd.util.TokenUtil;

import java.util.HashMap;
import java.util.Map;

public class SignatureListActivity extends BaseActivity {

    private Button backButton;

    private Button addSignatureButton;

    private ListView listView;

    public SignatureAdapter adapter;

    public JSONArray personalList;

    public JSONArray enterpriseList;


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
                startActivity(new Intent(getApplicationContext(),AddSignatureActivity.class));
            }
        });
        //设置button背景色为透明
        addSignatureButton.setBackgroundColor(Color.TRANSPARENT);

        setListView();

        netRequest();

    }

    private void  setListView(){
        listView = (ListView) findViewById(R.id.SignatureListActivity_listview);
        personalList = new JSONArray();
        enterpriseList = new JSONArray();

        adapter = new SignatureAdapter(getApplicationContext(),personalList,enterpriseList,this);
        listView.setAdapter(adapter);

//        setListitemOnclick();

    }

    private void  setListitemOnclick(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("^^^^^^^^^^^^^^","click");

                final  int index = position;
                Button deleteButton = (Button) view.findViewById(R.id.SignatureListActivity_delete_button);
                if (position == 1){
                    deleteButton.setVisibility(View.INVISIBLE);
                }
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        deleteSignature((((Map<String,String>) (personalList.get(index-1)))).get("id"));
                    }
                });
            }
        });
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

                Map<String,Object> data = (Map<String, Object>) map.get("data");

                JSONArray personListTemp  = (JSONArray) data.get("allsign");
                for (Object object: personListTemp) {
                    personalList.add(object);
                }
                JSONObject enterprise = (JSONObject) data.get("csign");
                enterpriseList.add(enterprise);

                adapter.notifyDataSetChanged();

            }

            @Override
            public void fail(String response) {
                gotoLoginActivity();
            }
        });
        Logger.getInstance(getApplicationContext()).info("获取签章列表：" + request.url);
        request.getRequest(getApplicationContext());
    }

    private  void  deleteSignature(String signatureId){
        VolleyRequest request = new VolleyRequest();

        request.url = URLConst.URL_DELETE_SLGNATURE + TokenUtil.token + "/signid/" + signatureId;
        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("删除签章成功");

                Map<String,Object> map = JSON.parseObject(response,new HashMap<String,Object>().getClass());

                Map<String,Object> data = (Map<String, Object>) map.get("data");

                enterpriseList.clear();
                personalList.clear();
                adapter.notifyDataSetChanged();

                netRequest();

            }

            @Override
            public void fail(String response) {

            }
        });
        Logger.getInstance(getApplicationContext()).info("删除签章：" + request.url);
        request.getRequest(getApplicationContext());
    }

}
