package com.lvgou.qdd.activity.contact;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.http.URLConst;
import com.lvgou.qdd.http.VolleyRequest;
import com.lvgou.qdd.util.Logger;
import com.lvgou.qdd.util.StringUtil;
import com.lvgou.qdd.util.ToastUtil;
import com.lvgou.qdd.util.TokenUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class HaveContactFragment extends Fragment {

    private TextView nameTextView;

    private TextView accountTextview;

    private Button addButton;

    private Map<String,String> userMap;


    public HaveContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_have_contact, container, false);

        nameTextView = (TextView) view.findViewById(R.id.AddContactActivity_name);
        accountTextview = (TextView) view.findViewById(R.id.AddContactActivity_account);
        addButton = (Button) view.findViewById(R.id.AddContactActivity_add);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netRequest();

            }
        });

        Bundle bundle = getArguments();
        String userInfoStr = bundle.getString("userInfo");
        userMap = JSON.parseObject(userInfoStr,Map.class);

        if (StringUtil.isPhoneNum(userMap.get("search"))){
            accountTextview.setText("账号  "+userMap.get("mobile"));
        }else {
            accountTextview.setText(userMap.get("mail"));
        }

        nameTextView.setText("名称  "+userMap.get("name"));

        return  view;
    }



    private void netRequest(){
        VolleyRequest request = new VolleyRequest();

        request.url = URLConst.URL_ADD_USER + TokenUtil.token;
        final Map<String,String>  map = new HashMap<>();
        map.put("name",userMap.get("name"));
        map.put("mail",userMap.get("mail"));
        map.put("mobile",userMap.get("mobile"));

        Logger.getInstance(getActivity().getApplicationContext()).info("添加联系人参数是 :" + map.toString());

        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getActivity().getApplicationContext()).info("添加联系人成功");

                JSONObject map = JSON.parseObject(response,JSONObject.class);

                ToastUtil.showToast(getActivity().getApplicationContext(),map.get("info").toString());


            }

            @Override
            public void fail(String response) {

                ((BaseActivity)getActivity()).gotoLoginActivity();
            }
        });
        Logger.getInstance(getActivity().getApplicationContext()).info("添加联系人：" + request.url);
        request.postRequest(getActivity().getApplicationContext(),map);
    }


}
