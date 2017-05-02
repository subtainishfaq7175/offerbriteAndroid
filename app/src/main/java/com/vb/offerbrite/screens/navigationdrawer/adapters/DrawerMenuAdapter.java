package com.vb.offerbrite.screens.navigationdrawer.adapters;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vb.offerbrite.R;
import com.vb.offerbrite.data.models.DrawerMenuModel;
import com.vb.offerbrite.utils.AppUtils;
import com.vb.offerbrite.utils.GeneralListener;

import java.util.ArrayList;

public class DrawerMenuAdapter extends RecyclerView.Adapter<DrawerMenuAdapter.MyViewHolder> {
    private final ArrayList<DrawerMenuModel> menusList;
    private final GeneralListener.OnItemClickListener listener;
    private Context context;

    public DrawerMenuAdapter(Context context, ArrayList<DrawerMenuModel> menusList, GeneralListener.OnItemClickListener listener) {
        this.context = context;
        this.menusList = menusList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.navigation_manu_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        DrawerMenuModel menuItem = menusList.get(position);
        holder.tvMenuTitle.setText(menuItem.title);
        int textColor = Color.BLACK;
        int backgroundColor = Color.WHITE;
        if (menuItem.isSelected) {
            textColor = context.getResources().getColor(R.color.colorPrimary);
            backgroundColor = context.getResources().getColor(R.color.white_back_ground);
        }
        holder.tvMenuTitle.setTextColor(textColor);
        holder.itemView.setBackgroundColor(backgroundColor);
        if (!menuItem.isClickable) {
            holder.tvMenuTitle.setTextColor(Color.GRAY);
            int paddingLeftRight = AppUtils.dpToPx(context, 12);
            int paddingTopBottom = AppUtils.dpToPx(context, 6);
            holder.tvMenuTitle.setPadding(paddingLeftRight, paddingTopBottom, paddingLeftRight, paddingTopBottom);
        } else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                        listener.onClick(position);
                }
            });
        }
        if (menuItem.isDivider)
            holder.vDivider.setVisibility(View.VISIBLE);
        else
            holder.vDivider.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return menusList == null ? 0 : menusList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvMenuTitle;
        private final View vDivider;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvMenuTitle = (TextView) itemView.findViewById(R.id.tv_menu_title);
            vDivider = itemView.findViewById(R.id.v_divider);
        }
    }
}
