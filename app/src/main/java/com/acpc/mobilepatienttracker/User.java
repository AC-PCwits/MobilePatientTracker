package com.acpc.mobilepatienttracker;

//this class allows us to create user objects that will be stored and be visible in the realtime database

public class User {
    public String fname;
    public String email;
    public String id;
    //password is not included because it is private and should not be visible to us

    public User(){

    }
    public User(String fname, String email, String id){
        this.fname=fname;
        this.email=email;
        this.id = id;
    }

}