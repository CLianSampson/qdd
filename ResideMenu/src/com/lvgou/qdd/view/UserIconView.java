package com.lvgou.qdd.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.special.ResideMenu.R;

/**
 * Created by apple on 17/3/3.
 */
public class UserIconView extends RelativeLayout{

    private Button icon;

    private TextView phone;

    public  UserIconView(Context context){
        super(context);
        initView(context);
    }


    private  void  initView(Context context){
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(com.special.ResideMenu.R.layout.user_icon, this);
        icon = (Button) findViewById(com.special.ResideMenu.R.id.userIcon);
        phone= (TextView) findViewById(com.special.ResideMenu.R.id.userphone);
    }

    public Button getIcon() {
        return icon;
    }

    public TextView getPhone() {
        return phone;
    }

    public void setIcon(Button icon) {
        this.icon = icon;
    }

    public void setPhone(TextView phone) {
        this.phone = phone;
    }
}
