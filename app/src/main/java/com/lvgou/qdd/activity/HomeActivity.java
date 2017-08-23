package com.lvgou.qdd.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.contact.ContactListActivity;
import com.lvgou.qdd.activity.message.MessageListActivity;
import com.lvgou.qdd.activity.order.OrderListActivity;
import com.lvgou.qdd.activity.setting.SettingActivity;
import com.lvgou.qdd.activity.shopping.ShoppingActivity;
import com.lvgou.qdd.activity.sign.SignShowActivity;
import com.lvgou.qdd.activity.signature.SignatureListActivity;
import com.lvgou.qdd.activity.verify.EnterpriseActivity;
import com.lvgou.qdd.activity.verify.HaveVerifyActivity;
import com.lvgou.qdd.activity.verify.UserVerifyActivity;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.http.URLConst;
import com.lvgou.qdd.http.VolleyRequest;
import com.lvgou.qdd.model.Sign;
import com.lvgou.qdd.util.Constant;
import com.lvgou.qdd.util.DateUtil;
import com.lvgou.qdd.util.Logger;
import com.lvgou.qdd.util.StorageUtil;
import com.lvgou.qdd.util.StringUtil;
import com.lvgou.qdd.util.TokenUtil;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.special.lvgou.UserIcon;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class HomeActivity extends BaseActivity implements View.OnClickListener ,UserIcon.IUserIcon{

    private ResideMenu resideMenu;
    private ResideMenuItem shopping;
    private ResideMenuItem mySignature;
    private ResideMenuItem contact;
    private ResideMenuItem myOrder;
    private ResideMenuItem settings;
    private HomeActivity mContext;

    private Button waitForMeButton;
    private Button waitOtherButton;
    private Button completeButton;
    private Button timeOutButton;
    private Button haveRefuseButton;

    private int orderStatus;  //1：待我签署 2：待他人签署 3：已完成 4：过期未签署

    private PullToRefreshListView pullToRefreshListView;

    private LinkedList<Map<String,Object>> signList;

    private SimpleAdapter adapter;

    private int pageNo = 0;

    private UserIcon userIcon;
    private int verifyState; //认证状态

    private TextView noDataView;  //没有数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {

        setContentView(R.layout.activity_home);

        mContext = this;

        orderStatus = 1;

//        signList = new LinkedList<Map<String,Object>>();

//        setPullToRefreshListView();
        setButton();
        setUpMenu();
        setPullToRefreshListView();


    }

    private  void  setButton(){
        waitForMeButton =(Button) findViewById(R.id.waitForMeButton);
        waitForMeButton.setOnClickListener(this);
        waitOtherButton =(Button) findViewById(R.id.waitOtherButton);
        waitOtherButton.setOnClickListener(this);
        completeButton =(Button) findViewById(R.id.completeButton);
        completeButton.setOnClickListener(this);
        timeOutButton =(Button) findViewById(R.id.timeOutButton);
        timeOutButton.setOnClickListener(this);
        haveRefuseButton = (Button) findViewById(R.id.haveRefuse);
        haveRefuseButton.setOnClickListener(this);

        noDataView = (TextView) findViewById(R.id.HomeActivity_noData);
    }


    @Override
    public void onClick(View view) {
      if (view == shopping){
          Intent intent = new Intent(getApplicationContext(), ShoppingActivity.class);
          startActivity(intent);
          return;
      }

      if (view == mySignature){
          startActivity(new Intent(getApplicationContext(), SignatureListActivity.class));
          return;
      }

      if (view == contact){
          startActivity(new Intent(getApplicationContext(), ContactListActivity.class));
          return;
      }

      if (view == myOrder){
          startActivity(new Intent(getApplicationContext(), OrderListActivity.class));
          return;
      }

      if (view == settings){
          startActivity(new Intent(getApplicationContext(), SettingActivity.class));
          return;
      }

      switch (view.getId()){
          case R.id.waitForMeButton:
              orderStatus = 1;
              changeOrderStatus();

              //设置背景
              waitForMeButton.setBackgroundResource(R.drawable.shape_nav_indicator);

              waitOtherButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              completeButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              timeOutButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              haveRefuseButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);

              //设置字体颜色
              waitForMeButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));

              waitOtherButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));
              completeButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));
              timeOutButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));
              haveRefuseButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));

              break;
          case R.id.waitOtherButton:
              orderStatus = 2;
              changeOrderStatus();

              waitOtherButton.setBackgroundResource(R.drawable.shape_nav_indicator);

              waitForMeButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              completeButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              timeOutButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              haveRefuseButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);

              //设置字体颜色
              waitOtherButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));

              waitForMeButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));
              completeButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));
              timeOutButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));
              haveRefuseButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));

              break;

          case R.id.completeButton:
              orderStatus = 3;
              changeOrderStatus();

              completeButton.setBackgroundResource(R.drawable.shape_nav_indicator);

              waitOtherButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              waitForMeButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              timeOutButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              haveRefuseButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);

              //设置字体颜色
              completeButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));

              waitOtherButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));
              waitForMeButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));
              timeOutButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));
              haveRefuseButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));

              break;
          case R.id.timeOutButton:
              orderStatus = 4;
              changeOrderStatus();

              timeOutButton.setBackgroundResource(R.drawable.shape_nav_indicator);

              waitOtherButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              completeButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              waitForMeButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              haveRefuseButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);

              //设置字体颜色
              timeOutButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));

              waitOtherButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));
              completeButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));
              waitForMeButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));
              haveRefuseButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));

              break;

          case R.id.haveRefuse:
              orderStatus = 5;
              changeOrderStatus();

              haveRefuseButton.setBackgroundResource(R.drawable.shape_nav_indicator);

              timeOutButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              waitOtherButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              completeButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);
              waitForMeButton.setBackgroundResource(R.drawable.shape_nav_no_indicator);

              //设置字体颜色
              haveRefuseButton.setTextColor(getResources().getColorStateList(R.color.systemBlue));
              timeOutButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));
              waitOtherButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));
              completeButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));
              waitForMeButton.setTextColor(getResources().getColorStateList(R.color.systemBlack));


      }
    }


    private void  changeOrderStatus(){
        pageNo = 0;
        signList.clear();
        //通知程序数据集已经改变，如果不做通知，那么将不会刷新mListItems的集合
        adapter.notifyDataSetChanged();
        // Call onRefreshComplete when the list has been refreshed.
        pullToRefreshListView.onRefreshComplete();
        netRequest();
    }

    /********************************** 抽屉效果开始 ************************************************/
    private  void  setUpMenu(){
        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setUse3D(false);
        //设置背景图片
        //resideMenu.setBackground(R.mipmap.menu);
//        resideMenu.setBackgroundColor(getResources().getColor(R.color.systemBlue));
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        shopping     = new ResideMenuItem(this, 0,     "购买套餐");
        mySignature  = new ResideMenuItem(this, 0,  "我的签名");
        contact = new ResideMenuItem(this, 0, "联系人");
        myOrder = new ResideMenuItem(this, 0, "我的订单");
        settings = new ResideMenuItem(this, 0, "设置");

        userIcon = new UserIcon(getApplicationContext(),null);
        userIcon.delegate = this;
        //获取认证状态
        String verifyStateStr = StorageUtil.getData(getApplicationContext(),StorageUtil.VERIFY_STATE);
        if (StringUtil.isNullOrBlank(verifyStateStr)){
            getVerifyStateByNet();
        }else {
            verifyState = Integer.valueOf(verifyStateStr).intValue();
            if (verifyState == Constant.HAVE_VERIFY){
                userIcon.setHaveVerify();
            }else {
                userIcon.setUnVerify();
            }
        }
        //设置账号
        userIcon.setAccount(StorageUtil.getData(getApplicationContext(),StorageUtil.PHONE));


        shopping.setOnClickListener(this);
        mySignature.setOnClickListener(this);
        contact.setOnClickListener(this);
        myOrder.setOnClickListener(this);
        settings.setOnClickListener(this);

        resideMenu.addUserIcon(userIcon, ResideMenu.DIRECTION_LEFT);


        resideMenu.addMenuItem(shopping, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(mySignature, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(contact, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(myOrder, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(settings, ResideMenu.DIRECTION_LEFT);


        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);


        findViewById(R.id.leftButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
                //resideMenu.setBackground(R.mipmap.menu);
            }
        });
        findViewById(R.id.rightButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
                //                resideMenu.setBackground(R.mipmap.menu);

                Intent intent = new Intent(getApplicationContext(),MessageListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
            Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };

    /********************************** 抽屉效果结束 ************************************************/



    private void setPullToRefreshListView(){
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.HomeActivity_pull_to_refresh_listview);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);//两端刷新
        //pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//上拉刷新
        //pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);//下拉刷新

        //        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
//            @Override
//            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//                refreshView.getLoadingLayoutProxy().setRefreshingLabel("正在加载");
//                refreshView.getLoadingLayoutProxy().setPullLabel("上拉加载更多");
//                refreshView.getLoadingLayoutProxy().setReleaseLabel("释放开始加载");
//
//                netRequest();
//            }
//        });


        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                refreshView.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
                refreshView.getLoadingLayoutProxy().setPullLabel("下拉刷新");
                refreshView.getLoadingLayoutProxy().setReleaseLabel("释放开始刷新");

                pullToRefreshListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullToRefreshListView.onRefreshComplete();
                    }
                }, 1000);


                signList.clear();

                pageNo = 0;
                netRequest();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshView.getLoadingLayoutProxy().setRefreshingLabel("正在加载");
                refreshView.getLoadingLayoutProxy().setPullLabel("上拉加载更多");
                refreshView.getLoadingLayoutProxy().setReleaseLabel("释放开始加载");

                pullToRefreshListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullToRefreshListView.onRefreshComplete();
                    }
                }, 1000);

                netRequest();
            }
        });



        signList = new LinkedList<Map<String,Object>>();

        adapter = new SimpleAdapter(this, //没什么解释
                signList,//数据来源
                R.layout.listitem_homeacitcity,//ListItem的XML实现

                //动态数组与ListItem对应的子项
                new String[] {"signName","state","sendPerson","sendtime","duration"},

                //ListItem的XML文件里面的两个TextView ID
                new int[] {R.id.HomeAcitivity_signName,R.id.HomeAcitivity_state,R.id.HomeAcitivity_sendPerson,R.id.HomeAcitivity_sendTime
                ,R.id.HomeAcitivity_duration});

        pullToRefreshListView.setAdapter(adapter);

        setListItemClickEvent();

        netRequest();
    }

    private  void  setListItemClickEvent(){
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String signId = (String) signList.get((int) id).get("signId");
                Intent intent = new Intent(getApplicationContext(), SignShowActivity.class);
                intent.putExtra("signId",signId);
                intent.putExtra("orderStatus",orderStatus);
                startActivityForResult(intent,0001);
            }
        });
    }

    protected void  netRequest(){
        super.netRequest();

        pageNo++;

        request.url = URLConst.URL_LIST_SIGN + TokenUtil.token + "?status=" + orderStatus + "&p=" + pageNo;
        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("获取合同列表成功");

                Map<String,Object> map = JSON.parseObject(response,new HashMap<String,Object>().getClass());

                Map<String,Object> data = (Map<String, Object>) map.get("data");
                List<Sign> listJson =  (List<Sign> )data.get("contract");

                Logger.getInstance(getApplicationContext()).info("list is :" + listJson);

                List<Sign> list = JSON.parseArray(JSON.toJSON(listJson).toString(),Sign.class);

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


    private  void  setListView(List<Sign> list) {

        for (Sign sign : list) {
            Map<String,Object> map = new  HashMap<String, Object>();

            //1：待我签署 2：待他人签署 3：已完成 4：过期未签署
            String status = null;
            switch (orderStatus){
                case 1:
                    status = "待我签署";
                    break;
                case 2:
                    status = "待他人签署";
                    break;
                case 3:
                    status = "已完成";
                    break;
                case 4:
                    status = "过期未签署";
                    break;
                case 5:
                    status = "已驳回";
                    break;
                default:
                    break;

            }

            Date createTime = DateUtil.stringToDateFormat(sign.getStime(),DateUtil.TIME_NORMAL_FORMAT);
            Date endTime = DateUtil.stringToDateFormat(sign.getEtime(),DateUtil.TIME_NORMAL_FORMAT);
            long day = 0;
            if (null!=createTime && null!=endTime){
                long timeInterval = endTime.getTime() - createTime.getTime();
                day =  timeInterval/(24*60*60*1000);

            }

            String[] mapKey =  new String[] {"signName","state","sendPerson","sendtime","duration"};
            map.put(mapKey[0],"合同名称: " + sign.getTitle());
            map.put(mapKey[1],status);
            map.put(mapKey[2],"发送人: " + sign.getSendname());
            map.put(mapKey[3],"发送时间: " + sign.getStime());
            map.put(mapKey[4],"签约有效期: "+day+"天");
            map.put("signId",sign.getId());



            if (null == signList){
                signList = new LinkedList<>();
            }
            signList.addLast(map);
        }

        if (signList.size() != 0){
            noDataView.setVisibility(View.INVISIBLE);
        }else {
            noDataView.setVisibility(View.VISIBLE);
        }

        //通知程序数据集已经改变，如果不做通知，那么将不会刷新mListItems的集合
        adapter.notifyDataSetChanged();
        // Call onRefreshComplete when the list has been refreshed.
        pullToRefreshListView.onRefreshComplete();
    }


    //反向回调传值
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //个人认证反向回调
        if (resultCode== Constant.GO_HOME_ACTIVITY_FROM_VERIFY_ACTIVITY){
            userIcon.setHaveVerify();
            verifyState = Constant.HAVE_VERIFY;

            //存储认证状态
            StorageUtil.storeData(getApplicationContext(),StorageUtil.VERIFY_STATE,verifyState+"");

            return;
        }else if (requestCode == Constant.GO_HOME_ACTIVITY_AFTER_REFUSE_SIGN){
            //反向回调成功
            Logger.getInstance(getApplicationContext()).info("反向回调成功");
            pageNo=0;
            signList.clear();
            netRequest();
        }
    }

    @Override
    public void gotoVerifyActivity() {
        if (verifyState == Constant.HAVE_VERIFY){
            //已认证
            startActivity(new Intent(getApplicationContext(), HaveVerifyActivity.class));
        }else {
            //未认证
            if (TokenUtil.account_flag == TokenUtil.USER_ACCOUNT){
                Intent intent = new Intent(getApplicationContext(), UserVerifyActivity.class);
                startActivityForResult(intent,0);
            }else {
                Intent intent = new Intent(getApplicationContext(), EnterpriseActivity.class);
                startActivityForResult(intent,0);
            }
        }
    }

    //获取认证状态
    private void getVerifyStateByNet(){
        VolleyRequest request = new VolleyRequest();

        request.url = URLConst.URL_GET_ACCOUNT_INFO + TokenUtil.token;
        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("获取个人信息成功");

                JSONObject jsonObject = JSON.parseObject(response,JSONObject.class);

                JSONObject data = (JSONObject) jsonObject.get("data");
                int status = (Integer.valueOf((String) data.get("cherk")).intValue());
                switch (status) {
                    case 0:
                        verifyState = Constant.NO_VERIFY;
                        break;

                    case 1:
                        verifyState = Constant.UNDER_VERIFYING;
                        break;

                    case 2:
                        verifyState = Constant.HAVE_VERIFY;
                        break;

                    case 3:
                        verifyState = Constant.NOT_PASS_VERIFY;
                        break;

                    default:
                        break;
                }

                //存储认证状态
                StorageUtil.storeData(getApplicationContext(),StorageUtil.VERIFY_STATE,verifyState+"");
            }

            @Override
            public void fail(String response) {
                gotoLoginActivity();
            }
        });
        Logger.getInstance(getApplicationContext()).info("获取个人信息的url is ："  + request.url);
        request.getRequest(getApplicationContext());
    }

}
