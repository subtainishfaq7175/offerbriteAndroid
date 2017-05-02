package com.vb.offerbrite.screens.homescreem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vb.offerbrite.R;
import com.vb.offerbrite.data.models.OffersModel;
import com.vb.offerbrite.utils.AppUtils;
import com.vb.offerbrite.utils.GeneralListener;

import java.util.ArrayList;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.MyViewHolder> {

    private final Context context;
    private final GeneralListener.OnItemClickListener listener;
    private final ArrayList<OffersModel> offersList;

    public OffersAdapter(Context context, ArrayList<OffersModel> offersList, GeneralListener.OnItemClickListener listener) {
        this.context = context;
        this.offersList = offersList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.home_offers_list_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        OffersModel offerItem = offersList.get(position);
        AppUtils.loadImage(context, offerItem.banner, R.drawable.ic_temp, holder.ivOffer, null);
        holder.tvOfferTitle.setText(offerItem.title);
        holder.tvLikes.setText(String.valueOf(offerItem.totalLikes));
        holder.tvFollowers.setText(String.valueOf(offerItem.totalFollows));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return offersList == null ? 0 : offersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivOffer;
        private final TextView tvOfferTitle;
        private final TextView tvLikes;
        private final TextView tvFollowers;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivOffer = (ImageView) itemView.findViewById(R.id.iv_offer);
            tvOfferTitle = (TextView) itemView.findViewById(R.id.tv_offer_title);
            tvLikes = (TextView) itemView.findViewById(R.id.tv_likes);
            tvFollowers = (TextView) itemView.findViewById(R.id.tv_followers);
        }
    }
}
