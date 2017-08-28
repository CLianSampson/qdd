package com.lvgou.qdd.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.http.URLConst;
import com.lvgou.qdd.http.VolleyRequest;
import com.lvgou.qdd.util.Logger;

/**
 * Created by sampson on 2017/8/15.
 */

public class VerifyCodeView extends RelativeLayout {

    public BaseActivity activity;

    private ImageView imageView;

    private Button changeBitton;


    public VerifyCodeView(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.verify_code, this);

        imageView = (ImageView) view.findViewById(R.id.verifyCodeImage);

        changeBitton = (Button) findViewById(R.id.changeVerifyCode);
        changeBitton.setBackgroundColor(Color.TRANSPARENT);
        changeBitton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getVerifyCodeByNet();
            }
        });

    }

    public void getVerifyCodeByNet(){
        VolleyRequest request = new VolleyRequest();

        request.url = URLConst.URL_PICTURE_CODE;
        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(activity.getApplicationContext()).info("获取图片验证码成功");
            }

            @Override
            public void fail(String response) {
            }
        });
        Logger.getInstance(activity.getApplicationContext()).info("获取图片验证码的url : " + request.url);
        request.downPictureNoCache(activity.getApplicationContext(),imageView,request.url);
    }

}