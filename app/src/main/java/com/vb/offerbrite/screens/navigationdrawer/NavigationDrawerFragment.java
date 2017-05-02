package com.vb.offerbrite.screens.navigationdrawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vb.offerbrite.R;
import com.vb.offerbrite.baseclasses.BaseActivity;
import com.vb.offerbrite.baseclasses.BaseFragment;
import com.vb.offerbrite.data.models.DrawerMenuModel;
import com.vb.offerbrite.screens.aboutscreen.AboutFragment;
import com.vb.offerbrite.screens.homescreem.fragment.HomeFragment;
import com.vb.offerbrite.screens.navigationdrawer.adapters.DrawerMenuAdapter;
import com.vb.offerbrite.utils.AppUtils;
import com.vb.offerbrite.utils.Constants;
import com.vb.offerbrite.utils.GeneralListener;

import java.util.ArrayList;

public class NavigationDrawerFragment extends BaseFragment {

    private View rootView;
    private ImageView ivUser;
    private TextView tvUserName;
    private TextView tvEmail;
    private LinearLayout llProfilePercentage;
    private ProgressBar pbProfile;
    private TextView tvProfileProgress;
    private RecyclerView rvMenu;
    private DrawerMenuAdapter adapter;
    private ArrayList<DrawerMenuModel> menus = new ArrayList<>();
    private int prePosition = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.nav_header_base, container, false);
        }
        return rootView;
    }

    private Toolbar toolbar;
    private BaseActivity baseActivity;

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    public void setUpNavigationDrawer(BaseActivity baseActivity, Toolbar toolbar, int drawerLayoutId) {
        this.baseActivity = baseActivity;
        this.toolbar = toolbar;
        initDrawer(drawerLayoutId);
    }

    @SuppressWarnings("deprecation")
    private void initDrawer(int drawer_layout) {
        drawer = (DrawerLayout) baseActivity.findViewById(drawer_layout);
        toggle = new ActionBarDrawerToggle(
                baseActivity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        ivUser = (ImageView) rootView.findViewById(R.id.iv_user);
        tvUserName = (TextView) rootView.findViewById(R.id.et_user_name);
        tvEmail = (TextView) rootView.findViewById(R.id.tv_email);
        llProfilePercentage = (LinearLayout) rootView.findViewById(R.id.ll_profile_progress);
        pbProfile = (ProgressBar) rootView.findViewById(R.id.pb_profile);
        tvProfileProgress = (TextView) rootView.findViewById(R.id.tv_profile_progress);
        rvMenu = (RecyclerView) rootView.findViewById(R.id.rv_menu);
        rvMenu.setLayoutManager(new LinearLayoutManager(baseActivity));
        rvMenu.setAdapter(new DrawerMenuAdapter(baseActivity, new ArrayList<DrawerMenuModel>(), null));
        setUpUserData();
    }

    public void setUpUserData() {
        if (!AppUtils.isEmpty(prefs.getToken())) {
            AppUtils.loadImage(baseActivity, prefs.getUserImage(), R.drawable.ic_launcher, ivUser, null);
            ivUser.setVisibility(View.VISIBLE);
            tvUserName.setText(prefs.getUserName());
            tvEmail.setText(prefs.getUserEmail());
            tvEmail.setVisibility(View.VISIBLE);
            llProfilePercentage.setVisibility(View.VISIBLE);
            pbProfile.setProgress(prefs.getProfilePercentage());
            String percentage = String.valueOf(prefs.getProfilePercentage()) + Constants.PERCENTAGE_SIGN;
            tvProfileProgress.setText(percentage);
        } else {
            llProfilePercentage.setVisibility(View.GONE);
            tvEmail.setVisibility(View.GONE);
            tvUserName.setText(getString(R.string.app_name));
        }
    }

    public void setAdapter() {
        menus = new ArrayList<>();

        menus.add(new DrawerMenuModel(Constants.HOME_TEXT, Constants.HOME_ID, false, true));
        boolean isUserLogin = !AppUtils.isEmpty(prefs.getToken());
        if (isUserLogin) {
            menus.add(new DrawerMenuModel(Constants.LIKED_TEXT, Constants.LIKED_ID, false, true));
            menus.add(new DrawerMenuModel(Constants.FOLLOWED_TEXT, Constants.FOLLOWED_ID, false, true));
        }
        menus.add(new DrawerMenuModel(Constants.ABOUT_TEXT, Constants.FOLLOWED_ID, true, false));
        menus.add(new DrawerMenuModel(Constants.PRIVACY_POLICY_TEXT, Constants.PRIVACY_POLICY_ID, false, true));
        menus.add(new DrawerMenuModel(Constants.TERMS_CONDITION_TEXT, Constants.TERMS_CONDITION_ID, false, true));
        menus.add(new DrawerMenuModel(Constants.APP_VERSION_TEXT, Constants.APP_VERSION_ID, false, true));

        // login and logout
        if (isUserLogin) {
            menus.add(new DrawerMenuModel(Constants.LOGOUT_TEXT, Constants.LOGOUT_ID, false, true));
        } else {
            menus.add(new DrawerMenuModel(Constants.LOGIN_TEXT, Constants.LOGIN_ID, false, true));
        }

        adapter = new DrawerMenuAdapter(baseActivity, menus, new GeneralListener.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                if (menus.get(position).id == Constants.LOGIN_ID) {
                    application.getHandler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            baseActivity.changeToLoginScreen();
                        }
                    }, 500);
                    drawer.closeDrawer(GravityCompat.START);
                } else if (menus.get(position).id == Constants.LOGOUT_ID) {
                    baseActivity.clearUserData();
                    AppUtils.makeText(baseActivity, Constants.LOGOUT_TEXT, Toast.LENGTH_LONG);
                    drawer.closeDrawer(GravityCompat.START);
                } else
                    onMenuItemSelected(position, true);
            }

            @Override
            public void onLongClick() {

            }
        });
        rvMenu.setAdapter(adapter);
    }

    public void onMenuItemSelected(int position, boolean changeScreen) {
        DrawerMenuModel menuItem = menus.get(position);
        if (prePosition != -1 && prePosition < menus.size()) {
            menus.get(prePosition).isSelected = false;
        }
        if (position >= 0 && position < menus.size())
            menuItem.isSelected = true;
        prePosition = position;
        adapter.notifyDataSetChanged();
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        boolean isItemClicked = true;
        switch (menuItem.id) {
            case Constants.HOME_ID:
            case Constants.LIKED_ID:
            case Constants.FOLLOWED_ID:
                bundle.putString(Constants.SCREEN_TITLE, menuItem.title);
                bundle.putInt(Constants.SCREEN_POSITION, position);
                bundle.putInt(Constants.SCREEN_ID, menuItem.id);
                fragment = new HomeFragment();
                fragment.setArguments(bundle);
                break;
            case Constants.PRIVACY_POLICY_ID:
            case Constants.TERMS_CONDITION_ID:
            case Constants.APP_VERSION_ID:
                bundle.putString(Constants.SCREEN_TITLE, menuItem.title);
                bundle.putInt(Constants.SCREEN_POSITION, position);
                fragment = new AboutFragment();
                fragment.setArguments(bundle);
                break;
            case Constants.ABOUT_ID:
                isItemClicked = false;
        }
        if (fragment != null && changeScreen)
            baseActivity.changeFragment(fragment, true);
        if (isItemClicked)
            drawer.closeDrawer(GravityCompat.START);
    }

    public DrawerLayout getDrawer() {
        return drawer;
    }

    public void showNavigation() {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        toggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_UNLOCKED);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
    }

    public void hideNavigation() {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        toggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();
    }

    public void updateMenus() {
        setAdapter();
        baseActivity.moveToHomeFragment();
        adapter.notifyDataSetChanged();
        setUpUserData();
    }
}
