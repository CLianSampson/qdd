package com.lvgou.qdd.activity.shopping;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;

public class PayActivity extends BaseActivity {

    private  String price;

    private  Button backButton;

    private TextView priceTextView;

    private Button aliPayButton;

    private Button wechatbutton;

    private Button confirmButton;

    private int payFlag = 1;  //1代表支付宝支付  2微信支付

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        childImpl(savedInstanceState);
    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        backButton = (Button) findViewById(R.id.PayActivity_backButton);
        priceTextView = (TextView) findViewById(R.id.PayActivity_price);
        aliPayButton = (Button) findViewById(R.id.PayActivity_alipay);
        wechatbutton = (Button) findViewById(R.id.PayActivity_wechet);
        confirmButton = (Button) findViewById(R.id.PayActivity_confirm);

        price = getIntent().getStringExtra("price");

        priceTextView.setText("需要支付:  " + price + "元");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        aliPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aliPayButton.setBackgroundResource(R.drawable.pay_choose);
                wechatbutton.setBackgroundResource(R.drawable.pay_unchoose);

                payFlag = 1;
            }
        });


        wechatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wechatbutton.setBackgroundResource(R.drawable.pay_choose);
                aliPayButton.setBackgroundResource(R.drawable.pay_unchoose);

                payFlag = 2;
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay();
            }
        });
    }


    private  void pay(){
        if (payFlag == 1){
            aliPay();
        }else {
            wechat();
        }
    }

    private void  aliPay(){

    }



    private void  wechat(){

    }

}
