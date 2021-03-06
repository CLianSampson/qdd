package com.lvgou.qdd.activity.signature;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lvgou.qdd.R;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.http.URLConst;
import com.lvgou.qdd.http.VolleyRequest;
import com.lvgou.qdd.util.Logger;
import com.lvgou.qdd.util.ToastUtil;
import com.lvgou.qdd.util.TokenUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sampson on 2017/7/28.
 *
 */

public class SignatureAdapter extends BaseAdapter {
    private SignatureListActivity activity;

    private JSONArray personalList;
    private JSONArray enterpriseList;
    private Context mContext;

    public SignatureAdapter(Context mContext, JSONArray personalList, JSONArray enterpriseList, SignatureListActivity activity) {
        super();
        this.activity = activity;
        this.mContext = mContext;
        this.personalList = personalList;
        this.enterpriseList = enterpriseList;
    }

    @Override
    public int getCount() {
        Log.i("++++++",personalList.size()+"");
        Log.i("++++++",enterpriseList.size()+"");
        return personalList.size() + enterpriseList.size() + 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {//第一项和第二项属于一类，剩下的属于一类
        if (position == 0){
           return 0;
        }else if (position == personalList.size()+1){
           return 1;

        }else if (position>0 && position<personalList.size()+1){
            return 2;
        }else{
            return 3;
        }
    }

    @Override
    public int getViewTypeCount() {//表示我共有两种item要显示，
        return 4;
    }



//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//
//        ViewHolder holder;
//
//        holder = new SignatureAdapter.ViewHolder();
//
//        int type = getItemViewType(position);
//
//        if (position == 0){
//            if (null == convertView){
//                convertView = inflater.inflate(R.layout.listitem_signature_list_activity_tag, null);
//
//                holder.textView = (TextView) convertView.findViewById(R.id.SignatureListActivity_listitem_tag);
//                holder.textView.setText("个人签章");
//
//                convertView.setTag(holder);
//                return convertView;
//            }else {
////                holder = (ViewHolder) convertView.getTag();
////                holder.textView.setText("个人签章");
//                return convertView;
//            }
//        }else if (position == personalList.size()+1){
//            if (null == convertView){
//                convertView = inflater.inflate(R.layout.listitem_signature_list_activity_tag, null);
//
//                holder.textView = (TextView) convertView.findViewById(R.id.SignatureListActivity_listitem_tag);
//                holder.textView.setText("企业签章");
//
//                convertView.setTag(holder);
//                return convertView;
//            }else {
////                holder = (ViewHolder) convertView.getTag();
//                return convertView;
//            }
//
//        }else if (position>0 && position<personalList.size()+1){
//            if (null == convertView){
//                final int index = position;
//
//                //个人签章图片
//                convertView = inflater.inflate(R.layout.listitem_signature_list_activity, null);
//
//                holder.icon = (ImageView) convertView.findViewById(R.id.SignatureListActivity_listitem_icon);
//                holder.icon.setScaleType(ImageView.ScaleType.FIT_XY);
//
//                String url = URLConst.URL_COMMON + (((Map<String,String>) (personalList.get(position-1)))).get("path");
//                VolleyRequest request = new VolleyRequest();
//                request.downPicture(mContext, holder.icon,url);
//
//                holder.deleteButton = (Button) convertView.findViewById(R.id.SignatureListActivity_delete_button);
//                if (position == 1){
//                    holder.deleteButton.setVisibility(View.INVISIBLE);
//                }
//
//                holder.deleteButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        deleteSignature((((Map<String,String>) (personalList.get(index-1)))).get("id"));
//                    }
//                });
//
//                holder.icon.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Log.i("########","click");
//                        setDefaultSignature((((Map<String,String>) (personalList.get(index-1)))).get("id"));
//                    }
//                });
//
//                holder.choose = (ImageView) convertView.findViewById(R.id.choose_person_signature_choose);
//                holder.choose.setVisibility(View.INVISIBLE);
//
//                holder.unchoose = (ImageView) convertView.findViewById(R.id.choose_person_signature_unchoose);
//                holder.unchoose.setVisibility(View.INVISIBLE);
//
//                convertView.setTag(holder);
//                return convertView;
//            }else {
////                holder = (ViewHolder) convertView.getTag();
//                return convertView;
//            }
//        }else {
//            if (null == convertView){
//                //企业签章图片  if (position > personalList.size()+1)
//                convertView = inflater.inflate(R.layout.listitem_signature_list_activity, null);
//
//                holder.icon = (ImageView) convertView.findViewById(R.id.SignatureListActivity_listitem_icon);
//                holder.icon.setScaleType(ImageView.ScaleType.FIT_XY);
//
//                String url = URLConst.URL_COMMON + ((Map<String,String>) enterpriseList.get(position-personalList.size()-2)).get("path");
//                VolleyRequest request = new VolleyRequest();
//                request.downPicture(mContext, holder.icon,url);
//
//                holder.deleteButton = (Button) convertView.findViewById(R.id.SignatureListActivity_delete_button);
//                holder.deleteButton.setVisibility(View.INVISIBLE);
//
//
//                holder.choose = (ImageView) convertView.findViewById(R.id.choose_person_signature_choose);
//                holder.choose.setVisibility(View.INVISIBLE);
//
//                holder.unchoose = (ImageView) convertView.findViewById(R.id.choose_person_signature_unchoose);
//                holder.unchoose.setVisibility(View.INVISIBLE);
//
//                convertView.setTag(holder);
//                return convertView;
//
//            }else {
////                holder = (ViewHolder) convertView.getTag();
//                return convertView;
//            }
//
//
//        }
//    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        ViewHolder holder;

        holder = new SignatureAdapter.ViewHolder();

        int type = getItemViewType(position);

        if (position == 0){

                convertView = inflater.inflate(R.layout.listitem_signature_list_activity_tag, null);

                holder.textView = (TextView) convertView.findViewById(R.id.SignatureListActivity_listitem_tag);
                holder.textView.setText("个人签章");

                convertView.setTag(holder);
                return convertView;

        }else if (position == personalList.size()+1){

                convertView = inflater.inflate(R.layout.listitem_signature_list_activity_tag, null);

                holder.textView = (TextView) convertView.findViewById(R.id.SignatureListActivity_listitem_tag);
                holder.textView.setText("企业签章");

                convertView.setTag(holder);
                return convertView;


        }else if (position>0 && position<personalList.size()+1){

                final int index = position;

                //个人签章图片
                convertView = inflater.inflate(R.layout.listitem_signature_list_activity, null);

                holder.icon = (ImageView) convertView.findViewById(R.id.SignatureListActivity_listitem_icon);
                holder.icon.setScaleType(ImageView.ScaleType.FIT_XY);

                String url = URLConst.URL_COMMON + (((Map<String,String>) (personalList.get(position-1)))).get("path");
                VolleyRequest request = new VolleyRequest();
                request.downPicture(mContext, holder.icon,url);

                holder.deleteButton = (Button) convertView.findViewById(R.id.SignatureListActivity_delete_button);
                if (position == 1){
                    holder.deleteButton.setVisibility(View.INVISIBLE);
                }

                holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteSignature((((Map<String,String>) (personalList.get(index-1)))).get("id"));
                    }
                });

                holder.icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("########","click");
                        setDefaultSignature((((Map<String,String>) (personalList.get(index-1)))).get("id"));
                    }
                });

                holder.choose = (ImageView) convertView.findViewById(R.id.choose_person_signature_choose);
                holder.choose.setVisibility(View.INVISIBLE);

                holder.unchoose = (ImageView) convertView.findViewById(R.id.choose_person_signature_unchoose);
                holder.unchoose.setVisibility(View.INVISIBLE);

                convertView.setTag(holder);
                return convertView;

        }else {

                //企业签章图片  if (position > personalList.size()+1)
                convertView = inflater.inflate(R.layout.listitem_signature_list_activity, null);

                holder.icon = (ImageView) convertView.findViewById(R.id.SignatureListActivity_listitem_icon);
                holder.icon.setScaleType(ImageView.ScaleType.FIT_XY);

                String url = URLConst.URL_COMMON + ((Map<String,String>) enterpriseList.get(position-personalList.size()-2)).get("path");
                VolleyRequest request = new VolleyRequest();
                request.downPicture(mContext, holder.icon,url);

                holder.deleteButton = (Button) convertView.findViewById(R.id.SignatureListActivity_delete_button);
                holder.deleteButton.setVisibility(View.INVISIBLE);


                holder.choose = (ImageView) convertView.findViewById(R.id.choose_person_signature_choose);
                holder.choose.setVisibility(View.INVISIBLE);

                holder.unchoose = (ImageView) convertView.findViewById(R.id.choose_person_signature_unchoose);
                holder.unchoose.setVisibility(View.INVISIBLE);

                convertView.setTag(holder);
                return convertView;

        }
    }



    static class ViewHolder{
        TextView textView;

        ImageView icon;
        Button deleteButton;
        ImageView choose;
        ImageView unchoose;
    }



    //删除签章
    private  void  deleteSignature(String signatureId){
        VolleyRequest request = new VolleyRequest();

        request.url = URLConst.URL_DELETE_SLGNATURE + TokenUtil.token + "/signid/" + signatureId;
        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(mContext).info("删除签章成功");

                Map<String,Object> map = JSON.parseObject(response,new HashMap<String,Object>().getClass());

                Map<String,Object> data = (Map<String, Object>) map.get("data");

                //重新加载
                activity.enterpriseList.clear();
                activity.personalList.clear();
                activity.adapter.notifyDataSetChanged();

                activity.netRequest();
            }

            @Override
            public void fail(String response) {

            }
        });
        Logger.getInstance(mContext).info("删除签章：" + request.url);
        request.getRequest(mContext);
    }

    //设置默认签章
    private  void  setDefaultSignature(String signatureId){
        VolleyRequest request = new VolleyRequest();

        request.url = URLConst.URL_SET_DETAULT_SIGNATURE + TokenUtil.token + "/sid/" + signatureId;
        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(mContext).info("设置默认签章成功");

                Map<String,Object> map = JSON.parseObject(response,new HashMap<String,Object>().getClass());

                ToastUtil.showToast(activity.getApplicationContext(),(String) map.get("info"));

                //重新加载
                activity.enterpriseList.clear();
                activity.personalList.clear();
                activity.adapter.notifyDataSetChanged();

                activity.netRequest();
            }

            @Override
            public void fail(String response) {

            }
        });
        Logger.getInstance(mContext).info("设置默认签章：" + request.url);
        request.getRequest(mContext);
    }


}
