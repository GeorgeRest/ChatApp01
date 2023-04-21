package com.george.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.george.chatapp.beans.UserInfo;
import com.george.chatapp.utils.MyToast;
import com.george.chatapp.utils.TextChange;

import org.litepal.LitePal;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText registerUser;
    private EditText registerEmail;
    private EditText registerPw;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        initialize();
        textChange();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = registerUser.getText().toString().trim();
                String email = registerEmail.getText().toString().trim();
                String pw = registerPw.getText().toString().trim();
                //建立数据库
                LitePal.getDatabase();
                if (!isEmailValid(email)) {
                    MyToast.show(RegisterActivity.this, "请输入正确的邮箱格式");
                    return;
                }
                //遍历数据库中用户信息查询是否重复注册
                List<UserInfo> userInfoList = LitePal.findAll(UserInfo.class);
                for (UserInfo userInfo : userInfoList) {
                    if (userId.equals(userInfo.getUserName())) {
                        MyToast.show(RegisterActivity.this, "该用户已注册，请登录");
                        return;
                    }
                }
                //将用户数据存入数据库中
                UserInfo userInfo = new UserInfo();
                userInfo.setUserName(userId);
                userInfo.setPw(pw);
                userInfo.setEmail(email);
                userInfo.save();
                finish();
                MyToast.show(RegisterActivity.this, "注册成功");
            }
        });

    }

    private void initialize() {
        registerUser = (EditText) findViewById(R.id.et_register_user);
        registerEmail = (EditText) findViewById(R.id.et_register_email);
        registerPw = (EditText) findViewById(R.id.et_register_pw);
        register = (Button) findViewById(R.id.bt_register_layout_register);
    }

    //简易邮箱格式验证
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,5}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void textChange() {
        TextChange textChange = new TextChange(registerUser, registerPw, registerEmail,3, register);
        registerUser.addTextChangedListener(textChange);
        registerPw.addTextChangedListener(textChange);
        registerEmail.addTextChangedListener(textChange);
        register.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

}