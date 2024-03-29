package com.vb.offerbrite.webservices;


import com.vb.offerbrite.utils.ServerConstants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static OkHttpClient httpClient = new OkHttpClient().newBuilder().build();
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(ServerConstants.API_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }
}
