package com.example.hms.models;

public class Hotels {

    String _id;
    private String hotel_name;
    private String description;
    private String price;
    private String hotel_image;

    public Hotels(String hotel_name, String description, String price, String hotel_image) {
        this.hotel_name = hotel_name;
        this.description = description;
        this.price = price;
        this.hotel_image = hotel_image;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getHotel_image() {
        return hotel_image;
    }

    public void setHotel_image(String hotel_image) {
        this.hotel_image = hotel_image;
    }
}
