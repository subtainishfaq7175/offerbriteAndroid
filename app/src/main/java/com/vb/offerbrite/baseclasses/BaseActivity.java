package com.vb.offerbrite.baseclasses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;

import com.vb.offerbrite.R;
import com.vb.offerbrite.application.MyApplication;
import com.vb.offerbrite.data.SharedPrefs;
import com.vb.offerbrite.data.models.OffersModel;
import com.vb.offerbrite.data.models.UserInfoModel;
import com.vb.offerbrite.screens.homescreem.HomeActivity;
import com.vb.offerbrite.screens.login.LoginActivity;
import com.vb.offerbrite.screens.navigationdrawer.NavigationDrawerFragment;
import com.vb.offerbrite.screens.offersdetail.OfferDetailActivity;
import com.vb.offerbrite.utils.Constants;

public abstract class BaseActivity extends AppCompatActivity {

    protected NavigationDrawerFragment navigationFragment;
    private FrameLayout mainFrame;
    private ActionBar actionbar;
    private Toolbar toolbar;
    protected SharedPrefs prefs;
    private MyApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }

    private void init() {
        mainFrame = (FrameLayout) findViewById(R.id.fl_main);
        navigationFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.nav_view);
        navigationFragment.setUpNavigationDrawer(this, toolbar, R.id.drawer_layout);
        actionbar = getSupportActionBar();
        application = (MyApplication) getApplication();
        prefs = application.getPrefs();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = navigationFragment.getDrawer();
        int count = getSupportFragmentManager().getBackStackEntryCount();
        try {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else if (count < 2) {
                finish();
            } else {
                getSupportFragmentManager().popBackStack();
            }
        } catch (Exception e) {
            finish();
        }
    }

    public void changeFragment(final Fragment fragment, boolean addToBackStack) {
        try {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(mainFrame.getId(), fragment);
            if (addToBackStack)
                fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setUpToolbar(boolean showNavigation, boolean showBackButton, boolean showActionBar) {
        if (showNavigation) {
            navigationFragment.showNavigation();
        } else {
            navigationFragment.hideNavigation();
            if (actionbar != null) {
                if (showBackButton) {
                    actionbar.setDisplayHomeAsUpEnabled(true);
                    actionbar.setDisplayShowHomeEnabled(true);
                    setBackButtonListener();
                } else {
                    actionbar.setHomeButtonEnabled(false);
                    actionbar.setDisplayHomeAsUpEnabled(false);
                    actionbar.setDisplayShowHomeEnabled(false);
                }
            }
        }
        if (actionbar != null)
            if (showActionBar) {
                actionbar.show();
            } else
                actionbar.hide();
    }

    private void setBackButtonListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void changeToHomeScreen() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void changeToDetail(OffersModel offerItem) {
        Intent intent = new Intent(this, OfferDetailActivity.class);
        intent.putExtra(Constants.SCREEN_TITLE, offerItem.title);
        intent.putExtra(Constants.OFFER_ID, offerItem.id);
        startActivity(intent);
    }

    public NavigationDrawerFragment getDrawerController() {
        return navigationFragment;
    }

    public void clearUserData() {
        prefs.setUserName("");
        prefs.setUserEmail("");
        prefs.setStatus(-1);
        prefs.setImage("");
        prefs.setUserType(-1);
        prefs.setUserId(1);
        prefs.setToken("");
        prefs.setProfilePercentage(0);
        navigationFragment.updateMenus();
    }

    public void updateUserInfo(UserInfoModel data) {
        prefs.setUserName(data.userName);
        prefs.setUserEmail(data.email);
        prefs.setStatus(data.status);
        prefs.setImage(data.image);
        prefs.setUserType(data.userType);
        prefs.setUserId(data.id);
        prefs.setToken(data.token);
        prefs.setProfilePercentage(data.profilePercentage);
    }

    public void changeToLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void moveToHomeFragment() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        navigationFragment.onMenuItemSelected(0, true);
    }
}
