package com.lvgou.qdd.activity.register;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
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
import com.lvgou.qdd.view.VerifyCodeView;

import java.util.HashMap;
import java.util.Map;


public class EnterpriseFragment extends Fragment {

    public interface IEnterpriseFragment{
        public void  IEnterpriseFragmentMethod();
    }

    public IEnterpriseFragment callBack;

    private VerifyCodeView verifyCodeView;

    private EditText emailText;

    private EditText verifyCodeText;

    private EditText passwordText;

    private EditText rePasswordText;

    private Button completeButon;

    /**
     * fragment与activity产生关联是  回调这个方法
     */
    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        //当前fragment从activity重写了回调接口  得到接口的实例化
        // 对象
        callBack =(IEnterpriseFragment) getActivity();

        Log.i("+++++++++++++++++=","111111111111111111111111111");
    }


    public EnterpriseFragment() {
        // Required empty public constructor
        Log.i("+++++++++++++++++=","222222222222222222222222222");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Log.i("+++++++++++++++++=","3333333333333333333333333333");

        View view = inflater.inflate(R.layout.fragment_enterprise, container, false);

        emailText = (EditText) view.findViewById(R.id.EnterpriseFragment_mail);
        verifyCodeText = (EditText) view.findViewById(R.id.EnterpriseFragment_inputVerifyCode);
        passwordText = (EditText) view.findViewById(R.id.EnterpriseFragment_password);
        rePasswordText = (EditText) view.findViewById(R.id.EnterpriseFragment_repassword);

        verifyCodeView = (VerifyCodeView) view.findViewById(R.id.EnterpriseFragment_verifyCode);
        verifyCodeView.activity = (BaseActivity) getActivity();
        verifyCodeView.getVerifyCodeByNet();

        completeButon  = (Button) view.findViewById(R.id.UserFragment_confirm);
        completeButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtil.isNullOrBlank(emailText.getText().toString())
                        || StringUtil.isNullOrBlank(verifyCodeText.getText().toString())
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



    private void netRequest(){
        VolleyRequest request = new VolleyRequest();

        request.url = URLConst.URL_REGISTER;
        final Map<String,String> map = new HashMap<>();
        map.put("email",emailText.getText().toString());
        map.put("verify",verifyCodeText.getText().toString());
        map.put("password",passwordText.getText().toString());
        map.put("repassword",rePasswordText.getText().toString());




        Logger.getInstance(getActivity().getApplicationContext()).info("企业用户注册参数是 :" + map.toString());

        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getActivity().getApplicationContext()).info("企业用户注册成功");

                JSONObject map = JSON.parseObject(response,JSONObject.class);

            }

            @Override
            public void fail(String response) {

                ((BaseActivity)getActivity()).gotoLoginActivity();
            }
        });
        Logger.getInstance(getActivity().getApplicationContext()).info("企业用户注册：" + request.url);
        request.postRequest(getActivity().getApplicationContext(),map);
    }

}
