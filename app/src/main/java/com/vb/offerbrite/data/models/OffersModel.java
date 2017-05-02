package com.vb.offerbrite.data.models;

import com.google.gson.annotations.SerializedName;

public class OffersModel {

    @SerializedName("categoryId")
    public String categoryId;

    @SerializedName("title")
    public String title;

    @SerializedName("description")
    public String description;

    @SerializedName("banner")
    public String banner;

    @SerializedName("startDate")
    public String startDate;

    @SerializedName("endDate")
    public String endDate;

    @SerializedName("starthours")
    public String starthours;

    @SerializedName("EndHours")
    public String EndHours;

    @SerializedName("additionalInformation")
    public String additionalInformation;

    @SerializedName("created_by")
    public String createdBy;

    @SerializedName("id")
    public int id;

    @SerializedName("totalLikes")
    public int totalLikes;

    @SerializedName("totalFollows")
    public int totalFollows;

    @SerializedName("isLiked")
    public boolean isLiked;

    @SerializedName("isfollowed")
    public boolean isfollowed;
}
