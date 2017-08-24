package com.lvgou.qdd.activity.sign;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.lvgou.qdd.R;
import com.lvgou.qdd.http.RequestCallback;
import com.lvgou.qdd.http.URLConst;
import com.lvgou.qdd.http.VolleyRequest;
import com.lvgou.qdd.util.Logger;
import com.lvgou.qdd.util.TokenUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sampson on 2017/7/24.
 */

public class SignShowAdapter extends BaseAdapter {

    private List<Object> data;
    private Context mContext;

    public String signId;

    public SignShowAdapter(Context mContext, List<Object> data) {
        super();
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
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
        SignShowAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listitem_sign_show_activity, null);
            holder = new SignShowAdapter.ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.SignShowActivity_signIamge);

            holder.icon.setScaleType(ImageView.ScaleType.FIT_XY);


            holder.signature = (ImageView) convertView.findViewById(R.id.SignShowActivity_signature);
            holder.signature.setScaleType(ImageView.ScaleType.FIT_XY);
            holder.signature.setVisibility(View.INVISIBLE);

            holder.signature.setX(100);
            holder.signature.setY(100);

            convertView.setTag(holder);


            netRequest(position+1,signId,holder.signature);
        }else {
            holder = (SignShowAdapter.ViewHolder) convertView.getTag();
        }


        String url = URLConst.URL_COMMON + (String) data.get(position);
        VolleyRequest request = new VolleyRequest();
        request.downPicture(mContext,holder.icon,url);


        return convertView;

    }


    //    在getView方法中，Adapter先从xml中用inflate方法创建view对象，然后在这个view找到每一个控件。
    //    这里的findViewById操作是一个树查找过程，也是一个耗时的操作，所以这里也需要优化，就是使用viewHolder，把每一个控件都放在Holder中，
    //    当第一次创建convertView对象时，把这些控件找出来。然后用convertView的setTag将viewHolder设置到Tag中，以便系统第二次绘制ListView时从Tag中取出。
    //    当第二次重用convertView时，只需从convertView中getTag取出来就可以。
    //    作者：mengming
    //    链接：http://www.jianshu.com/p/24833a2cffd1
    //    來源：简书
    //    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

    static class ViewHolder{
        ImageView icon;
        ImageView signature;
    }


    //获取合同对应的签章id
    protected void netRequest(int pageNo, String signId, final ImageView signatureView) {
        VolleyRequest request = new VolleyRequest();

        request.url = URLConst.URL_SIGN_SHOW + TokenUtil.token +"/id/" + signId + "/p/" + pageNo;
        request.setCallback(new RequestCallback() {
            @Override
            public void sucess(String response) {
                Logger.getInstance(mContext).info("获取合同签章成功");

                Map<String,Object> map = JSON.parseObject(response,new HashMap<String,Object>().getClass());

                Map<String,Object> data = (Map<String, Object>) map.get("data");
                List<String> list = (List<String>) data.get("pic_name");
                if (null==list || list.size()==0){
                    return;
                }

                List<Map<String,Object>> signArry = (List<Map<String,Object>>) data.get("sign");
                if (null==signArry || signArry.size()==0){
                    return;
                }

                Log.i("signature",signArry.toString());

                for (Map<String,Object> temp: signArry) {

                    signatureView.setVisibility(View.VISIBLE);

//                    int x = [[temp objectForKey:@"posX"] intValue] ;
//                    int y = [[temp objectForKey:@"posY"] intValue] ;
//                    int x = (int)temp.get("posX");
//                    int y = (int)temp.get("posY");

                    float x = Float.valueOf((temp.get("posX")).toString());
                    float y = Float.valueOf((temp.get("posY")).toString());


                    DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
                    int  screenWidth = dm.widthPixels;
                    int  screenHeight = dm.heightPixels;

                    int xPosition = (int) ((((float)x)/((float) 758))*screenWidth);
                    int yPosition = (int) ((((float)y)/((float) 1072))*screenHeight);

                    signatureView.setX(xPosition);
                    signatureView.setY(yPosition);


                    String url = URLConst.URL_COMMON + (String) temp.get("path");
                    VolleyRequest request = new VolleyRequest();
                    request.downPicture(mContext,signatureView,url);
                }
            }

            @Override
            public void fail(String response) {

            }
        });

        request.getRequest(mContext);
    }



}
