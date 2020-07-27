package com.example.hms.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bookings {
    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("user")
    @Expose
    private Users user;

    @SerializedName("hotel")
    @Expose
    private Hotels hotel;

    @SerializedName("order_status")
    @Expose
    private String order_status;

    public Bookings(String id, Users user, Hotels hotel, String order_status) {
        this.id = id;
        this.user = user;
        this.hotel = hotel;
        this.order_status = order_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Hotels getHotel() {
        return hotel;
    }

    public void setHotel(Hotels hotel) {
        this.hotel = hotel;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }
}
