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
import com.lvgou.qdd.util.DateUtil;
import com.lvgou.qdd.util.ToastUtil;

import java.util.Date;
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
        final LayoutInflater inflater = LayoutInflater.from(mContext);
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

        final JSONObject jsonObject = linkedList.get(position);
        Integer orderStatus = Integer.valueOf(  (String) (jsonObject.get("status")) );

        holder = new OrderListAdapter.ViewHolder();

        if (orderStatus.intValue() == 0){
            convertView = inflater.inflate(R.layout.list_item_order_list_activity, null);

            holder.gotoPayButton = (Button) convertView.findViewById(R.id.OrderListActivity_goto_pay);

            holder.gotoPayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PayActivity.class);
                    intent.putExtra("price",(Number)jsonObject.get("price")+"");
                    intent.putExtra("orderId",(String) jsonObject.get("orderid"));
                    activity.startActivity(intent);
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


        Date createTime = DateUtil.stringToDateFormat((String) jsonObject.get("ctime"),DateUtil.TIME_NORMAL_FORMAT);
        Date endTime = DateUtil.stringToDateFormat((String) jsonObject.get("etime"),DateUtil.TIME_NORMAL_FORMAT);
        long day = 0;
        if (null!=createTime && null!=endTime){
            long timeInterval = endTime.getTime() - createTime.getTime();
            day =  timeInterval/(24*60*60*1000);

        }

        holder.typeTextView.setText("套餐类型:  " + (String) jsonObject.get("name"));
        holder.priceTextView.setText("价格:  " + (Number) jsonObject.get("price") + "元");
        holder.useTimesTextView.setText("可使用次数:  " + (String) jsonObject.get("num") + "次");
        holder.signDurationTextView.setText("签约有效期:  " + day + "天");
        holder.orderIdTextView.setText("订单编号:  " + (String) jsonObject.get("orderid"));
        holder.orderTimeTextView.setText("下单时间:  " + (String) jsonObject.get("ctime"));


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
