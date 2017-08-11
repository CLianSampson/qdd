package com.lvgou.qdd.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
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

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sampson on 2017/8/9.
 */

public class SmsCoeView extends RelativeLayout {

    public BaseActivity activity;

    public String phone;

    private TextView getSmsCodeButton;

    private EditText inputSmsCode;

    private  static  int flag = 60;





    //View类有三个构造方法，你在继承时，至少要覆写其中一个，以便创建你的自定义View。
//    这三个构造方法分别是一个参数，两个参数和三个参数的。
//
//    一个参数的：在自己new对象建立此View时调用，也就是上面的new DrawView(this)这种。
//    二个参数的：在xml中声明自定义View时调用，就是在main.xml里声明了<xx.xx.DrawView>这种
//    三个参数的：也是在XML中声明自定义View时调用，但与两个参数的区别是这个加入了Style样式的引用，也就是说，如果你的 main.xml里<xx.xx.DrawView>中加入了style="@style/xxx.xml"，就会调用这个三参的方法。
//
//    综合以上所说的，如果三个构造方法都覆写，那么你的自定义View怎么建立都行。
//    如果只想通过java程序代码建立，那么只需要覆写一个参数的。
//    如果只想通过XML建立，那么需要覆写两个参数的，如果XML建立还想带样式，那么就必须同时覆写三个参数的构造方法了。

    //见链接   https://zhidao.baidu.com/question/583306960.html

    public SmsCoeView(final Context context, final AttributeSet attrs) {
        super(context,attrs);

        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.sms_code, this);


        inputSmsCode = (EditText) view.findViewById(R.id.inputSmsCode);

        getSmsCodeButton = (TextView) view.findViewById(R.id.getSmsCode);

        getSmsCodeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        //以下顺序不能改变

                        activity.getSmsCode();

                        Logger.getInstance(activity.getApplicationContext()).info("+++++++++++++++++++++++++++++++++++++++++");
                        Logger.getInstance(activity.getApplicationContext()).info("+++++++++++++++++++++++++++++++++++++++++  phone is :" + phone );

                        if (StringUtil.isNullOrBlank(phone)){

                            getSmsCodeButton.post(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.showToast(activity.getApplicationContext(),"手机号不能为空");
                                }
                            });

                            timer.cancel();
                            return;
                        }


                        getSmsCodeButton.post(new Runnable() {
                            @Override
                            public void run() {
                                getSmsCodeButton.setText("    " + flag + " " + "s" + "    ");
                                getSmsCodeButton.setBackgroundResource(R.drawable.button_radius_withoutborder_gray_backgroung);
                                getSmsCodeButton.setTextColor(Color.BLACK);
                                getSmsCodeButton.setClickable(false);
                            }
                        });

                        if (flag == 60){
                            getSmsCodeByNet();
                        }

                        flag--;


                        if (flag>0){
                            return;
                        }

                        getSmsCodeButton.post(new Runnable() {
                            @Override
                            public void run() {
                                getSmsCodeButton.setText("获取验证码");
                                getSmsCodeButton.setClickable(true);
                                getSmsCodeButton.setBackgroundResource(R.drawable.button_radius_background);
                                getSmsCodeButton.setTextColor(Color.WHITE);
                            }
                        });


                        flag = 60;
                        timer.cancel();


                    }
                },1000,1000); //1s后  每隔1s执行一次
            }
        });

    }


    public String getSmsCode(){
        return inputSmsCode.getText().toString();
    }


    private void getSmsCodeByNet(){
        VolleyRequest request = new VolleyRequest();

        request.url = URLConst.URL_SMS  + "mobile=" + phone;
        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(activity.getApplicationContext()).info("获取短信验证码成功");

                JSONObject responseData = JSON.parseObject(response,JSONObject.class);

                ToastUtil.showToast(activity.getApplicationContext(),"短信验证码已发送");

            }

            @Override
            public void fail(String response) {

            }
        });
        Logger.getInstance(activity.getApplicationContext()).info("获取短信验证码的url : " + request.url);
        request.getRequest(activity.getApplicationContext());
    }

}
