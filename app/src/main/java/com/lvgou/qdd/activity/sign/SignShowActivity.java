package com.lvgou.qdd.activity.sign;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.http.URLConst;
import com.lvgou.qdd.util.Logger;
import com.lvgou.qdd.util.ToastUtil;
import com.lvgou.qdd.util.TokenUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SignShowActivity extends BaseActivity {
    private BaseActivity activity;

    private String signId;

    private Button backButton;

    private Button signDetailButton;

    private TextView navTitle;

    private ListView listView;

    private SignShowAdapter adapter;

    private LinkedList<Object> linkedList;

    private Button refuseButton;  //驳回按钮

    private Button signButton;  //签署按钮

    private View sepreateView; //分割线

    private Button cancelSignButton;  //撤销按钮

    /*************************个人授权**********************/
    private TextView signByPersonPerson; //代表个人签署
    private TextView signCanceButtonPerson; //取消签署
    /*************************个人授权**********************/

    /*************************企业授权**********************/
    private TextView signByEnterpriseEnterprise; //代表个人签署
    private TextView signByEnterprise;  //代表企业签署
    private TextView signCanceButtonenterprise; //取消签署
    /*************************企业授权**********************/

    private int orderStatus;  //1：待我签署 2：待他人签署 3：已完成 4：过期未签署

    private int authState; //授权状态 ,默认为1未授权

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sign_show);

        authState = 1;

        //获取合同id
        signId = getIntent().getStringExtra("signId");
        orderStatus = getIntent().getIntExtra("orderStatus",1); //如果不存在则用默认值1

        setView();

        setListView();

        netRequest();
    }


    private void setView(){
        backButton = (Button) findViewById(R.id.SignShowActivity_backButton);
        signDetailButton = (Button) findViewById(R.id.SignShowActivity_messageDetail);
        navTitle = (TextView) findViewById(R.id.SignShowActivity_middleMessage);
        listView = (ListView)findViewById(R.id.SignShowActivity_listView);

        signDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignDetailActivity.class);
                intent.putExtra("signId",signId);
                startActivity(intent);
            }
        });

        refuseButton = (Button) findViewById(R.id.SignShowActivity_refuseButton);
        signButton = (Button) findViewById(R.id.SignShowActivity_signbutton);
        sepreateView = findViewById(R.id.SignShowActivity_sepreate);
        cancelSignButton = (Button) findViewById(R.id.SignShowActivity_cancel_sign_button);

        /**********个人授权***************/
        signByPersonPerson = (TextView) findViewById(R.id.SignShowActivity_signByPerson_person);
        signCanceButtonPerson = (TextView) findViewById(R.id.SignShowActivity_signCancel_person);
        /**********个人授权***************/


        /**********企业授权***************/
        signByEnterpriseEnterprise = (TextView) findViewById(R.id.SignShowActivity_signByPerson_enterprise);
        signByEnterprise = (TextView) findViewById(R.id.SignShowActivity_signByEnterprise_enterprise);
        signCanceButtonenterprise = (TextView) findViewById(R.id.SignShowActivity_signCancel_enterprise);
        /**********企业授权***************/






        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.SignShowActivity_layout);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.SignShowActivity_linerlayout);

        final LinearLayout signLinearLayoutPerson = (LinearLayout) findViewById(R.id.SignShowActivity_signtType_person); //个人授权
        final LinearLayout signLinearLayoutEnterprise = (LinearLayout) findViewById(R.id.SignShowActivity_signtType_enterprise); //企业授权
        //首先移除签署选择类型
        relativeLayout.removeView(signLinearLayoutPerson);
        relativeLayout.removeView(signLinearLayoutEnterprise);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (authState == 1){
                    //添加个人选择签署类型按钮
                    relativeLayout.addView(signLinearLayoutPerson);
                }else {
                    //添加企业选择签署类型按钮
                    relativeLayout.addView(signLinearLayoutEnterprise);
                }

                //移除签署和取消签署
                relativeLayout.removeView(cancelSignButton);
                relativeLayout.removeView(linearLayout);
                relativeLayout.removeView(sepreateView);

            }
        });


        signCanceButtonPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout.removeView(signLinearLayoutPerson);

                relativeLayout.addView(linearLayout);
                relativeLayout.addView(sepreateView);
            }
        });

        signCanceButtonenterprise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout.removeView(signLinearLayoutEnterprise);

                relativeLayout.addView(linearLayout);
                relativeLayout.addView(sepreateView);
            }
        });


        signByPersonPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ChoosePersonSignatureActivity.class);
                intent.putExtra("signId",signId);
                startActivity(intent);
            }
        });

        refuseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加驳回弹窗
                setAlertView("确定","取消");
            }
        });

        cancelSignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加驳回弹窗
                setAlertView("确定","取消");
            }
        });



        switch (orderStatus){
            case 1:
                //待我签署
                relativeLayout.removeView(cancelSignButton);
                break;
            case 2:
                //待别人签署
                relativeLayout.removeView(linearLayout);
                relativeLayout.removeView(sepreateView);
                break;
            case 3:
                //已完成
                relativeLayout.removeView(cancelSignButton);
                relativeLayout.removeView(linearLayout);
                relativeLayout.removeView(sepreateView);
                break;
            case 4:
                //过期未签署
                relativeLayout.removeView(cancelSignButton);
                relativeLayout.removeView(linearLayout);
                relativeLayout.removeView(sepreateView);
                break;
            case 5:
                //已驳回
                relativeLayout.removeView(cancelSignButton);
                relativeLayout.removeView(linearLayout);
                relativeLayout.removeView(sepreateView);
            default:
                break;

        }
    }




    private void setListView(){
        linkedList = new LinkedList<>();
        adapter = new SignShowAdapter(getApplicationContext(),linkedList);
        listView.setAdapter(adapter);
    }

    @Override
    protected void netRequest() {
        super.netRequest();

        request.url = URLConst.URL_SIGN_SHOW + TokenUtil.token +"/id/" + signId + "/p/0";
        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("获取合同展示成功");

                Map<String,Object> map = JSON.parseObject(response,new HashMap<String,Object>().getClass());

                Map<String,Object> data = (Map<String, Object>) map.get("data");
                List<String> list = (List<String>) data.get("pic_name");

                for (String str: list) {
                    linkedList.add(str);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void fail(String response) {
                gotoLoginActivity();
            }
        });

        request.getRequest(getApplicationContext());
    }

    protected void  setAlertView(String confirm , String cancel){
        new AlertDialog.Builder(this).setTitle(null)//设置对话框标题
                .setMessage("是否驳回？")//设置显示的内容
                .setPositiveButton(confirm, new DialogInterface.OnClickListener() {
                    //添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                        //驳回合同
                        refuse();
                    }
                }).setNegativeButton(cancel,new DialogInterface.OnClickListener() {
                    //添加返回按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//响应事件

                }

        }).show();//在按键响应事件中显示此对话框
    }

    //驳回合同
    private void  refuse(){
        super.netRequest();

        request.url = URLConst.URL_REJECT_SIGN + TokenUtil.token +"/id/" + signId;
        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(getApplicationContext()).info("获取合同展示成功");

                Map<String,Object> map = JSON.parseObject(response,new HashMap<String,Object>().getClass());

                ToastUtil.showToast(getApplicationContext(),(String) map.get("info"));
                Intent intent = new Intent();
                setResult(0002,intent);
                finish();
            }

            @Override
            public void fail(String response) {
                gotoLoginActivity();
            }
        });
        Logger.getInstance(getApplicationContext()).info("驳回合同：" + request.url);
        request.getRequest(getApplicationContext());
    }

    //取消合同


    //签合同
    private void signSign(){

    }



}
