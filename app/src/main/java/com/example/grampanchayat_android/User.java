package com.example.grampanchayat_android;

public class User {

    String Status, Title;

    public User(){

    }

    public User(String status, String title) {
        Status = status;
        Title = title;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
