package com.lvgou.qdd.activity.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvgou.qdd.R;
import com.lvgou.qdd.model.Message;

import java.util.List;

/**
 * Created by sampson on 2017/7/23.
 */

public class MessageListAdapter extends BaseAdapter {

    private List<Message> data;
    private Context mContext;

    public MessageListAdapter(Context mContext, List<Message> data) {
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listitem_messagelistactivity, null);
            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.MessageListActivity_read_picture);

            holder.title = (TextView) convertView.findViewById(R.id.MessageListActivity_title);
            holder.content = (TextView) convertView.findViewById(R.id.MessageListActivity_content);
            holder.time = (TextView) convertView.findViewById(R.id.MessageListActivity_time);

            convertView.setTag(holder);
        }    else {
            holder = (ViewHolder) convertView.getTag();
        }


        Message message = data.get(position);
        if (null == message.getStatus()){
            holder.icon.setImageResource(R.drawable.message_unread);
        }else {
            holder.icon.setImageResource(R.drawable.message_read);
        }


        holder.title.setText(message.getTitle());
        holder.content.setText(message.getContents());
        holder.time.setText(message.getCtime());


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
        TextView title;
        TextView content;
        TextView time;
    }
}
