package com.vb.offerbrite.screens.offersdetail;

import android.os.Bundle;

import com.vb.offerbrite.baseclasses.BaseActivity;
import com.vb.offerbrite.screens.offersdetail.fragment.OfferDetailFragment;

public class OfferDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setUpToolbar(false, true, true);
    }

    private void init() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            OfferDetailFragment fragment = new OfferDetailFragment();
            fragment.setArguments(bundle);
            changeFragment(fragment, false);
        }
    }
}
