package com.george.chatapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.george.chatapp.R;
import com.george.chatapp.beans.Msg;

import java.util.List;

public class MessageAdapter extends BaseAdapter {
    private Context context;
    private int resource;
    private List<Msg> msgList;
    private Msg msg;


    public MessageAdapter(Context context, @LayoutRes int resource, List<Msg> msgList) {
        this.context = context;
        this.resource = resource;
        this.msgList = msgList;
    }

    @Override
    public int getCount() {
        return msgList.size();
    }

    @Override
    public Object getItem(int position) {
        return msgList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        msg = (Msg) getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {

            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(resource, null);
            viewHolder.left_layout = (LinearLayout) convertView.findViewById(R.id.left_layout);
            viewHolder.right_layout = (LinearLayout) convertView.findViewById(R.id.right_layout);
            viewHolder.left_msg = (TextView) convertView.findViewById(R.id.left_msg);
            viewHolder.right_msg = (TextView) convertView.findViewById(R.id.right_msg);
            viewHolder.leftTime = (TextView) convertView.findViewById(R.id.left_time);
            viewHolder.right_time = (TextView) convertView.findViewById(R.id.right_time);
            viewHolder.left_image = (ImageView) convertView.findViewById(R.id.left_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }



        if (this.msg.getType() == Msg.MSG_RECEIVE) {
            viewHolder.left_layout.setVisibility(convertView.VISIBLE);
            viewHolder.right_layout.setVisibility(convertView.GONE);
            viewHolder.left_msg.setText(this.msg.getContent());
            viewHolder.leftTime.setText(this.msg.getTime());
            viewHolder.left_image.setImageResource(this.msg.getImageId());
        }
        if (this.msg.getType() == Msg.MSG_SEND) {
            viewHolder.left_layout.setVisibility(convertView.GONE);
            viewHolder.right_layout.setVisibility(convertView.VISIBLE);
            viewHolder.right_msg.setText(this.msg.getContent());
            viewHolder.right_time.setText(this.msg.getTime());
            viewHolder.left_image.setImageResource(this.msg.getImageId());
        }
        return convertView;

    }

    private static class ViewHolder {
        LinearLayout left_layout;
        LinearLayout right_layout;
        TextView left_msg;
        TextView right_msg;
        TextView leftTime;
        TextView right_time;
        ImageView left_image;
    }
}

