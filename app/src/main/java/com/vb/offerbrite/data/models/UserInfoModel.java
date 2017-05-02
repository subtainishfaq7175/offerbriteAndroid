package com.vb.offerbrite.data.models;

import com.google.gson.annotations.SerializedName;

public class UserInfoModel {

    @SerializedName("username")
    public String userName;

    @SerializedName("email")
    public String email;

    @SerializedName("gender")
    public String gender;

    @SerializedName("status")
    public int status;

    @SerializedName("image")
    public String image;

    @SerializedName("phone")
    public String phone;

    @SerializedName("userType")
    public int userType;

    @SerializedName("country")
    public String country;

    @SerializedName("nationality")
    public String nationality;

    @SerializedName("website")
    public String website;

    @SerializedName("city")
    public String city;

    @SerializedName("dob")
    public String dob;

    @SerializedName("Last_login")
    public String lastLogin;

    @SerializedName("id")
    public int id;

    @SerializedName("token")
    public String token;

    @SerializedName("profilePercentage")
    public int profilePercentage;
}
