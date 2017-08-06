package com.lvgou.qdd.activity.order;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.http.URLConst;
import com.lvgou.qdd.util.Logger;
import com.lvgou.qdd.util.TokenUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class OrderListActivity extends BaseActivity {

    private Button backButton;

    private Button allButton;

    private Button noPayButton;

    private Button havePayButton;

    private OrderListAdapter adapter;

    private PullToRefreshListView pullToRefreshListView;

    private LinkedList<JSONObject> linkedList;

    private String orderStatus = "2";  //订单状态 未完成0 已完成1 全部 2


    private int pageNo=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order_list);


        setView();

        setPullToRefreshListView();

        netRequest();

    }


    private void setView(){
        backButton = (Button) findViewById(R.id.OrderListActivity_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        allButton = (Button) findViewById(R.id.OrderListActivity_all);
        noPayButton = (Button) findViewById(R.id.OrderListActivity_not_pay);
        havePayButton = (Button) findViewById(R.id.OrderListActivity_have_pay);

        allButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allButton.setBackgroundResource(R.drawable.shape_nav_indicator);
                noPayButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
                havePayButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);


                //设置字体颜色
                allButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));
                noPayButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));
                havePayButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));

                pageNo=0;
                orderStatus="2";
                linkedList.clear();
                adapter.notifyDataSetChanged();
                netRequest();
            }
        });


        noPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
                noPayButton.setBackgroundResource(R.drawable.shape_nav_indicator);
                havePayButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);

                //设置字体颜色
                allButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));
                noPayButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));
                havePayButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));

                pageNo=0;
                orderStatus="0";
                linkedList.clear();
                adapter.notifyDataSetChanged();
                netRequest();
            }
        });

        havePayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
                noPayButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
                havePayButton.setBackgroundResource(R.drawable.shape_nav_indicator);


                //设置字体颜色
                allButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));
                noPayButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));
                havePayButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));

                pageNo=0;
                orderStatus="1";
                linkedList.clear();
                adapter.notifyDataSetChanged();
                netRequest();

            }
        });

    }

    private void setPullToRefreshListView(){
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.OrderListActivity_listView);

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

        linkedList = new LinkedList<>();
        adapter = new OrderListAdapter(linkedList,getApplicationContext(),this);

        pullToRefreshListView.setAdapter(adapter);
    }


    @Override
    protected void netRequest() {
        super.netRequest();

        pageNo++;

        request.url = URLConst.URL_LIST_ORDER + TokenUtil.token + "/p/" + pageNo;
        final Map<String,String> map = new HashMap<>();
        map.put("orstatus",orderStatus);

        Logger.getInstance(getApplicationContext()).info("获取订单列表参数是 :" + map.toString());

        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("获取订单列表成功");

                JSONObject map = JSON.parseObject(response,JSONObject.class);
                JSONObject data = (JSONObject) map.get("data");
                JSONArray jsonArray = (JSONArray) data.get("orlist");

                if (null==jsonArray || 0==jsonArray.size()){
                    return;
                }

                for (Object temp: jsonArray) {
                    JSONObject jsonObject = (JSONObject) temp;
                    linkedList.add(jsonObject);
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void fail(String response) {

               gotoLoginActivity();
            }
        });
        Logger.getInstance(getApplicationContext()).info("获取订单列表列表：" + request.url);
        request.postRequest(getApplicationContext(),map);

    }


}
