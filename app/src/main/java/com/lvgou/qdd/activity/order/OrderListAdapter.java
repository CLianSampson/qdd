package com.lvgou.qdd.activity.order;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.activity.shopping.PayActivity;
import com.lvgou.qdd.util.ToastUtil;

import java.util.LinkedList;

/**
 * Created by sampson on 2017/8/6.
 */

public class OrderListAdapter extends BaseAdapter {

    private Context mContext;

    private LinkedList<JSONObject> linkedList;

    private BaseActivity activity;

    public OrderListAdapter(LinkedList<JSONObject> linkedList, Context context,BaseActivity activity){
        this.linkedList = linkedList;
        this.mContext = context;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return linkedList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        OrderListAdapter.ViewHolder holder = null;
//        if (convertView == null) {
//            convertView = inflater.inflate(R.layout.list_item_order_list_activity, null);
//            holder = new OrderListAdapter.ViewHolder();
//            holder.typeTextView = (TextView) convertView.findViewById(R.id.OrderListActivity_type);
//
//            holder.priceTextView = (TextView) convertView.findViewById(R.id.OrderListActivity_price);
//            holder.useTimesTextView = (TextView) convertView.findViewById(R.id.OrderListActivity_use_times);
//            holder.signDurationTextView = (TextView) convertView.findViewById(R.id.OrderListActivity_sign_duration);
//            holder.orderIdTextView = (TextView) convertView.findViewById(R.id.OrderListActivity_orderId);
//            holder.orderTimeTextView = (TextView) convertView.findViewById(R.id.OrderListActivity_order_time);
//
//            holder.gotoPayButton = (Button) convertView.findViewById(R.id.OrderListActivity_goto_pay);
//
//            convertView.setTag(holder);
//        }    else {
//            holder = (OrderListAdapter.ViewHolder) convertView.getTag();
//        }

        JSONObject jsonObject = linkedList.get(position);
        Integer orderStatus = Integer.valueOf(  (String) (jsonObject.get("status")) );

        holder = new OrderListAdapter.ViewHolder();

        if (orderStatus.intValue() == 0){
            convertView = inflater.inflate(R.layout.list_item_order_list_activity, null);

            holder.gotoPayButton = (Button) convertView.findViewById(R.id.OrderListActivity_goto_pay);

            holder.gotoPayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(mContext, PayActivity.class));
                }
            });
        }else if ( orderStatus.intValue() == 1){
            convertView = inflater.inflate(R.layout.list_item_order_list_activity_have_pay, null);


        }else {
            ToastUtil.showToast(mContext,"系统出错");
        }



        holder.typeTextView = (TextView) convertView.findViewById(R.id.OrderListActivity_type);

        holder.priceTextView = (TextView) convertView.findViewById(R.id.OrderListActivity_price);
        holder.useTimesTextView = (TextView) convertView.findViewById(R.id.OrderListActivity_use_times);
        holder.signDurationTextView = (TextView) convertView.findViewById(R.id.OrderListActivity_sign_duration);
        holder.orderIdTextView = (TextView) convertView.findViewById(R.id.OrderListActivity_orderId);
        holder.orderTimeTextView = (TextView) convertView.findViewById(R.id.OrderListActivity_order_time);


        holder.typeTextView.setText("套餐类型:  " + jsonObject.get("name").toString());
        holder.priceTextView.setText("价格:  " + jsonObject.get("price").toString());
        holder.useTimesTextView.setText("可使用次数:  " + jsonObject.get("num").toString());
        holder.signDurationTextView.setText("签约有效期:  " + jsonObject.get("ctime").toString());
        holder.orderIdTextView.setText("订单编号:  "+ jsonObject.get("orderid").toString());
        holder.orderTimeTextView.setText("下单时间:  " + jsonObject.get("ctime").toString());


        return convertView;

    }



    static class ViewHolder{
        TextView typeTextView;
        TextView priceTextView;
        TextView useTimesTextView;
        TextView signDurationTextView;
        TextView orderIdTextView;
        TextView orderTimeTextView;

        Button gotoPayButton;

    }
}
