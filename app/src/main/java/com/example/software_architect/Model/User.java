package com.example.software_architect.Model;

public class User {
    private String id,username, imageURL;

    public User(String mId, String mUsername, String mImageURL) {
        id = mId;
        username = mUsername;
        imageURL = mImageURL;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String mId) {
        id = mId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String mUsername) {
        username = mUsername;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String mImageURL) {
        imageURL = mImageURL;
    }
}
