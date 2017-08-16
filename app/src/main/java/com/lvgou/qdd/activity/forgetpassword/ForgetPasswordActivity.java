package com.lvgou.qdd.activity.forgetpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.util.StringUtil;
import com.lvgou.qdd.util.ToastUtil;
import com.lvgou.qdd.view.VerifyCodeView;

public class ForgetPasswordActivity extends BaseActivity {

    private Button backButton;

    private EditText phoneInput;

    private EditText verifyCodeInput;

    private VerifyCodeView verifyCodeView;

    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_forget_password);


        backButton = (Button) findViewById(R.id.ForgetPasswordActivity_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        phoneInput = (EditText) findViewById(R.id.ForgetPasswordActivity_phone_input);

        verifyCodeInput = (EditText) findViewById(R.id.ForgetPasswordActivity_verifycode_input);

        verifyCodeView = (VerifyCodeView) findViewById(R.id.ForgetPasswordActivity_verifycode);
        verifyCodeView.activity = this;
        verifyCodeView.getVerifyCodeByNet();

        nextButton = (Button) findViewById(R.id.ForgetPasswordActivity_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtil.isNullOrBlank(phoneInput.getText().toString())
                        || StringUtil.isNullOrBlank(verifyCodeInput.getText().toString())){
                    ToastUtil.showToast(getApplicationContext(),"输入内容不能为空");

                    return;
                }

                Intent intent = new Intent(getApplicationContext(),ForgetAndSetPasswordActivity.class);
                intent.putExtra("phone",phoneInput.getText().toString());
                intent.putExtra("verifyCode",verifyCodeInput.getText().toString());
                startActivity(intent);

            }
        });
    }





}
