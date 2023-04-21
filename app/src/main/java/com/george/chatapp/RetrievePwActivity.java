package com.george.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.george.chatapp.beans.UserInfo;
import com.george.chatapp.utils.MyToast;

import org.litepal.LitePal;

import java.util.List;

public class RetrievePwActivity extends AppCompatActivity {

    private EditText retrieveId;
    private Button retrieveConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrieve_pw_layout);
        initialize();
        retrievePw();

        retrieveId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()>=1){
                    retrieveConfirm.setBackgroundResource(R.drawable.retrieve_button);
                    retrieveConfirm.setEnabled(true);

                }else {
                    retrieveConfirm.setBackgroundResource(R.drawable.button_gray);
                    retrieveConfirm.setEnabled(false);
                }
            }
        });
    }

    private void initialize() {
        retrieveId = (EditText) findViewById(R.id.et_retrieve_id);
        retrieveConfirm = (Button) findViewById(R.id.bt_retrieve_confirm);
    }

    private void retrievePw(){
        retrieveConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = retrieveId.getText().toString().trim();
                List<UserInfo> userInfoList = LitePal.findAll(UserInfo.class);
                boolean found=false;
                for (UserInfo userInfo:userInfoList) {
                    if(id.equals(userInfo.getUserName())||id.equals(userInfo.getEmail())){
                        found = true;
                        MyToast.show(RetrievePwActivity.this,userInfo.getPw());
                        new Intent(RetrievePwActivity.this,MainActivity.class);
                        return;
                    }
                }
                if(!found){
                        MyToast.show(RetrievePwActivity.this,"未查询到该账号或邮箱");
                    }
                }
        });
    }

    public void back(View view) {
        finish();
    }
}