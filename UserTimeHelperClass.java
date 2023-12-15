package com.example.nfc_app;

public class UserTimeHelperClass {
    String UID, Name, Time;

    public UserTimeHelperClass() {
    }

    public UserTimeHelperClass(String Time, String Name, String UID) {
        this.Time = Time;
        this.Name = Name;
        this.UID = UID;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
}
