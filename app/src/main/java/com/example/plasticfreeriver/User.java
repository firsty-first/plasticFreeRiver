package com.example.plasticfreeriver;

public class User {
    String userId,name, profileImg;
    User()
    {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public User(String userId, String name, String profile) {
        this.userId = userId;
        this.name = name;
        this.profileImg = profile;

    }
}
