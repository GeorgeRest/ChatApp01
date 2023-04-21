package com.george.chatapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.george.chatapp.beans.AddUserInfo;
import com.george.chatapp.beans.MessageList;
import com.george.chatapp.beans.UserItem;
import com.george.chatapp.utils.MyToast;

import org.litepal.LitePal;

import java.util.List;

public class UserInfoActivity extends AppCompatActivity {

    public static final int RESULT_CODE_NAME = 3;
    private final int REQUEST_CODE_NAME = 1;
    private TextView tv_name;
    private TextView tv_gender;
    private TextView tv_time;
    private String userName;
    private ImageView photo;
    private List<AddUserInfo> addUserInfos;
    private String name;
    private String newName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initialize();
        Intent intent = getIntent();
        UserItem userItem = (UserItem) intent.getSerializableExtra("userName");
        userName = userItem.getUserName();
        int imageId = userItem.getImageId();
        photo.setImageResource(imageId);

        addUserInfos = LitePal.where("name=?", userName).find(AddUserInfo.class);
        setInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setInfo();

    }


    private void setInfo() {

        for (AddUserInfo userInfo : addUserInfos) {
            newName = userInfo.getName();
            tv_name.setText(newName);
            tv_gender.setText(userInfo.getGender());
            tv_time.setText(userInfo.getCreationTime());

            if ("男".equals(userInfo.getGender())) {
                tv_name.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.mipmap.man, 0);
            } else {
                tv_name.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.mipmap.woman, 0);
            }
        }
    }

    public void alter(View view) {
        Intent intent = new Intent(UserInfoActivity.this, ModificationActivity.class);
        UserItem userItem = new UserItem();
        userItem.setUserName(newName);
        intent.putExtra("userName", userItem);
        startActivityForResult(intent, REQUEST_CODE_NAME);
    }

    public void clearChat(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(UserInfoActivity.this);
        dialog.setTitle("清空聊天记录");
        dialog.setMessage("确定清空聊天记录吗?");
        dialog.setCancelable(false);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                LitePal.deleteAll(MessageList.class,"username=?",newName);
                ContentValues values = new ContentValues();
                values.put("issend", AddUserInfo.unSend);
                LitePal.updateAll(AddUserInfo.class, values, "name=?", newName);

                MyToast.show(UserInfoActivity.this,"清空聊天记录成功");

            }
        });
        dialog.setNegativeButton("取消",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();


    }

    private void initialize() {
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_gender = (TextView) findViewById(R.id.tv_gender);
        tv_time = (TextView) findViewById(R.id.tv_time);
        photo = (ImageView) findViewById(R.id.iv_photo);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case ModificationActivity.RESULT_CODE_NAME:
                name = data.getStringExtra("name");
                addUserInfos = LitePal.where("name=?", name).find(AddUserInfo.class);
        }
    }

    public void back(View view) {

        Intent intent = new Intent();
        intent.putExtra("name", newName);
        setResult(RESULT_CODE_NAME,intent);
        finish();
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra("name", newName);
        setResult(RESULT_CODE_NAME,intent);
        super.onBackPressed();
    }


    public void sendMessage(View view) {
        UserItem userItem = new UserItem();
        userItem.setUserName(newName);
        Intent intent = new Intent(UserInfoActivity.this, ChatActivity.class);
        intent.putExtra("item",userItem);
        startActivity(intent);
    }
}