package com.george.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.george.chatapp.beans.AddUserInfo;
import com.george.chatapp.utils.MyToast;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    private String imageName = "";
    AddUserInfo addUserInfo = new AddUserInfo();
    private TextView addName;
    private String name;
    private List<AddUserInfo> addUserInfos;
    private int selectedIndex;
    private ImageView[] photoImageViews;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initialize();
        addUserInfos = LitePal.findAll(AddUserInfo.class);
        Button addButton = (Button) findViewById(R.id.add_name_image);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addName = (TextView) findViewById(R.id.add_name);
                name = addName.getText().toString().trim();
                LitePal.getDatabase();
                if (name.isEmpty() || imageName.equals("")) {
                    MyToast.show(AddActivity.this, "请输入名字并选择头像");
                    return;
                }
                for (AddUserInfo addUser : addUserInfos) {
                    if (name.equals(addUser.getName())) {
                        MyToast.show(AddActivity.this, "用户" + name + "已被添加");
                        return;
                    }
                }
                Date date = new Date(System.currentTimeMillis());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String time = formatter.format(date);
                addUserInfo.setGender("男");
                addUserInfo.setCreationTime(time);
                addUserInfo.setName(name);
                addUserInfo.setImageName(imageName);
                addUserInfo.setIsSend(AddUserInfo.unSend);
                addUserInfo.save();
                finish();
                MyToast.show(AddActivity.this, "用户" + name + "添加成功");
            }
        });
    }




    public void image(View view) {
        switch (view.getId()) {

            case R.id.photo1:
                imageName = "photo1";
                selectedIndex = 0;
                photoChange(selectedIndex);


                break;
            case R.id.photo2:
                imageName = "photo2";
                selectedIndex = 1;
                photoChange(selectedIndex);


                break;
            case R.id.photo3:
                imageName = "photo3";
                selectedIndex = 2;
                photoChange(selectedIndex);
                break;
            case R.id.photo4:
                imageName = "photo4";
                selectedIndex = 3;
                photoChange(selectedIndex);
                break;
            case R.id.photo5:
                imageName = "photo5";
                selectedIndex = 4;
                photoChange(selectedIndex);
                break;
            case R.id.photo6:
                imageName = "photo6";
                selectedIndex = 5;
                photoChange(selectedIndex);
                break;
            case R.id.photo7:
                imageName = "photo7";
                selectedIndex = 6;
                photoChange(selectedIndex);
                break;
            case R.id.photo8:
                imageName = "photo8";
                selectedIndex = 7;
                photoChange(selectedIndex);
                break;
            case R.id.photo9:
                imageName = "photo9";
                selectedIndex = 8;
                photoChange(selectedIndex);
                break;
            case R.id.photo10:
                imageName = "photo10";
                selectedIndex = 9;
                photoChange(selectedIndex);
                break;
            case R.id.photo11:
                imageName = "photo11";
                selectedIndex = 10;
                photoChange(selectedIndex);
                break;
            case R.id.photo12:
                imageName = "photo12";
                selectedIndex = 11;
                photoChange(selectedIndex);
                break;
            case R.id.photo13:
                imageName = "photo13";
                selectedIndex = 12;
                photoChange(selectedIndex);
                break;
            case R.id.photo14:
                imageName = "photo14";
                selectedIndex = 13;
                photoChange(selectedIndex);
                break;
            case R.id.photo15:
                imageName = "photo15";
                selectedIndex = 14;
                photoChange(selectedIndex);
                break;
            case R.id.photo16:
                imageName = "photo16";
                selectedIndex = 15;
                photoChange(selectedIndex);
                break;
        }
    }


    private void photoChange(int selectedIndex) {
        photoImageViews[selectedIndex].setSelected(true);
        for (int i = 0; i <photoImageViews.length; i++) {
            if (i != selectedIndex) {
                photoImageViews[i].setSelected(false);
            }
        }
    }

    private void initialize() {
        photoImageViews = new ImageView[16];
        photoImageViews[0] = (ImageView) findViewById(R.id.photo1);
        photoImageViews[1] = (ImageView) findViewById(R.id.photo2);
        photoImageViews[2] = (ImageView) findViewById(R.id.photo3);
        photoImageViews[3] = (ImageView) findViewById(R.id.photo4);
        photoImageViews[4] = (ImageView) findViewById(R.id.photo5);
        photoImageViews[5] = (ImageView) findViewById(R.id.photo6);
        photoImageViews[6] = (ImageView) findViewById(R.id.photo7);
        photoImageViews[7] = (ImageView) findViewById(R.id.photo8);
        photoImageViews[8] = (ImageView) findViewById(R.id.photo9);
        photoImageViews[9] = (ImageView) findViewById(R.id.photo10);
        photoImageViews[10] = (ImageView) findViewById(R.id.photo11);
        photoImageViews[11] = (ImageView) findViewById(R.id.photo12);
        photoImageViews[12] = (ImageView) findViewById(R.id.photo13);
        photoImageViews[13] = (ImageView) findViewById(R.id.photo14);
        photoImageViews[14] = (ImageView) findViewById(R.id.photo15);
        photoImageViews[15] = (ImageView) findViewById(R.id.photo16);
    }

    public void back(View view) {
        finish();
    }
}
