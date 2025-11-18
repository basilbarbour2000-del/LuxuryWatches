package com.example.luxurywatches;

import android.os.Parcel;
import android.os.Parcelable;

public class Watches {
    private String price;
    private String size;
    private  String color;
    private  String gender;
    private String brand;


    public Watches() {
    }

    public Watches(String price, String size, String color, String gender, String brand
                   ) {
        this.price = price;
        this.size = size;
        this.color = color;
        this.gender = gender;
        this.brand = brand;

    }

    protected Watches(Parcel in) {
        this.price = in.readString();
        this.size = in.readString();
        this.color = in.readString();
        this.gender = in.readString();
        this.brand = in.readString();

    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.price);
        dest.writeString(this.size);
        dest.writeString(this.color);
        dest.writeString(this.gender);
        dest.writeString(this.brand);

    }

    public String getprice() {
        return price;
    }

    public void setprice(String price) {
        this.price= price;
    }

    public String getsize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getcolor() {
        return color;
    }

    public void setcolor(String color) {
        this.color = color;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender= gender;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String phone) {
        this.brand = brand;
    }




    @Override
    public String toString() {
        return "watches{" +
                "price='" + price + '\'' +
                "size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", gender='" + gender + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }

    public int describeContents() {
        return 0;
    }

}
