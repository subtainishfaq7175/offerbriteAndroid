package com.vb.offerbrite.data.models;


import com.google.gson.annotations.SerializedName;

public class SignUpModel {

    @SerializedName("username")
    private final String userName;

    @SerializedName("email")
    private final String email;

    @SerializedName("password")
    private final String password;

    @SerializedName("status")
    private final String status = "1";

    @SerializedName("nationality")
    private final String country;

    @SerializedName("userType")
    private final String userType = "1";


    public SignUpModel(String userName, String email, String password, String country) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.country = country;
    }
}
