package com.vb.offerbrite.screens.aboutscreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vb.offerbrite.R;
import com.vb.offerbrite.baseclasses.BaseFragment;
import com.vb.offerbrite.utils.AppUtils;

public class AboutFragment extends BaseFragment {

    private View rootView;
    private TextView tvAbout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_about, container, false);
            init();
        }
        return rootView;
    }

    private void init() {
        tvAbout = (TextView) rootView.findViewById(R.id.tv_about_title);
        if (!AppUtils.isEmpty(title)) {
            tvAbout.setText(title);
        }
    }
}
