package com.vb.offerbrite.screens.login;

import android.os.Bundle;

import com.vb.offerbrite.baseclasses.BaseActivity;
import com.vb.offerbrite.screens.login.fragment.LoginFragment;
import com.vb.offerbrite.utils.AppUtils;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppUtils.isEmpty(prefs.getToken())) {
            init();
            setUpToolbar(false, false, false);
        } else {
            changeToHomeScreen();
            finish();
        }
    }

    private void init() {
        changeFragment(new LoginFragment(), true);
    }
}
