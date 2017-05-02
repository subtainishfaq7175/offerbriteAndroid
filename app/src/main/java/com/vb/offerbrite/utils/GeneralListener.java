package com.vb.offerbrite.utils;


public class GeneralListener {

    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick();
    }

    public interface OnCallback {
        void onSuccess();

        void onError();
    }
}
