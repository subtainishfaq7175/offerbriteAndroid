package com.vb.offerbrite.screens.offersdetail.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.vb.offerbrite.R;
import com.vb.offerbrite.baseclasses.BaseFragment;
import com.vb.offerbrite.data.UpdateOffer;
import com.vb.offerbrite.data.models.FollowOfferModel;
import com.vb.offerbrite.data.models.LikeOfferModel;
import com.vb.offerbrite.data.models.OffersModel;
import com.vb.offerbrite.resservice.ResGeneral;
import com.vb.offerbrite.resservice.ResOfferDetail;
import com.vb.offerbrite.utils.AppUtils;
import com.vb.offerbrite.utils.Constants;
import com.vb.offerbrite.utils.ServerConstants;

import retrofit2.Call;
import retrofit2.Response;

public class OfferDetailFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private String offerId;
    private TextView tvCategoryName;
    private ImageView ivOffer;
    private TextView tvOfferTitle;
    private TextView tvLikes;
    private LinearLayout llLikes;
    private TextView tvFollowers;
    private LinearLayout llFollowers;
    private TextView tvStartDate;
    private TextView tvEndDate;
    private TextView tvStartTime;
    private TextView tvEndTime;
    private TextView tvDescription;
    private ScrollView svDetail;
    private ProgressBar pbDetail;
    private TextView tvAdditionalInfo;
    private TextView tvTermsCondition;
    private OffersModel offerDetailData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtras();
    }

    private void getExtras() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            offerId = bundle.getString(Constants.OFFER_ID, "0");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_offer_detail, container, false);
            init();
            getOfferDetail();
        }
        return rootView;
    }

    private void getOfferDetail() {
        pbDetail.setVisibility(View.VISIBLE);
        if (AppUtils.isNetworkAvailable(baseActivity)) {
            final Call<ResOfferDetail> request = application.getClient().getOfferDetail(offerId);
            request.enqueue(new retrofit2.Callback<ResOfferDetail>() {

                @Override
                public void onResponse(Call<ResOfferDetail> call, Response<ResOfferDetail> response) {
                    pbDetail.setVisibility(View.GONE);
                    if (response.isSuccessful() && response.body() != null) {
                        if (response.body().response &&
                                response.body().responseCode.equalsIgnoreCase(ServerConstants.SUCCESS_CODE)) {
                            if (response.body().data != null) {
                                offerDetailData = response.body().data;
                                setOfferData();
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
                public void onFailure(Call<ResOfferDetail> call, Throwable t) {
                    pbDetail.setVisibility(View.GONE);
                    if (!request.isCanceled())
                        AppUtils.makeText(baseActivity, t.getMessage(), Toast.LENGTH_LONG);
                }
            });
        } else {
            pbDetail.setVisibility(View.GONE);
            AppUtils.makeText(baseActivity, Constants.NO_INTERNET_CONNECTION, Toast.LENGTH_LONG);
        }
    }

    private void setOfferData() {
        svDetail.setVisibility(View.VISIBLE);
        tvCategoryName.setText(offerDetailData.categoryId);
        AppUtils.loadImage(baseActivity, offerDetailData.banner, R.drawable.ic_temp, ivOffer, null);
        tvOfferTitle.setText(offerDetailData.title);
        tvLikes.setText(String.valueOf(offerDetailData.totalLikes));
        tvFollowers.setText(String.valueOf(offerDetailData.totalFollows));

        if (!AppUtils.isEmpty(prefs.getToken())) {

            if (offerDetailData.isLiked)
                tvLikes.setTypeface(null, Typeface.BOLD);
            else
                tvLikes.setTypeface(null, Typeface.NORMAL);

            if (offerDetailData.isfollowed)
                tvFollowers.setTypeface(null, Typeface.BOLD);
            else
                tvFollowers.setTypeface(null, Typeface.NORMAL);

            llFollowers.setOnClickListener(this);
            llLikes.setOnClickListener(this);
        }

        tvStartDate.setText(offerDetailData.startDate);
        tvEndDate.setText(offerDetailData.endDate);
        tvStartTime.setText(offerDetailData.starthours);
        tvEndTime.setText(offerDetailData.EndHours);
        tvDescription.setText(offerDetailData.description);
        tvAdditionalInfo.setText(offerDetailData.additionalInformation);

        tvTermsCondition.setOnClickListener(this);
    }

    private void init() {
        svDetail = (ScrollView) rootView.findViewById(R.id.sv_detail);
        pbDetail = (ProgressBar) rootView.findViewById(R.id.pb_detail);
        tvCategoryName = (TextView) rootView.findViewById(R.id.tv_category_name);
        ivOffer = (ImageView) rootView.findViewById(R.id.iv_offer);
        tvOfferTitle = (TextView) rootView.findViewById(R.id.tv_offer_title);
        tvLikes = (TextView) rootView.findViewById(R.id.tv_likes);
        llLikes = (LinearLayout) rootView.findViewById(R.id.ll_likes);
        tvFollowers = (TextView) rootView.findViewById(R.id.tv_followers);
        llFollowers = (LinearLayout) rootView.findViewById(R.id.ll_followers);
        tvStartDate = (TextView) rootView.findViewById(R.id.tv_start_date);
        tvEndDate = (TextView) rootView.findViewById(R.id.tv_end_date);
        tvStartTime = (TextView) rootView.findViewById(R.id.tv_start_time);
        tvEndTime = (TextView) rootView.findViewById(R.id.tv_end_time);
        tvDescription = (TextView) rootView.findViewById(R.id.tv_description);
        tvAdditionalInfo = (TextView) rootView.findViewById(R.id.tv_additional_info);
        tvTermsCondition = (TextView) rootView.findViewById(R.id.tv_terms_condition);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_terms_condition:
                AppUtils.makeText(baseActivity, ((TextView) view).getText().toString());
                break;

            case R.id.ll_followers:
                int isFollowed = 0;
                if (!offerDetailData.isfollowed) {
                    isFollowed = 1;
                }
                likeFollowOffer(false, true, isFollowed);
                break;

            case R.id.ll_likes:
                int isLiked = 0;
                if (!offerDetailData.isLiked) {
                    isLiked = 1;
                }
                likeFollowOffer(true, false, isLiked);
                break;
        }
    }

    private void likeFollowOffer(boolean isLiked, boolean isFollowed, int value) {
        if (AppUtils.isNetworkAvailable(baseActivity)) {
            Call<ResGeneral> request = null;
            if (isFollowed) {
                FollowOfferModel offerStatus = new FollowOfferModel();
                offerStatus.isFollowed = value;
                offerStatus.offerId = offerDetailData.id;
                offerStatus.userId = prefs.getUserId();
                request = application.getClient().followOffer(prefs.getToken(), offerStatus);
            } else if (isLiked) {
                LikeOfferModel offerStatus = new LikeOfferModel();
                offerStatus.isLiked = value;
                offerStatus.offerId = offerDetailData.id;
                offerStatus.userId = prefs.getUserId();
                request = application.getClient().likeOffer(prefs.getToken(), offerStatus);
            }
            if (request != null) {
                final Call<ResGeneral> finalRequest = request;
                request.enqueue(new retrofit2.Callback<ResGeneral>() {

                    @Override
                    public void onResponse(Call<ResGeneral> call, Response<ResGeneral> response) {
                        pbDetail.setVisibility(View.GONE);
                        if (response.isSuccessful() && response.body() != null) {
                            if (!AppUtils.isEmpty(response.body().responseMessage)) {
                                AppUtils.makeText(baseActivity, response.body().responseMessage, Toast.LENGTH_LONG);
                                if (response.body().response) {
                                    upDateLikeFollowStatus(response.body().responseCode);
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
                    public void onFailure(Call<ResGeneral> call, Throwable t) {
                        pbDetail.setVisibility(View.GONE);
                        if (!finalRequest.isCanceled())
                            AppUtils.makeText(baseActivity, t.getMessage(), Toast.LENGTH_LONG);
                    }
                });
            }
        } else {
            pbDetail.setVisibility(View.GONE);
            AppUtils.makeText(baseActivity, Constants.NO_INTERNET_CONNECTION, Toast.LENGTH_LONG);
        }
    }

    private void upDateLikeFollowStatus(String responseCode) {
        boolean updateData = false;
        if (responseCode.equalsIgnoreCase(ServerConstants.LIKED_CODE)) {
            offerDetailData.isLiked = true;
            offerDetailData.totalLikes++;
            updateData = true;
        } else if (responseCode.equalsIgnoreCase(ServerConstants.UN_LIKED_CODE)) {
            offerDetailData.isLiked = false;
            offerDetailData.totalLikes--;
            updateData = true;
        } else if (responseCode.equalsIgnoreCase(ServerConstants.FOLLOWED_CODE)) {
            offerDetailData.isfollowed = true;
            offerDetailData.totalFollows++;
            updateData = true;
        } else if (responseCode.equalsIgnoreCase(ServerConstants.UN_FOLLOWED_CODE)) {
            offerDetailData.isfollowed = false;
            offerDetailData.totalFollows--;
            updateData = true;
        }
        if (updateData) {
            setOfferData();
            UpdateOffer.setOffer(offerDetailData);
        }
    }
}
