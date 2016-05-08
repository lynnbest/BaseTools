package com.cloudy.basetools.enterpage;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * the adpater for selecting base tools item.
 */
public class SelectActionAdapter extends BaseAdapter {
    private static final String TAG = "Cloudy/SelectAdapter";

    private Context mContext = null;
    private List<SelectItemBean> mData = null;

    public SelectActionAdapter(Context Context, List<SelectItemBean> Data) {
        mContext = Context;
        mData = Data;
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

    public void add(SelectItemBean itemBean) {
        mData.add(itemBean);
    }

    public void remove(SelectItemBean itemBean) {
        mData.remove(itemBean);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView: position: " + position);
        View view = null;
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.itembean_select, null);
            viewHolder.iconView = (ImageView) view.findViewById(R.id.icon_select_id);
            viewHolder.actionView = (TextView) view.findViewById(R.id.name_select_id);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.iconView.setImageDrawable(((SelectItemBean)getItem(position)).getIcon());
        viewHolder.actionView.setText(((SelectItemBean) getItem(position)).getActionName());
        return view;
    }

    class ViewHolder {
        public ImageView iconView;
        public TextView actionView;
    }
}
