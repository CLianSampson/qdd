package com.lvgou.qdd.activity.shopping;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.http.URLConst;
import com.lvgou.qdd.util.Logger;
import com.lvgou.qdd.util.TokenUtil;

import java.util.HashMap;
import java.util.Map;

public class PayActivity extends BaseActivity {

    private  String price;

    private  String orderId;

    private  Button backButton;

    private TextView priceTextView;

    private Button aliPayButton;

    private Button wechatbutton;

    private Button confirmButton;

    private int payFlag = 1;  //1代表支付宝支付  2微信支付

    private static  final  int SDK_PAY_FLAG = 1;

    private static  final  int SDK_AUTH_FLAG = 2;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SDK_PAY_FLAG:
                    Logger.getInstance(getApplicationContext()).info("支付宝支付返回 " + msg);
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);


                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(PayActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }

                    break;

                case SDK_AUTH_FLAG:
                    Logger.getInstance(getApplicationContext()).info("支付宝鉴权返回 " + msg);

                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatusAuth = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatusAuth, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(PayActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(PayActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }

                    break;
            }
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
        orderId = getIntent().getStringExtra("orderId");

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
           getAliPayParams();
        }else {
            wechat();
        }
    }


    private void getAliPayParams(){
        super.netRequest();

        int priceFloat = (int) (Float.valueOf(price)*100);

        Map<String,String> map = new HashMap<>();
        map.put("WIDout_trade_no",orderId);
        map.put("WIDsubject","套餐");
        map.put("WIDbody","套餐");
        map.put("WIDtotal_fee",""+priceFloat);

        String url = URLConst.URL_AILPAY + TokenUtil.token;

        request.url = url;

        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("获取支付宝支付参数 : " + response);

                aliPay(response);
            }

            @Override
            public void fail(String response) {

            }
        });
        request.aliPayPost(getApplicationContext(),map);
    }


    private void  aliPay(String order){
        final String orderInfo = order;   // 订单信息

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(PayActivity.this);
                Map<String,String> result = alipay.payV2(orderInfo,true);

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
