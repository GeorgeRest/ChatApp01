package com.george.chatapp.beans;

import org.litepal.crud.LitePalSupport;

public class AddUserInfo extends LitePalSupport {
    public static final int send=1;
    public static final int unSend=0;
    private String name;
    private String imageName;
    private int isSend;
    private String creationTime;
    private String gender;

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getIsSend() {
        return isSend;
    }

    public void setIsSend(int isSend) {
        this.isSend = isSend;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }


    //zhww
}
