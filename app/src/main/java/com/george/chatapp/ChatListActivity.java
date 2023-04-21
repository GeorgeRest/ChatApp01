package com.george.chatapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.george.chatapp.beans.AddUserInfo;
import com.george.chatapp.beans.MessageList;
import com.george.chatapp.beans.UserItem;
import com.george.chatapp.adapters.ChatListAdapter;
import com.george.chatapp.utils.MyToast;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class ChatListActivity extends AppCompatActivity {
    private List<UserItem> users = new ArrayList<>();
    private UserItem userItem;
    private String lastContent;
    private String time;
    private ListView listView;
    private ChatListAdapter chatListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);

        chatListAdapter = new ChatListAdapter(this, R.layout.chat_list_item, users);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(chatListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                userItem = users.get(position);
                UserItem item = new UserItem();
                item.setUserName(userItem.getUserName());
                item.setImageId(userItem.getImageId());
                Intent intent = new Intent(ChatListActivity.this, ChatActivity.class);
                intent.putExtra("item",item);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ChatListActivity.this);
                UserItem userItem = users.get(position);
                String userName = userItem.getUserName();
                dialog.setTitle("删除用户");
                dialog.setMessage("确定删除用户"+userName+"吗?");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LitePal.deleteAll(AddUserInfo.class,"name=?",userName);
                        LitePal.deleteAll(MessageList.class,"username=?",userName);
                        data();

                        MyToast.show(ChatListActivity.this,"删除用户"+userName+"成功");

                    }
                });
                dialog.setNegativeButton("取消",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();

                return true;
            }
        });
    }

    private void data() {

        List<AddUserInfo> addUserInfos = LitePal.findAll(AddUserInfo.class);
        users.clear();
        for (AddUserInfo addUserInfo : addUserInfos) {

            String name = addUserInfo.getName();
            MessageList lastData = LitePal.where("username=?", name).findLast(MessageList.class);
            if (addUserInfo.getIsSend() == 1) {
                List<String> content = lastData.getContent();
                time = lastData.getTime();
                lastContent = content.get(content.size() - 1);
            } else {
                lastContent = "";
                time="";
            }
            UserItem userItem = new UserItem(addUserInfo.getName(), lastContent, getDrawableRes(ChatListActivity.this, addUserInfo.getImageName()),time);
            users.add(userItem);

        }
        chatListAdapter.notifyDataSetChanged();


    }


    public void more(View view) {
        ImageButton moreVertical = (ImageButton) findViewById(R.id.iv_more_vertical);
        PopupMenu popupMenu = new PopupMenu(ChatListActivity.this, moreVertical);
        popupMenu.getMenuInflater().inflate(R.menu.add_delete_item,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.add_item:
                        Intent intent = new Intent(ChatListActivity.this, AddActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.delete_item:
                        MyToast.show(ChatListActivity.this,"删除");
                        break;
                }
                return true;
            }
        });
        popupMenu.show();

    }

    private int getDrawableRes(Context context, String name) {
        String packageName = context.getPackageName();
        return context.getResources().getIdentifier(name, "mipmap", packageName);
    }

    @Override
    protected void onStart() {
        super.onStart();
        data();

    }
}