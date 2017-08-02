package com.lvgou.qdd.activity.contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.alibaba.fastjson.JSON;
import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.http.URLConst;
import com.lvgou.qdd.util.Logger;
import com.lvgou.qdd.util.StringUtil;
import com.lvgou.qdd.util.TokenUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactListActivity extends BaseActivity {

    private Button backButton;

    private Button addContactbutton;

    private EditText editText;

    private Button searchButton;

    private ListView listView;

    private SimpleAdapter adapter;

    private List<Map<String,String>> list;  //展示list数据

    private List<Map<String,String>> rawList;  //存储原始数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_contact_list);

        backButton = (Button) findViewById(R.id.ContactListActivity_backButton);
        addContactbutton = (Button) findViewById(R.id.ContactListActivity_addContact);
        editText = (EditText) findViewById(R.id.ContactListActivity_edit);
        searchButton = (Button) findViewById(R.id.ContactListActivity_search);
        listView = (ListView) findViewById(R.id.ContactListActivity_listView);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addContactbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddContactActivity.class));
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edit  = editText.getText().toString();
                if (StringUtil.isNullOrBlank(edit)){
                    list.clear();
                    for (Map<String,String> temp: rawList) {
                        list.add(temp);
                    }
                }else {
                    List<Map<String,String>> newList = new ArrayList<Map<String, String>>();
                    for (Map<String,String> temp: list) {
                        if (temp.get("name").contains(edit)){
                            newList.add(temp);
                        }
                    }

                    list.clear();;
                    for (Map<String,String> temp: newList) {
                        list.add(temp);
                    }
                }

                adapter.notifyDataSetChanged();
            }
        });

        list = new ArrayList<>();
        rawList = new ArrayList<>();

        adapter = new SimpleAdapter(this, //没什么解释
                list,//数据来源
                R.layout.listitem_contact_list_activity,//ListItem的XML实现

                //动态数组与ListItem对应的子项
                new String[] {"name"},

                //ListItem的XML文件里面的两个TextView ID
                new int[] {R.id.ContactListActivity_list_name});

        listView.setAdapter(adapter);

        netRequest();
    }

    @Override
    protected void netRequest() {
        super.netRequest();

        request.url = URLConst.URL_LIST_CONTACT + TokenUtil.token;
        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("获取联系人列表成功");

                Map<String,Object> map = JSON.parseObject(response,new HashMap<String,Object>().getClass());

                Map<String,Object> data = (Map<String,Object>) map.get("data");
                int allcount = Integer.valueOf((String)data.get("allcount")).intValue();
                for (int i=0; i< allcount;i++){
                    Map<String,String> mapInfo = (Map<String, String>) data.get(""+i);
                    list.add(mapInfo);

                    rawList.add(mapInfo);
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void fail(String response) {
                gotoLoginActivity();
            }
        });
        Logger.getInstance(getApplicationContext()).info("获取联系人列表：" + request.url);
        request.getRequest(getApplicationContext());
    }
}
