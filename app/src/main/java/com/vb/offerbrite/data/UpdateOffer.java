package com.vb.offerbrite.data;

import com.vb.offerbrite.data.models.OffersModel;

public class UpdateOffer {

    public static OffersModel getOffer() {
        return offer;
    }

    public static void setOffer(OffersModel offer) {
        UpdateOffer.offer = offer;
    }

    private static OffersModel offer = null;
}
