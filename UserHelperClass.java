package com.example.nfc_app;

public class UserHelperClass {
    String UID, Name;

    public UserHelperClass() {
    }

    public UserHelperClass(String UID, String Name) {
        this.UID = UID;
        this.Name = Name;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
}


