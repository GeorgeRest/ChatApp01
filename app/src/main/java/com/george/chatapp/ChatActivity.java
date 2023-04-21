package com.george.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.george.chatapp.beans.AddUserInfo;
import com.george.chatapp.beans.MessageList;

import com.george.chatapp.adapters.MessageAdapter;
import com.george.chatapp.beans.Msg;
import com.george.chatapp.beans.UserItem;
import com.george.chatapp.utils.TextChange;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_NAME = 2;
    private ListView listView;
    private EditText inputText;
    private Button send;
    private MessageAdapter messageAdapter;
    private List<MessageList> messageLists;
    private List<Msg> msgList = new ArrayList<Msg>();
    private Switch aSwitch;
    private TextView tv_userName;
    private String userName;
    private int imageId;
    private String newName;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        UserItem item = (UserItem) intent.getSerializableExtra("item");
        userName = item.getUserName();
        imageId = item.getImageId();
        newName();
        messageLists = LitePal.where("username=?", name).find(MessageList.class);
        initialize();
        listView = (ListView) findViewById(R.id.msg_list_view);
        messageAdapter = new MessageAdapter(ChatActivity.this, R.layout.msg_item, msgList);

        listView.setAdapter(messageAdapter);


        textChange();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String content = inputText.getText().toString();
                LitePal.getDatabase();
                Date date = new Date(System.currentTimeMillis());
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                String time = formatter.format(date);
                MessageList messageList = new MessageList();
                if (aSwitch.isChecked()) {
                    messageList.setType(Msg.MSG_RECEIVE);
                } else {
                    messageList.setType(Msg.MSG_SEND);
                }

                newName();
                messageList.setUserName(name);
                messageList.getContent().add(content);
                messageList.setTime(time);

                messageList.setImageId(imageId);
                messageList.save();
                AddUserInfo addUserInfo = new AddUserInfo();
                addUserInfo.setIsSend(AddUserInfo.send);
                addUserInfo.updateAll("name=?", name);

                data();
                messageAdapter.notifyDataSetChanged();
                listView.setSelection(msgList.size());
                inputText.setText("");


            }
        });
    }


    private void initialize() {
        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        aSwitch = (Switch) findViewById(R.id.swtich);
        tv_userName = (TextView) findViewById(R.id.tv_user_name);
    }

    private void textChange() {
        TextChange textChange = new TextChange(inputText, 1, send);
        inputText.addTextChangedListener(textChange);
        send.addTextChangedListener(new TextWatcher() {
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

    private void data() {
        msgList.clear();
        newName();
            messageLists = LitePal.where("username=?", name).find(MessageList.class);
        if (messageLists.size() > 0) {
            for (MessageList messageList : messageLists) {
                List<String> content1 = messageList.getContent();

                for (String content : content1) {
                    Msg msg = new Msg();
                    msg.setContent(content);
                    msg.setTime(messageList.getTime());
                    msg.setType(messageList.getType());
                    msg.setImageId(messageList.getImageId());
                    msgList.add(msg);

                }

            }
        }
    }


    public void back(View view) {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        newName();
        tv_userName.setText(name);

        data();

    }


    public void more(View view) {
        UserItem userItem = new UserItem();

        newName();
        userItem.setUserName(name);

        userItem.setImageId(imageId);
        Intent intent = new Intent(ChatActivity.this, UserInfoActivity.class);

        intent.putExtra("userName", userItem);
        startActivityForResult(intent, REQUEST_CODE_NAME);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case UserInfoActivity.RESULT_CODE_NAME:
                newName = data.getStringExtra("name");
        }
    }
    private void newName(){
        if(newName==null){
            name=userName;
        }else{
            name=newName;
        }
    }

}
