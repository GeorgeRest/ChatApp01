package com.george.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.george.chatapp.beans.AddUserInfo;
import com.george.chatapp.beans.MessageList;
import com.george.chatapp.beans.UserItem;
import com.george.chatapp.utils.MyToast;


public class ModificationActivity extends AppCompatActivity {

    public static final int RESULT_CODE_NAME = 2;
    private EditText et_alter_name;
    private RadioGroup rg_gender;
    private Button finish;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification);
        initialize();
        Intent intent = getIntent();
        UserItem userItem = (UserItem) intent.getSerializableExtra("userName");
        userName = userItem.getUserName();
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newName = et_alter_name.getText().toString();
                int genderId = rg_gender.getCheckedRadioButtonId();
                if(newName.isEmpty()||genderId==-1){
                    MyToast.show(ModificationActivity.this,"请输入备注并选择性别");
                    return;
                }
                RadioButton rb_gender = (RadioButton) findViewById(genderId);
                String gender = rb_gender.getText().toString();

                AddUserInfo addUserInfo = new AddUserInfo();
                addUserInfo.setName(newName);
                addUserInfo.setGender(gender);
                addUserInfo.updateAll("name=?",userName);

                MessageList messageList = new MessageList();
                messageList.setUserName(newName);
                messageList.updateAll("username=?",userName);

                Intent intent1 = new Intent();
                intent1.putExtra("name",newName);
                setResult(RESULT_CODE_NAME,intent1);
                finish();
                MyToast.show(ModificationActivity.this,"修改成功");

            }
        });
    }

    private void initialize() {
        et_alter_name = (EditText) findViewById(R.id.et_alter_name);
        rg_gender = (RadioGroup) findViewById(R.id.rg_gender);
        finish = (Button) findViewById(R.id.bt_finish);
    }



    public void cancel(View view) {
        finish();
    }


}