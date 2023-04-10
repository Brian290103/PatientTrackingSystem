package com.example.patienttrackingsystem;

public class NotificationModel {
    int id, user_id;
    boolean isRead;
    String reason, date;

    public NotificationModel(int id, int user_id, boolean isRead, String reason, String date) {
        this.id = id;
        this.user_id = user_id;
        this.isRead = isRead;
        this.reason = reason;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public boolean isRead() {
        return isRead;
    }

    public String getReason() {
        return reason;
    }

    public String getDate() {
        return date;
    }
}
