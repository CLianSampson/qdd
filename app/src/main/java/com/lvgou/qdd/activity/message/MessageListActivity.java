package com.lvgou.qdd.activity.message;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.http.URLConst;
import com.lvgou.qdd.model.Message;
import com.lvgou.qdd.util.Logger;
import com.lvgou.qdd.util.TokenUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MessageListActivity extends BaseActivity {

    private Button backButton;

    private PullToRefreshListView pullToRefreshListView;

    private LinkedList<Message> messageList;

    private MessageListAdapter adapter;

    private int pageNo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        childImpl(savedInstanceState);
    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {

        backButton = (Button) findViewById(R.id.MessageListActivity_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setPullToRefreshListView();
    }


    private void setPullToRefreshListView(){
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.MessageListActivity_pull_to_refresh_listview);
        //        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);//两端刷新
        //        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//上拉刷新
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);//下拉刷新
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshView.getLoadingLayoutProxy().setRefreshingLabel("正在加载");
                refreshView.getLoadingLayoutProxy().setPullLabel("上拉加载更多");
                refreshView.getLoadingLayoutProxy().setReleaseLabel("释放开始加载");

                netRequest();
            }
        });

        messageList = new LinkedList<Message>();

        adapter = new MessageListAdapter(getApplicationContext(),messageList);

        pullToRefreshListView.setAdapter(adapter);

        setListItemClickEvent();

        netRequest();
    }


    private  void  setListItemClickEvent(){
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }


    @Override
    protected void netRequest() {
        super.netRequest();

        pageNo++;

        request.url = URLConst.URL_LIST_MESSAGE + TokenUtil.token + "/p/" + pageNo;
        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("获取消息列表成功");

                Map<String,Object> map = JSON.parseObject(response,new HashMap<String,Object>().getClass());

                Map<String,Object> data = (Map<String, Object>) map.get("data");
                List<Message> listJson =  (List<Message> )data.get("message");

                Logger.getInstance(getApplicationContext()).info("list is :" + listJson);

                List<Message> list = JSON.parseArray(JSON.toJSON(listJson).toString(),Message.class);

                setListView(list);


            }

            @Override
            public void fail(String response) {
                gotoLoginActivity();
            }
        });
        Logger.getInstance(getApplicationContext()).info("获取合同列表的url is ："  + request.url);
        request.getRequest(getApplicationContext());
    }

    private  void  setListView(List<Message> list) {
        messageList.addAll(list);
        adapter.notifyDataSetChanged();
        pullToRefreshListView.onRefreshComplete();
    }
}
