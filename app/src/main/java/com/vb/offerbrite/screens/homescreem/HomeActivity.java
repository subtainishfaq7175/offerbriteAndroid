package com.vb.offerbrite.screens.homescreem;

import android.os.Bundle;

import com.vb.offerbrite.baseclasses.BaseActivity;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        navigationFragment.setAdapter();
        getDrawerController().onMenuItemSelected(0, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationFragment.setUpUserData();
        navigationFragment.setAdapter();
    }
}
