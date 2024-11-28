package com.example.chattingavapp;

public class Users {
    String profilepic, male, userName, password, userId, lastMessage, status;

    // Corrected constructor
    public Users(String userId, String userName, String email, String password, String profilepic, String status) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.profilepic = profilepic; // Profile picture as a URL string
        this.status = status;
        this.lastMessage = ""; // Initialize with default value
        this.male = ""; // Optional field, initialize with default if not provided
    }

    // Default constructor (needed for Firebase Realtime Database)
    public Users() {}

    // Getter and setter methods
    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getMale() {
        return male;
    }

    public void setMale(String male) {
        this.male = male;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
