package com.lvgou.qdd.activity.shopping;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.http.URLConst;
import com.lvgou.qdd.model.Shopping;
import com.lvgou.qdd.util.Logger;
import com.lvgou.qdd.util.TokenUtil;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;




public class ShoppingActivity extends BaseActivity {

    private Button backButton;

    private TextView typeTextView;

    private TextView durationTextView;

    private TextView totalTimesTextView;

    private TextView remainTimesTextView;

    private PullToRefreshListView pullToRefreshListView;

    private LinkedList<Map<String,Object>> goodList;

    private SimpleAdapter adapter;

    private int pageNo = 0;

    private  LinkedList<Shopping.Good> rawList; //用来存放原始数据，用于点击item，获取数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        childImpl(savedInstanceState);
    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shopping);
        setView();
//        netRequest();
    }

    private  void  setView(){
        backButton = (Button) findViewById(R.id.ShoppingActivity_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingActivity.this.finish();
            }
        });

        typeTextView = (TextView) findViewById(R.id.ShoppingActivity_type);
        durationTextView = (TextView) findViewById(R.id.ShoppingActivity_duration);
        totalTimesTextView =(TextView) findViewById(R.id.ShoppingActivity_totalTimes);
        remainTimesTextView = (TextView) findViewById(R.id.ShoppingActivity_remianTimes);


        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.ShoppingActivity_pull_to_refresh_listview);
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

        goodList = new LinkedList<Map<String,Object>>();

        adapter = new SimpleAdapter(this, //没什么解释
                goodList,//数据来源
                R.layout.listitem_shoppingactivity,//ListItem的XML实现

                //动态数组与ListItem对应的子项
                new String[] {"times","type","content"},

                //ListItem的XML文件里面的两个TextView ID
                new int[] {R.id.ShoppingActivity_lsit_times,R.id.ShoppingActivity_lsit_type,R.id.ShoppingActivity_lsit_content});

        pullToRefreshListView.setAdapter(adapter);

        setListItemClickEvent();

        netRequest();

    }

    private  void  setListItemClickEvent(){
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Map<String,Object> map = goodList.get((int) id);
//
//                Intent intent = new Intent(getApplicationContext(),ShoppingDetailActivity.class);
//                intent.putExtra("good",JSON.toJSONString(map));
//                startActivity(intent);


                Shopping.Good good = rawList.get((int) id);
                Intent intent = new Intent(getApplicationContext(),ShoppingDetailActivity.class);
                intent.putExtra("good",JSON.toJSONString(good));

                Logger.getInstance(getApplicationContext()).info("json str is :" + JSON.toJSONString(good));

                Logger.getInstance(getApplicationContext()).info("raw good is :"+good);

                startActivity(intent);

            }
        });
    }


    protected  void  netRequest(){
        super.netRequest();

        pageNo++;

        request.url = URLConst.URL_BUY_GOODS + TokenUtil.token + "/p/" + pageNo;
        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("获取商品成功");

                Map<String,Object> map = JSON.parseObject(response,new HashMap<String,Object>().getClass());

                Map<String,Object> data = (Map<String, Object>) map.get("data");

                Logger.getInstance(getApplicationContext()).info("data is :" + data);

                Shopping shopping = JSON.parseObject(JSON.toJSON(data).toString(),Shopping.class);

                Logger.getInstance(getApplicationContext()).info("shopping is :" + shopping);

                String type = "套餐类型: " + shopping.getGoodname();
                String duration = "有效期: " + shopping.getTime();
                String totaltimes = "总次数: " + shopping.getAllnum()+"次";
                String remianTimes = "剩余次数: " + shopping.getLast_name()+"次";


                typeTextView.setText(type);
                durationTextView.setText(duration);
                totalTimesTextView.setText(totaltimes);
                remainTimesTextView.setText(remianTimes);

                if (rawList == null){
                    rawList = new LinkedList<Shopping.Good>();
                }

                rawList.addAll(shopping.getGoods());

                setListView(shopping.getGoods());

            }

            @Override
            public void fail(String response) {
               gotoLoginActivity();
            }
        });
        Logger.getInstance(getApplicationContext()).info("获取商品的url is ："  + request.url);
        request.getRequest(getApplicationContext());

    }


    private  void  setListView(List<Shopping.Good> list){

        for (Shopping.Good good : list) {
            Map<String,Object> map = new  HashMap<String, Object>();

            float price=Float.valueOf(good.getPrice())/100;
            DecimalFormat decimalFormat=new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
            String priceStr = decimalFormat.format(price);//format 返回的是字符串

            map.put("times","" + priceStr + "元" + "/" + good.getNum() + "次");
            map.put("type",good.getName());
            map.put("content",good.getContents());

            map.put("goodId",good.getId());

            if (null == goodList){
                goodList = new LinkedList<>();
            }
            goodList.addLast(map);
        }


        //通知程序数据集已经改变，如果不做通知，那么将不会刷新mListItems的集合
        adapter.notifyDataSetChanged();
        // Call onRefreshComplete when the list has been refreshed.
        pullToRefreshListView.onRefreshComplete();

    }
}
