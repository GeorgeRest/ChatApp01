package com.george.chatapp.beans;

public class Msg {
    public static final int MSG_RECEIVE = 0;
    public static final int MSG_SEND = 1;
    private int type=1;
    private String content;
    private String time;
    private int imageId;




    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

