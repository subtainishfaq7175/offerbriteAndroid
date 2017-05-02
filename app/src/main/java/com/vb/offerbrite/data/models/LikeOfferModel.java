package com.vb.offerbrite.data.models;

import com.google.gson.annotations.SerializedName;

public class LikeOfferModel {

    @SerializedName("user_id")
    public int userId;

    @SerializedName("is_liked")
    public int isLiked;

    @SerializedName("status")
    public String status = "1";

    @SerializedName("offer_id")
    public int offerId;

}
