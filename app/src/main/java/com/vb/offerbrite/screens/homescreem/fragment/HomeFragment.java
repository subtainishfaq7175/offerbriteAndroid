package com.vb.offerbrite.screens.homescreem.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vb.offerbrite.R;
import com.vb.offerbrite.baseclasses.BaseFragment;
import com.vb.offerbrite.data.UpdateOffer;
import com.vb.offerbrite.data.models.OffersModel;
import com.vb.offerbrite.resservice.ResOffersList;
import com.vb.offerbrite.screens.homescreem.adapter.OffersAdapter;
import com.vb.offerbrite.utils.AppUtils;
import com.vb.offerbrite.utils.Constants;
import com.vb.offerbrite.utils.GeneralListener;
import com.vb.offerbrite.utils.ServerConstants;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

public class HomeFragment extends BaseFragment {

    private View rootView;
    private int screenId;
    private ProgressBar pbHome;
    private ArrayList<OffersModel> offersList = new ArrayList<>();
    private OffersAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtras();
    }

    public void getExtras() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            screenId = bundle.getInt(Constants.SCREEN_ID, 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
            init();
            getOffersList();
        }
        return rootView;
    }

    private void init() {
        pbHome = (ProgressBar) rootView.findViewById(R.id.pb_home);
        RecyclerView rvOffers = (RecyclerView) rootView.findViewById(R.id.rv_offers);
        rvOffers.setLayoutManager(new LinearLayoutManager(baseActivity));
        adapter = new OffersAdapter(baseActivity, offersList, new GeneralListener.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                baseActivity.changeToDetail(offersList.get(position));
            }

            @Override
            public void onLongClick() {

            }
        });
        rvOffers.setAdapter(adapter);
    }

    private void getOffersList() {
        pbHome.setVisibility(View.VISIBLE);
        if (AppUtils.isNetworkAvailable(baseActivity)) {
            final Call<ResOffersList> request;
            String token = prefs.getToken();
            HashMap<String, String> headers = new HashMap<>();
            if (AppUtils.isEmpty(token)) {
                headers.put(Constants.TOKEN_TEXT, token);
            }
            if (screenId == Constants.LIKED_ID)
                request = application.getClient().getHomeOffers(headers);
            else if (screenId == Constants.FOLLOWED_ID)
                request = application.getClient().getHomeOffers(headers);
            else
                request = application.getClient().getHomeOffers(headers);
            request.enqueue(new retrofit2.Callback<ResOffersList>() {

                @Override
                public void onResponse(Call<ResOffersList> call, Response<ResOffersList> response) {
                    pbHome.setVisibility(View.GONE);
                    if (response.isSuccessful() && response.body() != null) {
                        if (response.body().response &&
                                response.body().responseCode.equalsIgnoreCase(ServerConstants.SUCCESS_CODE)) {
                            if (response.body().data != null) {
                                setOffersList(response.body().data);
                            } else if (!AppUtils.isEmpty(response.body().responseMessage)) {
                                AppUtils.makeText(baseActivity, response.body().responseMessage, Toast.LENGTH_LONG);
                            }
                        } else {
                            if (!AppUtils.isEmpty(response.body().responseMessage))
                                AppUtils.makeText(baseActivity, response.body().responseMessage, Toast.LENGTH_LONG);
                        }
                    } else {
                        AppUtils.makeText(baseActivity, response.message());
                    }
                }

                @Override
                public void onFailure(Call<ResOffersList> call, Throwable t) {
                    pbHome.setVisibility(View.GONE);
                    if (!request.isCanceled())
                        AppUtils.makeText(baseActivity, t.getMessage(), Toast.LENGTH_LONG);
                }
            });
        } else {
            pbHome.setVisibility(View.GONE);
            AppUtils.makeText(baseActivity, Constants.NO_INTERNET_CONNECTION, Toast.LENGTH_LONG);
        }
    }

    private void setOffersList(ArrayList<OffersModel> data) {
        offersList.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        OffersModel offer = UpdateOffer.getOffer();
        if (offer != null) {
            for (OffersModel item : offersList)
                if (item.id == offer.id) {
                    item.totalFollows = offer.totalFollows;
                    item.totalLikes = offer.totalLikes;
                    adapter.notifyDataSetChanged();
                    UpdateOffer.setOffer(null);
                    break;
                }
        }
    }
}
