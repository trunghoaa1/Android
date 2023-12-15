package com.example.nfc_app;

public class User {
    private String uid;
    private String name;

    public User(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public User(){}

    public String getId(){
        return uid;
    }

    public void setId(String uid)
    {
        this.uid = uid;
    }

    public String getName(){
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
