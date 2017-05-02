package com.vb.offerbrite.data.models;

import com.google.gson.annotations.SerializedName;

public class FollowOfferModel {

    @SerializedName("user_id")
    public int userId;

    @SerializedName("is_followed")
    public int isFollowed;

    @SerializedName("status")
    public String status = "1";

    @SerializedName("offer_id")
    public int offerId;

}
