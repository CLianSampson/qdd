package com.lvgou.qdd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.lvgou.qdd.R;
import com.lvgou.qdd.model.SignatureFirstPage;
import com.lvgou.qdd.view.NavigtionView;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private NavigtionView navigtionView;

    private Button leftButton;

    private  Button rightButton;

    private TextView middleText;

    private ListView firstPageListView;

    private List<SignatureFirstPage> signatureFirstPageList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

//        navigtionView = (NavigtionView)findViewById(R.id.nav);
//
//        navigtionView.setMessage("我的acitivity");
//
//        navigtionView.setLeftButtonImage(R.drawable.icon_home);
//
//        navigtionView.setRightButtonImage(R.drawable.icon_calendar);

        leftButton = (Button)findViewById(R.id.leftButton);
//        leftButton.setBackgroundResource(R.mipmap.menu);

        middleText = (TextView)findViewById(R.id.middleMessage);
//        middleText.setText("我的列表");
        TextPaint tp = middleText.getPaint();
        tp.setFakeBoldText(true);

        rightButton = (Button)findViewById(R.id.rightButton);
//        rightButton.setBackgroundResource(R.mipmap.menu);

        setListener();

        setListView();

    }


    private  void  setListener(){
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info","kkkkkkkkkkkk");
                finish();
            }
        });
    }

    private  void setListView(){
        firstPageListView = (ListView)findViewById(R.id.firstPageListView);

        signatureFirstPageList = new ArrayList<>();
        signatureFirstPageList.add(new SignatureFirstPage("chenlian"));
        signatureFirstPageList.add(new SignatureFirstPage("chenlian"));
        signatureFirstPageList.add(new SignatureFirstPage("chenlian"));

        // 这里ListView的适配器选用ArrayAdapter，ListView中每一项的布局选用系统的simple_list_item_1。
        ArrayAdapter<SignatureFirstPage> adapter = new ArrayAdapter<SignatureFirstPage>(this, android.R.layout.simple_list_item_1, signatureFirstPageList);
        firstPageListView.setAdapter(adapter);



        // 通过一个实现OnItemClickListener接口的匿名类的onItemClick方法来处理ListView中每一项的点击事件。
        firstPageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("test click","gggg");
            }
        });
    }
}
