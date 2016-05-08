package com.cloudy.basetools.shareapk;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudy.basetools.enterpage.R;

import java.util.List;

/**
 * Created by Administrator on 2016/5/7.
 */
public class GetApkBaseAdapter extends BaseAdapter {
    private static final String TAG = "Cloudy/GetApkBaseAdapter";
    private List<ShareItemBean> mData;
    private Context mContext;

    public GetApkBaseAdapter(Context context, List<ShareItemBean> data) {
        mContext = context;
        mData = data;
    }

    @Override

    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView: ");
        View view = null;
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.itembean_share, null);
            viewHolder.iconView = (ImageView) view.findViewById(R.id.icon_share_id);
            viewHolder.nameView = (TextView) view.findViewById(R.id.name_apk_share_id);
            viewHolder.sizeView = (TextView) view.findViewById(R.id.size_apk_share_id);
            viewHolder.timeView = (TextView) view.findViewById(R.id.time_apk_share_id);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.iconView.setImageDrawable(((ShareItemBean)getItem(position)).mIcon);
        viewHolder.nameView.setText(((ShareItemBean)getItem(position)).mAppName);
        viewHolder.sizeView.setText(((ShareItemBean)getItem(position)).mAppSize);
        viewHolder.timeView.setText(((ShareItemBean)getItem(position)).mUpdateTime);
        return view;
    }

    class ViewHolder {
        public ImageView iconView;
        public TextView nameView;
        public TextView sizeView;
        public TextView timeView;
    }
}
