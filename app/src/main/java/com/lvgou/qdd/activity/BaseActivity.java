package com.lvgou.qdd.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.lvgou.qdd.R;
import com.lvgou.qdd.http.VolleyRequest;


public abstract class BaseActivity extends Activity {
    protected VolleyRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_base);
            childImpl(savedInstanceState);
        }catch (Exception e){
            Log.i("exception",e.toString());
        }


    }

    protected abstract void childImpl(Bundle savedInstanceState);


    protected void netRequest(){
        request = new VolleyRequest();
    }

    protected void  setAlertView(String confirm , String cancel){
        new AlertDialog.Builder(this).setTitle("系统异常")//设置对话框标题

                .setMessage("请确认所有数据都保存后再推出系统！")//设置显示的内容

                .setPositiveButton(confirm, new DialogInterface.OnClickListener() {//添加确定按钮


                    @Override

                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                        // TODO Auto-generated method stub

                        finish();

                    }

                }).setNegativeButton(cancel,new DialogInterface.OnClickListener() {//添加返回按钮



            @Override
            public void onClick(DialogInterface dialog, int which) {//响应事件

                // TODO Auto-generated method stub

                Log.i("alertdialog", " 请保存数据！");

            }

        }).show();//在按键响应事件中显示此对话框

    }


    public void gotoLoginActivity(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void getSmsCode(){

    }
}
