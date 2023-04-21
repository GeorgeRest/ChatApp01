package com.george.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.george.chatapp.beans.UserInfo;
import com.george.chatapp.utils.MyToast;
import org.litepal.LitePal;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText loginName;
    private EditText loginPw;
    private CheckBox rememberMe;
    private TextView forgetPw;
    private Button login;
    private Button register;
    private SharedPreferences userState;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userState = getSharedPreferences("userState", MODE_PRIVATE);
        initialize();
        loginJudge();
        showUserInfo();
        System.out.println("zxp2");

        //跳转注册
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        //跳转找回密码
        forgetPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RetrievePwActivity.class);
                startActivity(intent);
            }
        });


    }


    private void initialize() {
        loginName = (EditText) findViewById(R.id.et_login_name);
        loginPw = (EditText) findViewById(R.id.et_login_pw);
        rememberMe = (CheckBox) findViewById(R.id.cb_remember_me);
        forgetPw = (TextView) findViewById(R.id.tv_forget_pw);
        login = (Button) findViewById(R.id.bt_login);
        register = (Button) findViewById(R.id.bt_register);

    }

    private void loginJudge() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginUserId = loginName.getText().toString().trim();
                String login_Pw = loginPw.getText().toString().trim();
                if (loginUserId.isEmpty() || login_Pw.isEmpty()) {
                    MyToast.show(MainActivity.this, "用户名或密码不能为空");
                    return;
                }
                List<UserInfo> userInfoList = LitePal.findAll(UserInfo.class);
                boolean found = false;
                for (UserInfo userInfo : userInfoList) {
                    if (loginUserId.equals(userInfo.getUserName())&&!login_Pw.equals(userInfo.getPw())) {
                        MyToast.show(MainActivity.this, "密码错误");
                        return;
                    } else if (loginUserId.equals(userInfo.getUserName()) && login_Pw.equals(userInfo.getPw())) {
                        found = true;
                        boolean checked = rememberMe.isChecked();
                        editor = userState.edit();
                        if(checked){
                            editor.putString("userId",loginUserId);
                            editor.putString("pw",login_Pw);
                            editor.putBoolean("rememberMe",checked);
                        }else{
                            editor.clear();
                        }
                        editor.commit();
                        Intent intent = new Intent(MainActivity.this, ChatListActivity.class);
                        startActivity(intent);
                        MyToast.show(MainActivity.this, "欢迎用户"+loginUserId);
                        return;
                    }
                }
                if (!found) {
                    MyToast.show(MainActivity.this, "该账号未注册");
                }
            }
        });
    }
    private void showUserInfo() {
            String userId = userState.getString("userId", "");
            String pw = userState.getString("pw", "");
            boolean rememberMe1 = userState.getBoolean("rememberMe", false);
            loginName.setText(userId);
            loginPw.setText(pw);
            rememberMe.setChecked(rememberMe1);
    }


    public void contactMe(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:4006700700"));
        startActivity(intent);
    }


}