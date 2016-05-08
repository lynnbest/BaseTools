package com.cloudy.basetools.shareapk;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/5/6.
 */
public class ShareItemBean {
    public Drawable mIcon;
    public String mAppName;
    public String mAppPath;
    public String mAppSize;
    public String mUpdateTime;

    public ShareItemBean(Drawable icon, String name, String path, String size, String updateTime) {
        mIcon = icon;
        mAppName = name;
        mAppPath = path;
        mAppSize = size;
        mUpdateTime = updateTime;
    }
}
