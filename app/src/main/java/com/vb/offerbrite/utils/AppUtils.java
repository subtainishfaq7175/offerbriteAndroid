package com.vb.offerbrite.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class AppUtils {

    private static Toast toast;

    public static boolean isEmpty(String inputStr) {
        if (inputStr == null) {
            return true;
        } else if (inputStr.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            return info != null && info.isConnectedOrConnecting();
        } else {
            return false;
        }
    }

    public static void makeText(Context context, String message, int duration) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    public static void makeText(Context context, String message) {
        makeText(context, message, Toast.LENGTH_SHORT);
    }

    public static void loadImage(final Context context, String imgSrc, final int defaultImg, final ImageView view, final GeneralListener.OnCallback listener) {
        if (!isEmpty(imgSrc)) {
            Picasso.with(context).load(imgSrc).placeholder(defaultImg).error(defaultImg).into(view, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    if (listener != null) {
                        listener.onError();
                    }
                }
            });
        } else {
            view.setImageResource(defaultImg);
        }
    }

    public static int dpToPx(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    public static void hideKeyBoard(Context context, View view) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
