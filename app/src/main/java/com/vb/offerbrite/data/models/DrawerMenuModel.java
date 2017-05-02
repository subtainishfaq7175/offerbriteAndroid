package com.vb.offerbrite.data.models;

public class DrawerMenuModel {

    public String title;
    public int id;
    public boolean isSelected = false;
    public boolean isDivider = false;
    public boolean isClickable = true;

    public DrawerMenuModel(String title, int id, boolean isDivider, boolean isClickable) {
        this.title = title;
        this.id = id;
        this.isDivider = isDivider;
        this.isClickable = isClickable;
    }
}
