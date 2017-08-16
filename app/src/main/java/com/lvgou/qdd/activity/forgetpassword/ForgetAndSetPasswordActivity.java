package com.lvgou.qdd.activity.forgetpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.util.StringUtil;
import com.lvgou.qdd.util.ToastUtil;
import com.lvgou.qdd.view.SmsCoeView;

public class ForgetAndSetPasswordActivity extends BaseActivity implements SmsCoeView.ISmsCoeView{

    private Button backButton;

    private TextView phoneInput;



    private SmsCoeView smsCoeView;

    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_forget_and_set_password);

        backButton = (Button) findViewById(R.id.ForgetAndSetPasswordActivity_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        phoneInput = (TextView) findViewById(R.id.ForgetAndSetPasswordActivity_phone_input);
        phoneInput.setText(getIntent().getStringExtra("phone"));


        smsCoeView = (SmsCoeView) findViewById(R.id.ForgetAndSetPasswordActivity_smsCode);
        smsCoeView.activity = this;
        smsCoeView.callback = this;
        smsCoeView.getSmsCode();

        nextButton = (Button) findViewById(R.id.ForgetPasswordActivity_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtil.isNullOrBlank(phoneInput.getText().toString())
                        || StringUtil.isNullOrBlank(smsCoeView.getSmsCode())){
                    ToastUtil.showToast(getApplicationContext(),"输入内容不能为空");

                    return;
                }

                String phone = getIntent().getStringExtra("phone");
                String verifyCode = getIntent().getStringExtra("verifyCode");

                Intent intent = new Intent(getApplicationContext(),ResetPasswordActivity.class);

                intent.putExtra("phone",phone);
                intent.putExtra("verifyCode",verifyCode);
                intent.putExtra("smsCode",smsCoeView.getSmsCode());

                startActivity(intent);

            }
        });


    }


    @Override
    public void getSmsCode() {
        smsCoeView.phone = phoneInput.getText().toString();
    }
}
