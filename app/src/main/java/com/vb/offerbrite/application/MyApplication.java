package com.vb.offerbrite.application;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.vb.offerbrite.data.SharedPrefs;
import com.vb.offerbrite.webservices.ApiMethods;
import com.vb.offerbrite.webservices.ServiceGenerator;

public class MyApplication extends Application {

    private SharedPrefs prefs;

    public ApiMethods getClient() {
        return client;
    }

    private ApiMethods client;
    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        handler = new Handler(Looper.getMainLooper());
        setupSharedPrefs();
        setClient();
    }

    private void setupSharedPrefs() {
        prefs = new SharedPrefs(this);
    }

    private void setClient() {
        client = ServiceGenerator.createService(ApiMethods.class);
    }

    public SharedPrefs getPrefs() {
        return prefs;
    }

    public Handler getHandler() {
        return handler;
    }
}
