package com.lvgou.qdd.activity.register;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
import com.lvgou.qdd.view.SmsCoeView;

import java.util.HashMap;
import java.util.Map;


public class UserFragment extends Fragment implements SmsCoeView.ISmsCoeView{

    private EditText phoneEditText;

    private SmsCoeView smsCoeView;

    private Button completeButon;

    private EditText passwordText;

    private EditText rePasswordText;


    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        phoneEditText = (EditText) view.findViewById(R.id.UserFragment_inputPhone);

        passwordText = (EditText) view.findViewById(R.id.UserFragment_password);

        rePasswordText = (EditText) view.findViewById(R.id.UserFragment_repassword);

        smsCoeView = (SmsCoeView) view.findViewById(R.id.UserFragment_smdCode);
        smsCoeView.activity = (BaseActivity) getActivity();
        smsCoeView.callback = this;

        completeButon  = (Button) view.findViewById(R.id.UserFragment_confirm);
        completeButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtil.isNullOrBlank(phoneEditText.getText().toString())
                        || StringUtil.isNullOrBlank(smsCoeView.getSmsCode())
                        || StringUtil.isNullOrBlank(passwordText.getText().toString())
                        || StringUtil.isNullOrBlank(rePasswordText.getText().toString()) ){

                    ToastUtil.showToast(getActivity().getApplicationContext(),"输入内容不能为空");

                    return;
                }





                netRequest();
            }
        });

        return view;
    }


    @Override
    public void getSmsCode() {
        smsCoeView.phone = phoneEditText.getText().toString();
    }

    private void netRequest(){
        VolleyRequest request = new VolleyRequest();

        request.url = URLConst.URL_REGISTER;
        final Map<String,String> map = new HashMap<>();
        map.put("mobile",phoneEditText.getText().toString());
        map.put("mobilecode",smsCoeView.getSmsCode());
        map.put("password",passwordText.getText().toString());
        map.put("repassword",rePasswordText.getText().toString());




        Logger.getInstance(getActivity().getApplicationContext()).info("个人用户注册参数是 :" + map.toString());

        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getActivity().getApplicationContext()).info("个人用户注册成功");

                JSONObject map = JSON.parseObject(response,JSONObject.class);

            }

            @Override
            public void fail(String response) {

                ((BaseActivity)getActivity()).gotoLoginActivity();
            }
        });
        Logger.getInstance(getActivity().getApplicationContext()).info("个人用户注册：" + request.url);
        request.postRequest(getActivity().getApplicationContext(),map);
    }
}
