package com.special.lvgou;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.special.ResideMenu.R;


/**
 * Created by sampson on 2017/8/16.
 */

public class UserIcon extends LinearLayout {

    public interface IUserIcon{
        public void gotoVerifyActivity();
    }

    public  IUserIcon delegate;

    private Button userIconButton;

    private TextView account;

    private ImageView verifyState;

    public UserIcon(final Context context, final AttributeSet attrs) {
        super(context,attrs);

        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.user_icon, this);

        View view = inflater.inflate(R.layout.user_icon,this);

        userIconButton = (Button) view.findViewById(R.id.userIcon_userIcon);
        account = (TextView) view.findViewById(R.id.userIcon_account);
        verifyState = (ImageView) findViewById(R.id.userIcon_verify_state);

        userIconButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.gotoVerifyActivity();
            }
        });
    }

    public void  setAccount(String account){
        this.account.setText(account);
    }

    public void setHaveVerify(){
        verifyState.setImageDrawable(getResources().getDrawable(R.drawable.have_verify));
    }

    public void setUnVerify(){
        verifyState.setImageDrawable(getResources().getDrawable(R.drawable.un_verify));
    }

}
