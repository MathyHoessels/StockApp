package com.hoessels.stockapp.domain;

import android.location.Address;

import java.util.Locale;

public class Supplier {
    private Address address = null; // this should hold the rest of contact information in the future
    private String email = "";
    private String name = "";
    private int nr;
    private String phone = "";

    public Supplier() {
        this.address = new Address(new Locale("nl_BE"));
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
