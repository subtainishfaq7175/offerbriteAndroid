package com.vb.offerbrite.webservices;

import com.vb.offerbrite.data.models.FollowOfferModel;
import com.vb.offerbrite.data.models.LikeOfferModel;
import com.vb.offerbrite.data.models.LoginModel;
import com.vb.offerbrite.data.models.SignUpModel;
import com.vb.offerbrite.resservice.ResGeneral;
import com.vb.offerbrite.resservice.ResLogin;
import com.vb.offerbrite.resservice.ResOfferDetail;
import com.vb.offerbrite.resservice.ResOffersList;
import com.vb.offerbrite.utils.ServerConstants;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiMethods {

    // user registration
    @Headers({ServerConstants.SECRET_KEY_HEADER, ServerConstants.API_KEY_HEADER})
    @POST(ServerConstants.LOGIN)
    Call<ResLogin> userLogin(@Body LoginModel info);

    @Headers({ServerConstants.SECRET_KEY_HEADER, ServerConstants.API_KEY_HEADER})
    @POST(ServerConstants.SIGN_UP)
    Call<Object> signUp(@Body SignUpModel info);


    // offers list and detail
    @GET(ServerConstants.OFFERS_LIST)
    Call<ResOffersList> getHomeOffers(@HeaderMap Map<String, String> headers);

    @GET(ServerConstants.OFFERS_DETAIL)
    Call<ResOfferDetail> getOfferDetail(@Path("offer_id") String offerId);


    // offers follow and like
    @POST(ServerConstants.FOLLOW_OFFER)
    Call<ResGeneral> followOffer(@Header("token") String token, @Body FollowOfferModel offer);

    @POST(ServerConstants.LIKE_OFFER)
    Call<ResGeneral> likeOffer(@Header("token") String token, @Body LikeOfferModel offer);
}