package com.vb.offerbrite.data.models;

import com.google.gson.annotations.SerializedName;

public class LoginModel {

    @SerializedName("email")
    public String email;

    @SerializedName("password")
    public String password;

    public LoginModel(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
