package com.lvgou.qdd.activity.sign;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.http.URLConst;
import com.lvgou.qdd.http.VolleyRequest;
import com.lvgou.qdd.util.Constant;
import com.lvgou.qdd.util.Logger;
import com.lvgou.qdd.util.ToastUtil;
import com.lvgou.qdd.util.TokenUtil;

import java.util.HashMap;
import java.util.Map;

public class ChoosePersonSignatureActivity extends BaseActivity {

    private Button backButton;

    private Button completeButton;

    private ListView listView;

    public JSONArray personalList;

    public PersonAdapter adapter;

    private String signId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_choose_person_signature);

        signId = getIntent().getStringExtra("signId");

        backButton = (Button) findViewById(R.id.ChoosePersonSignatureActivity_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        completeButton = (Button) findViewById(R.id.ChoosePersonSignatureActivity_complete);
        completeButton.setBackgroundColor(Color.TRANSPARENT);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == PassImageView.passImageView){
                    ToastUtil.showToast(getApplicationContext(),"签名为空");
                }


                Intent intent = new Intent(getApplicationContext(),SetPositionActivity.class);
                intent.putExtra("signId",signId);
                intent.putExtra("signStatus", Constant.SIGN_BY_PERSON+"");
                startActivity(intent);
            }
        });

        setListView();

        netRequest();

    }



    private void  setListView(){
        listView = (ListView) findViewById(R.id.ChoosePersonSignatureActivity_listview);
        personalList = new JSONArray();

        adapter = new PersonAdapter(getApplicationContext(),personalList);
        listView.setAdapter(adapter);

        setListItemClickEvent();
    }

    private  void  setListItemClickEvent(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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

                if (null==personListTemp || personListTemp.size()==0)return;

                for (Object object: personListTemp) {
                    personalList.add(object);
                }


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



    private static class PersonAdapter extends BaseAdapter{

        private JSONArray personalList;
        private Context mContext;

        public PersonAdapter(Context mContext, JSONArray personalList) {
            super();
            this.mContext = mContext;
            this.personalList = personalList;
        }

        @Override
        public int getCount() {
            if (null==personalList || personalList.size()==0) return 1;
            return  2;
        }
        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(mContext);

            if (position == 0){
                convertView = inflater.inflate(R.layout.listitem_signature_list_activity_tag, null);

                TextView textView = (TextView) convertView.findViewById(R.id.SignatureListActivity_listitem_tag);
                textView.setText("个人签章");
                return convertView;

            }else{
                //个人签章图片
                convertView = inflater.inflate(R.layout.listitem_signature_list_activity, null);

                ImageView icon = (ImageView) convertView.findViewById(R.id.SignatureListActivity_listitem_icon);
                icon.setScaleType(ImageView.ScaleType.FIT_XY);

                Log.i("position is : ",position+"");
                Log.i("personalList is : ",personalList.toString());

                String url = URLConst.URL_COMMON + (((Map<String,String>) (personalList.get(position-1)))).get("path");
                VolleyRequest request = new VolleyRequest();
                request.downPicture(mContext,icon,url);

                //用来传递对象
                PassImageView.passImageView = icon;


                Button deleteButton = (Button) convertView.findViewById(R.id.SignatureListActivity_delete_button);
                deleteButton.setVisibility(View.INVISIBLE);

                final ImageView choose = (ImageView) convertView.findViewById(R.id.choose_person_signature_choose);

                icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        choose.setVisibility(View.VISIBLE);
                    }
                });

                return convertView;
            }
        }

    }


}
