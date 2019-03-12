package com.example.software_architect.Model;

public class User {
    private String id,username, imageURL,status,search;

    public User(String mId, String mUsername, String mImageURL) {
        id = mId;
        username = mUsername;
        imageURL = mImageURL;
    }

    public User(String mId, String mUsername, String mImageURL, String mStatus) {
        id = mId;
        username = mUsername;
        imageURL = mImageURL;
        status = mStatus;
    }

    public User(String mId, String mUsername, String mImageURL, String mStatus, String mSearch) {
        id = mId;
        username = mUsername;
        imageURL = mImageURL;
        status = mStatus;
        search = mSearch;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String mStatus) {
        status = mStatus;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String mSearch) {
        search = mSearch;
    }
}
