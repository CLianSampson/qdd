package com.lvgou.qdd.activity.shopping;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.service.media.MediaBrowserService;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
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

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            MediaBrowserService.Result result = new MediaBrowserService.Result((String) msg.obj);
            Toast.makeText(PayActivity.this, result.getResult(),
                    Toast.LENGTH_LONG).show();
        };
    };

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
        final String orderInfo = "";   // 订单信息

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(PayActivity.this);
                String result = alipay.payV2(orderInfo,true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private void  wechat(){

    }

}
