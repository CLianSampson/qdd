package com.lvgou.qdd.activity.setting;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.http.URLConst;
import com.lvgou.qdd.util.Logger;
import com.lvgou.qdd.util.StringUtil;
import com.lvgou.qdd.util.ToastUtil;
import com.lvgou.qdd.util.TokenUtil;

import java.util.HashMap;
import java.util.Map;

public class SetPasswordActivity extends BaseActivity {

    private Button backButton;

    private Button completeButton;

    private TextView accountTextView;

    private EditText oldPassword;

    private EditText newPassword;

    private EditText confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_set_password);

        backButton = (Button) findViewById(R.id.SetPasswordActivity_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        completeButton = (Button) findViewById(R.id.SetPasswordActivity_complete);
        completeButton.setBackgroundColor(Color.TRANSPARENT);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netRequest();
            }
        });


        accountTextView = (TextView) findViewById(R.id.SetPasswordActivity_account);

        accountTextView.setText(getIntent().getStringExtra("idName"));

        oldPassword = (EditText) findViewById(R.id.SetPasswordActivity_oldPassword);
        newPassword = (EditText) findViewById(R.id.SetPasswordActivity_newPassword);
        confirmPassword = (EditText) findViewById(R.id.SetPasswordActivity_comfirm);

        //设置光标在最后
        oldPassword.setSelection(oldPassword.getText().length());
        newPassword.setSelection(newPassword.getText().length());
        confirmPassword.setSelection(confirmPassword.getText().length());

//        oldPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                String hint;
//                EditText editText = (EditText) v;
//                if (hasFocus) {
//                    hint = editText.getHint().toString();
//                    editText.setTag(hint);
//                    editText.setHint("");
//                } else {
//                    hint = editText.getTag().toString();
//                    editText.setHint(hint);
//                }
//            }
//        });
//
//        newPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                String hint;
//                EditText editText = (EditText) v;
//                if (hasFocus) {
//                    hint = editText.getHint().toString();
//                    editText.setTag(hint);
//                    editText.setHint("");
//                } else {
//                    hint = editText.getTag().toString();
//                    editText.setHint(hint);
//                }
//            }
//        });
//
//        confirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                String hint;
//                EditText editText = (EditText) v;
//                if (hasFocus) {
//                    hint = editText.getHint().toString();
//                    editText.setTag(hint);
//                    editText.setHint("");
//                } else {
//                    hint = editText.getTag().toString();
//                    editText.setHint(hint);
//                }
//            }
//        });

    }

    @Override
    protected void netRequest() {
        super.netRequest();

        request.url = URLConst.URL_RESET_PASSWORD + TokenUtil.token;
        final Map<String,String> map = new HashMap<>();

        if (StringUtil.isNullOrBlank(oldPassword.getText().toString())){
            ToastUtil.showToast(getApplicationContext(),"旧密码不能为空");
            return;
        }

        if ( StringUtil.isNullOrBlank(newPassword.getText().toString())){
            ToastUtil.showToast(getApplicationContext(),"新密码不能为空");
            return;
        }

        if (StringUtil.isNullOrBlank(confirmPassword.getText().toString())){
            ToastUtil.showToast(getApplicationContext(),"确认密码不能为空");
            return;
        }

        if (!newPassword.getText().toString().equals(confirmPassword.getText().toString())){
            ToastUtil.showToast(getApplicationContext(),"两次密码不相同");
            return;
        }

        map.put("pwd",oldPassword.getText().toString());
        map.put("newpwd",newPassword.getText().toString());
        map.put("renewpwd",confirmPassword.getText().toString());

        Logger.getInstance(getApplicationContext()).info("重置密码参数是 :" + map.toString());

        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("重置密码成功");

                JSONObject map = JSON.parseObject(response,JSONObject.class);
                ToastUtil.showToast(getApplicationContext(),(String) map.get("info"));

            }

            @Override
            public void fail(String response) {

                gotoLoginActivity();
            }
        });
        Logger.getInstance(getApplicationContext()).info("重置密码：" + request.url);
        request.postRequest(getApplicationContext(),map);
    }




}
