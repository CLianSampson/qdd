package com.lvgou.qdd.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvgou.qdd.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sampson on 2017/8/9.
 */

public class SmsCoeView extends RelativeLayout {

    private EditText editText;

    private TextView getSmsCodeButton;

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

    public SmsCoeView(Context context, AttributeSet attrs) {
        super(context,attrs);

        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.sms_code, this);
        editText = (EditText) view.findViewById(R.id.inputSmsCode);
        getSmsCodeButton = (TextView) view.findViewById(R.id.getSmsCode);

        getSmsCodeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        flag--;

                        getSmsCodeButton.post(new Runnable() {
                            @Override
                            public void run() {
                                getSmsCodeButton.setText("    " + flag + " " + "s" + "    ");
                                getSmsCodeButton.setBackgroundResource(R.drawable.button_radius_withoutborder_gray_backgroung);
                                getSmsCodeButton.setTextColor(Color.BLACK);
                                getSmsCodeButton.setClickable(false);
                            }
                        });




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


                    }
                },1000,1000); //1s后  每隔1s执行一次
            }
        });


    }


}
