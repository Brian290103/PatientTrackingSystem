package com.example.patienttrackingsystem;

public class DoctorModel {

    private int user_id, id_no, phone, speciality_id, isPresent;
    private String fname, lname, gender, email, address, available_times, date;

    public DoctorModel(int user_id, int id_no, int phone, int speciality_id, int isPresent, String fname, String lname, String gender, String email, String address, String available_times, String date) {
        this.user_id = user_id;
        this.id_no = id_no;
        this.phone = phone;
        this.speciality_id = speciality_id;
        this.isPresent = isPresent;
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.available_times = available_times;
        this.date = date;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getId_no() {
        return id_no;
    }

    public int getPhone() {
        return phone;
    }

    public int getSpeciality_id() {
        return speciality_id;
    }

    public int getIsPresent() {
        return isPresent;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getAvailable_times() {
        return available_times;
    }

    public String getDate() {
        return date;
    }
}
