package com.example.twitxclone.model;

public class user {
    static final String N_KEY = "EMAILV";
    static final String DOB_Key = "DOBV";
    private String name;
    private String dob;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
