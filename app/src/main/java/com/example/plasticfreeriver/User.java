package com.example.plasticfreeriver;

public class User {
    String userId,name,profile;

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

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public User(String userId, String name, String profile) {
        this.userId = userId;
        this.name = name;
        this.profile = profile;

    }
}
