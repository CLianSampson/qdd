package com.lvgou.qdd.activity.sign;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.http.URLConst;
import com.lvgou.qdd.http.VolleyRequest;
import com.lvgou.qdd.util.Logger;
import com.lvgou.qdd.util.TokenUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SetPositionActivity extends BaseActivity {

    private String signId;

    private String signStatus;

    private Button backButton;

    private Button signDetailButton;

    private ListView listView;

    private SignShowAdapter adapter;

    private LinkedList<Object> linkedList;

    private ImageView signatureImageView;

    private Button confirmButton;

    private int screenWidth;
    private int screenHeight;
    private int lastX, lastY;



    // ListView读取屏幕上当前可见Item的索引
//    getFirstVisiblePosition()   ///获取可见区域的第一个索引
//    getLastVisiblePosition() //可见区域的最后一个索引

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_set_position);

        //获取合同id
        signId = getIntent().getStringExtra("signId");
        //获取合同签署方式(个人签署或企业签署)
        signStatus = getIntent().getStringExtra("signStatus");

        setView();

        setListView();

        netRequest();
    }


    private void setView(){
        backButton = (Button) findViewById(R.id.SetPositionActivity_backButton);
        signDetailButton = (Button) findViewById(R.id.SetPositionActivity_messageDetail);
        listView = (ListView)findViewById(R.id.SetPositionActivity_listView);
        signatureImageView = (ImageView) findViewById(R.id.SetPositionActivity_signature);
        confirmButton = (Button) findViewById(R.id.SetPositionActivity_confirm);

        signatureImageView.setImageBitmap(((BitmapDrawable) ( PassImageView.passImageView).getDrawable()).getBitmap());
        signatureImageView.setScaleType(ImageView.ScaleType.FIT_XY);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignDetailActivity.class);
                intent.putExtra("signId",signId);
                startActivity(intent);
            }
        });


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPositionToServer();
            }
        });

        //设置手势拖动
        setDrag();

//        setListViewListener();
    }




    private void setListView(){
        linkedList = new LinkedList<>();
        adapter = new SignShowAdapter(getApplicationContext(),linkedList);
        adapter.signId = signId;
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


    private void  setDrag(){
        signatureImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
//                        ToastUtil.showToast(getApplicationContext(),"down");
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        //          System.out.println("lastX:"+lastX+",lastY:"+lastY);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) event.getRawX() - lastX;
                        int dy = (int) event.getRawY() - lastY;

                        int left = v.getLeft() + dx;
                        int top = v.getTop() + dy;
                        int right = v.getRight() + dx;
                        int bottom = v.getBottom() + dy;

                        System.out.println("left:" + left);
                        System.out.println("top:" + top);
                        System.out.println("right:" + right);
                        System.out.println("bottom:" + bottom);

                        // 设置不能出界
                        if (left < 0) {
                            left = 0;
                            right = left + v.getWidth();
                        }

                        if (right > screenWidth) {
                            right = screenWidth;
                            left = right - v.getWidth();
                        }

                        if (top < 25+48+5) {
                            top = 25+48+5;
                            bottom = top + v.getHeight();
                        }

                        if (bottom > screenHeight) {
                            bottom = screenHeight;
                            top = bottom - v.getHeight();
                        }
                        v.layout(left, top, right, bottom);

                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();

                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });
    }


//    private void setListViewListener(){
//        listView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        // 触摸按下时的操作
//
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        // 触摸移动时的操作
//
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        // 触摸抬起时的操作
//                        Logger.getInstance(getApplicationContext()).info("当前索引 :" + listView.getLastVisiblePosition());
//
//                        break;
//                }
//                return false;
//            }
//        });
//    }



    private void uploadPositionToServer(){
        VolleyRequest request = new VolleyRequest();

        request.url = URLConst.URL_STORE_AND_DELETE_SIGNATURE + TokenUtil.token ;
        Map<String,String> personalSignature = new HashMap<>();

        float xPrecentFloat = signatureImageView.getX()/screenWidth;
        float yPrecentFloat = signatureImageView.getY()/screenHeight;

        float xPrecent = xPrecentFloat * 100;
        float yPrecent = yPrecentFloat * 100;

        String xPosition = (int)xPrecent + "";
        String yPosition = (int)yPrecent + "";
        String pageNumStr = listView.getLastVisiblePosition() + 1 + "";

        personalSignature.put("signid",signId);
        personalSignature.put("num",pageNumStr);
        personalSignature.put("posX",xPosition);
        personalSignature.put("posY",yPosition);

        List<Object> addArry = new ArrayList<>();
        addArry.add(personalSignature);

        Map<String,Object>  paramasDic = new HashMap<String, Object>();

        paramasDic.put("status",signStatus);
        paramasDic.put("id",signId);
        paramasDic.put("add",addArry);

        final Map<String,String>  paramsDic2 = new HashMap<String, String>();
        paramsDic2.put("sign",JSON.toJSONString(paramasDic));

        Logger.getInstance(getApplicationContext()).info("存储签章合同信息参数 :" + paramsDic2.toString());

        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("存储签章合同信息成功");

                JSONObject map = JSON.parseObject(response,JSONObject.class);

                getUserPhone();
            }

            @Override
            public void fail(String response) {

                gotoLoginActivity();
            }
        });
        Logger.getInstance(getApplicationContext()).info("存储签章合同信息列表：" + request.url);
        request.postRequest(getApplicationContext(),paramsDic2);
    }


    private void getUserPhone(){
        VolleyRequest request = new VolleyRequest();

        request.url = URLConst.URL_GET_USER_PHONE + TokenUtil.token;
        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("获取用户手机号成功");

                JSONObject jsonObject = JSON.parseObject(response,JSONObject.class);
                JSONObject data = (JSONObject)jsonObject.get("data");

                JSONObject mobileDic = (JSONObject) data.get("mobile");
                String mobile = (String) mobileDic.get("mobile");

                Intent intent = new Intent(getApplicationContext(),SignMobileVerifyActivity.class);
                intent.putExtra("phone",mobile);
                intent.putExtra("signId",signId);
                intent.putExtra("signStatus",signStatus);
                startActivity(intent);
            }

            @Override
            public void fail(String response) {
                gotoLoginActivity();
            }
        });
        Logger.getInstance(getApplicationContext()).info("获取用户手机号：" + request.url);
        request.getRequest(getApplicationContext());
    }



}
