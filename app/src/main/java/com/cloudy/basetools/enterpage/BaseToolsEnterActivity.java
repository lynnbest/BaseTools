package com.cloudy.basetools.enterpage;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cloudy.basetools.shareapk.ShareActivity;

import java.util.ArrayList;
import java.util.List;

public class BaseToolsEnterActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener {
    private static final String TAG = "cloudy/EnterActivity";
    private ListView mListView = null;
    private BaseAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_tools_enter);
        Log.d(TAG, "onCreate: ");
        mListView = (ListView) findViewById(R.id.listview_selecet_id);
        if (mListView != null) {
            mListView.setOnItemClickListener(this);
        }
        configListView();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemClick: position: " + position);
        int selectId = ((SelectItemBean) mAdapter.getItem(position)).getActionRes();
        switch (selectId) {
            case R.string.select_action_name_shareapk:
                Log.d(TAG, "onItemClick: share apk action is click!");
                Toast.makeText(this, "share apk will start!", Toast.LENGTH_SHORT).show();
                doShareApk();
                break;
            case R.string.select_action_name_qrcode:
                Toast.makeText(this, "scan had no implement!", Toast.LENGTH_SHORT).show();
                break;
            case R.string.select_action_name_musicrepeater:
                Toast.makeText(this, "repeater had no implements!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void configListView() {
        createAndConfigAdapter(this, initData());
    }

    private List<SelectItemBean> initData() {
        //get data
        int[] acitonNameArray = new int[]{
                R.string.select_action_name_shareapk,
                R.string.select_action_name_qrcode,
                R.string.select_action_name_musicrepeater};

        List<Drawable> iconArray = new ArrayList<Drawable>();
        iconArray.add(getResources().getDrawable(R.drawable.share));
        iconArray.add(getResources().getDrawable(R.drawable.scan));
        iconArray.add(getResources().getDrawable(R.drawable.repeater));

        List<SelectItemBean> data = new ArrayList<SelectItemBean>();
        for (int i = 0; i < acitonNameArray.length; i++) {
            int actionNameResId = acitonNameArray[i];
            SelectItemBean itemBean = new SelectItemBean(iconArray.get(i),
                    this.getString(actionNameResId), actionNameResId);
            Log.d(TAG, "initData: itemBean: " + itemBean);
            data.add(itemBean);
        }

        return data;
    }

    private BaseAdapter createAndConfigAdapter(Context context, List<SelectItemBean> data) {
        mAdapter = new SelectActionAdapter(context, data);
        mListView.setAdapter(mAdapter);
        return mAdapter;
    }

    private void doShareApk() {
        Log.d(TAG, "doShareApk: ");
        Intent shareIntent = new Intent(this, ShareActivity.class);
        startActivity(shareIntent);
    }
}
