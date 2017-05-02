package com.vb.offerbrite.resservice;

import com.google.gson.annotations.SerializedName;

public class ResGeneral {

    @SerializedName("response")
    public boolean response;

    @SerializedName("response_code")
    public String responseCode;

    @SerializedName("response_message")
    public String responseMessage;

}
