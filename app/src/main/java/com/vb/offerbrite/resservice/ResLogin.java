package com.vb.offerbrite.resservice;

import com.google.gson.annotations.SerializedName;
import com.vb.offerbrite.data.models.UserInfoModel;

public class ResLogin {

    @SerializedName("response")
    public boolean response;

    @SerializedName("response_code")
    public String responseCode;

    @SerializedName("response_message")
    public String responseMessage;

    @SerializedName("response_data")
    public UserInfoModel data;

}
