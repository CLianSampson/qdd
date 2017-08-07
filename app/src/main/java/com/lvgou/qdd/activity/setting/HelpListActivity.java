package com.lvgou.qdd.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.http.URLConst;
import com.lvgou.qdd.util.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelpListActivity extends BaseActivity {

    private Button backButton;

    private ListView listView;

    private List<Map<String,String>>  list;

    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_help_list);

        backButton = (Button) findViewById(R.id.HelpListActivity_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listView = (ListView) findViewById(R.id.HelpListActivity_listView);
        list = new ArrayList();
        adapter = new SimpleAdapter(this, //没什么解释
                list,//数据来源
                R.layout.listitem_help_list_activity,//ListItem的XML实现

                //动态数组与ListItem对应的子项
                new String[] {"title"},

                //ListItem的XML文件里面的两个TextView ID
                new int[] {R.id.HelpListActivity_name});

        listView.setAdapter(adapter);

        setListItemClickEvent();

        netRequest();

    }

    private  void  setListItemClickEvent(){
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String signId = (String) list.get((int) id).get("id");
//                Intent intent = new Intent(getApplicationContext(), HelpActivity.class);
//                intent.putExtra("helpId",signId);
//                startActivity(intent);
//            }
//        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String signId = (String) list.get((int) id).get("id");
                Intent intent = new Intent(getApplicationContext(), HelpActivity.class);
                intent.putExtra("helpId",signId);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void netRequest() {
        super.netRequest();

        request.url = URLConst.URL_LIST_HELP;
        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("获取帮助列表成功");

                JSONObject responseData = JSON.parseObject(response,JSONObject.class);

                JSONObject data = (JSONObject) responseData.get("data");
                JSONArray listJson =  (JSONArray) data.get("message");

                for (Object temp: listJson) {
                    JSONObject jsonObject = (JSONObject) temp;
                    Map<String,String> map = new HashMap<String, String>();
                    map.put("title",(String) jsonObject.get("title"));
                    map.put("id",(String) jsonObject.get("id"));

                    list.add(map);

                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void fail(String response) {
                gotoLoginActivity();
            }
        });
        Logger.getInstance(getApplicationContext()).info("获取帮助列表的url is ："  + request.url);
        request.getRequest(getApplicationContext());

    }


}
