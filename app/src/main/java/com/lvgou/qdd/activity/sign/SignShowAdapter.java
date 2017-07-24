package com.lvgou.qdd.activity.sign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.lvgou.qdd.R;
import com.lvgou.qdd.http.URLConst;
import com.lvgou.qdd.http.VolleyRequest;

import java.util.List;

/**
 * Created by sampson on 2017/7/24.
 */

public class SignShowAdapter extends BaseAdapter {

    private List<Object> data;
    private Context mContext;

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



            convertView.setTag(holder);
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

    }
}
