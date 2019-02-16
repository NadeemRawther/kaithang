package com.example.greeshma.bloodbank;

public class ListSearch_Item {

    private String Name;
    private String PhoneNo;
    private String Place;


    public ListSearch_Item(String Name, String PhoneNo, String Place) {
        this.Name = Name;
        this.PhoneNo = PhoneNo;
        this.Place = Place;
    }

    public String getName() {
        return Name;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public String getPlace() {
        return Place;
    }
}