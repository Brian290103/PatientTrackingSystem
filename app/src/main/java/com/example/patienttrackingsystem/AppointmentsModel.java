package com.example.patienttrackingsystem;

public class AppointmentsModel {
    private int id,user_id,doctor_id,speciality_id;
    private boolean isPaid,isApproved;
    private String username,message,app_date,app_time,date;

    public AppointmentsModel(int id, int user_id, int doctor_id, int speciality_id, boolean isPaid, boolean isApproved, String username, String message, String app_date, String app_time, String date) {
        this.id = id;
        this.user_id = user_id;
        this.doctor_id = doctor_id;
        this.speciality_id = speciality_id;
        this.isPaid = isPaid;
        this.isApproved = isApproved;
        this.username = username;
        this.message = message;
        this.app_date = app_date;
        this.app_time = app_time;
        this.date = date;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public int getSpeciality_id() {
        return speciality_id;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public String getDate() {
        return date;
    }




    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public String getApp_date() {
        return app_date;
    }

    public String getApp_time() {
        return app_time;
    }
}
