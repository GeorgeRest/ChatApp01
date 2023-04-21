package com.george.chatapp.adapters;




import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.george.chatapp.R;
import com.george.chatapp.beans.UserItem;

import java.util.List;


public class ChatListAdapter extends BaseAdapter {
    private int resource;
    private Context context;
    private List<UserItem> userItemList;

    public ChatListAdapter(Context context,int resource,  List<UserItem> userItemList) {
        this.resource = resource;
        this.context = context;
        this.userItemList = userItemList;
    }

    @Override
    public int getCount() {
        return userItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return userItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        UserItem user = (UserItem) getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(resource, parent,false);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.img1);
            viewHolder.title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.content = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.image.setImageResource(user.getImageId());
        viewHolder.title.setText(user.getUserName());
        viewHolder.content.setText(user.getContent());
        viewHolder.time.setText(user.getTime());
        return convertView;
    }

    private static class ViewHolder {
        ImageView image;
        TextView title;
        TextView content;
        TextView time;
    }

}

