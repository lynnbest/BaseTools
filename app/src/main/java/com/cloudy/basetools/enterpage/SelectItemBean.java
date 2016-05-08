package com.cloudy.basetools.enterpage;

import android.graphics.drawable.Drawable;

/**
 * the data for select for SelectActionAdapter
 */
public class SelectItemBean {
    private Drawable mIcon;
    private String mActionName;
    private int mActionRes;

    public SelectItemBean(Drawable icon, String actionName, int actionRes) {
        this.mActionName = actionName;
        this.mActionRes = actionRes;
        this.mIcon = icon;
    }

    public Drawable getIcon() {
        return mIcon;
    }

    public String getActionName() {
        return mActionName;
    }

    public int getActionRes() {
        return mActionRes;
    }

    public void setActionName(String actionName) {
        this.mActionName = actionName;
    }

    public void setActionRes(int actionRes) {
        this.mActionRes = actionRes;
    }

    @Override
    public String toString() {
        return "[actionName: " + mActionName + ",resId: " + mActionRes + "]";
    }
}
