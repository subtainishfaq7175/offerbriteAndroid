package com.vb.offerbrite.utils;

public class ServerConstants {

    //base url
    public static final String API_URL = "http://192.168.10.156:1337/";

    // urls
    public static final String LOGIN = "Login";
    public static final String OFFERS_LIST = "offers";
    public static final String OFFERS_DETAIL = "offers/{offer_id}";
    public static final String FOLLOW_OFFER = "follows";
    public static final String LIKE_OFFER = "likes";
    public static final String SIGN_UP = "users";


    // keys
    private static final String secretKey = "egdsdrtw54t4e5wytgscfgw45wygsdfgwre546y45645dfgsdfgst4w5645wsdfgw";
    private static final String apiKey = "sdfsdkfnmaliu7sdyoeyr347y85o75o74";

    // header for the post type services
    public static final String SECRET_KEY_HEADER = "secret_key:" + secretKey;
    public static final String API_KEY_HEADER = "api_key:" + apiKey;
    // response codes
    public static final String SUCCESS_CODE = "S000";
    public static final String LIKED_CODE = "S003";
    public static final String UN_LIKED_CODE = "S004";
    public static final String FOLLOWED_CODE = "S005";
    public static final String UN_FOLLOWED_CODE = "S006";
}
