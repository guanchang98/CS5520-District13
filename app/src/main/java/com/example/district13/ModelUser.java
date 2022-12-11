package com.example.district13;

public class ModelUser {

    //WARNING: DO NOT change these name
    String name, email, avatar, uid;

    public ModelUser() {
    }

    public ModelUser(String name, String email, String avatar, String uid) {
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "ModelUser{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}
