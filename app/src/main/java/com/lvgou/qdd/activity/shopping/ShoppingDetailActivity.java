package com.lvgou.qdd.activity.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.model.Shopping;

public class ShoppingDetailActivity extends BaseActivity {

    private Button backButoon;

    private TextView typeTextView;

    private TextView durationTextView;

    private TextView totalTimesTextView;

    private TextView contentTextView;

    private TextView priceTextView;

    private  Button buyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        childImpl(savedInstanceState);
    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shopping_detail);

        setView();
    }

    private  void  setView(){
        backButoon = (Button) findViewById(R.id.ShoppingDetailActivity_backButton);
        backButoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        typeTextView = (TextView) findViewById(R.id.ShoppingDetailActivity_type);
        durationTextView = (TextView) findViewById(R.id.ShoppingDetailActivity_duration);
        totalTimesTextView = (TextView) findViewById(R.id.ShoppingDetailActivity_totalTimes);
        contentTextView = (TextView) findViewById(R.id.ShoppingDetailActivity_content);
        priceTextView = (TextView) findViewById(R.id.ShoppingDetailActivity_price);

        buyButton = (Button) findViewById(R.id.ShoppingDetailActivity_buy);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PayActivity.class);
                startActivity(intent);
            }
        });


        Intent intent=getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent
        String str = intent.getStringExtra("good");

        Shopping.Good good = JSON.parseObject(str,Shopping.Good.class);

        String type = "套餐类型: " + good.getName();
        String totalTimes = "总次数: " + good.getNum() + "次";
        String content = good.getContents();
        String prcie = ""+Float.valueOf(good.getPrice())/100;

        typeTextView.setText(type);
        totalTimesTextView.setText(totalTimes);
        contentTextView.setText(content);
        priceTextView.setText(prcie);


    }
}
