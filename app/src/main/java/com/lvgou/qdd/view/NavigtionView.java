package com.lvgou.qdd.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvgou.qdd.R;

/**
 * Created by apple on 17/3/3.
 */
public class NavigtionView extends FrameLayout {
    private Button leftButton;

    private TextView middleText;

    private  Button rightButton;



    public NavigtionView(Context context) {
        super(context);

        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.navigation_xml, this);
        leftButton = (Button) findViewById(R.id.leftButton);
        middleText = (TextView) findViewById(R.id.middleMessage);
        rightButton = (Button) findViewById(R.id.rightButton);
    }

    public void setMessage(String message){
        middleText.setText(message);
    }


    public  void setLeftButtonImage(int icon){
        leftButton.setBackgroundResource(icon);
    }

    public  void  setRightButtonImage(int icon){
        rightButton.setBackgroundResource(icon);
    }
}
