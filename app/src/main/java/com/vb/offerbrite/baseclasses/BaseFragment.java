package com.vb.offerbrite.baseclasses;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.vb.offerbrite.application.MyApplication;
import com.vb.offerbrite.data.SharedPrefs;
import com.vb.offerbrite.utils.AppUtils;
import com.vb.offerbrite.utils.Constants;

public abstract class BaseFragment extends Fragment {

    protected BaseActivity baseActivity;
    protected String title = "";
    protected MyApplication application;
    private int position = -1;
    protected SharedPrefs prefs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseActivity = (BaseActivity) getActivity();
        application = ((MyApplication) baseActivity.getApplication());
        prefs = application.getPrefs();
        getExtras();
    }

    private void getExtras() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            title = bundle.getString(Constants.SCREEN_TITLE, "");
            position = bundle.getInt(Constants.SCREEN_POSITION, -1);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!AppUtils.isEmpty(title))
            baseActivity.setTitle(title);
        if (position > -1)
            baseActivity.getDrawerController().onMenuItemSelected(position, false);
    }

}
