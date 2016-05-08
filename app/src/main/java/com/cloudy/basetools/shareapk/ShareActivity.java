package com.cloudy.basetools.shareapk;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cloudy.basetools.enterpage.R;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShareActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "Cloudy/ShareActivity";
    private ListView mListView = null;
    private BaseAdapter mAdapter = null;
    private DialogFragment mProgressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate:");
        setContentView(R.layout.activity_share_apk);

        mListView = (ListView) findViewById(R.id.listview_share_id);
        if (mListView != null) {
            mListView.setOnItemClickListener(this);
        }
        new GetAppInfoTask().execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String appPath = ((ShareItemBean) mAdapter.getItem(position)).mAppPath;
        Log.d(TAG, "onItemClick: position: " + position + ",appPath: " + appPath);
        File apkFile = new File(appPath);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("*/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(apkFile));
        startActivityForResult(shareIntent, 1);
        //Toast.makeText(ShareActivity.this, "start select share way!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: requestCode: " + requestCode + ",resultCode: " + resultCode +
                ",data: " + data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "send success!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d(TAG, "onPrepareOptionsMenu: ");
        return super.onPrepareOptionsMenu(menu);
    }

    private void makeMenuItemVisible(Menu menu, int itemId, boolean visible) {
        final MenuItem item = menu.findItem(itemId);
        if (item != null) {
            item.setVisible(visible);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.multioperater, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: ");
        switch (item.getItemId()) {
            case R.id.menu_delete:
                Toast.makeText(this, "delete action will start", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_share:
                Toast.makeText(this, "share action will start", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private BaseAdapter getAdapter() {
        return mAdapter;
    }

    private void configAdapter(Context context, List<ShareItemBean> data) {
        Log.d(TAG, "configAdapter: ");
        if (mAdapter == null) {
            mAdapter = new GetApkBaseAdapter(context, data);
        }
    }

    class GetAppInfoTask extends AsyncTask<Void, Integer, List<ShareItemBean>> {

        @Override
        protected void onPostExecute(List<ShareItemBean> data) {
            super.onPostExecute(data);
            Log.d(TAG, "onPostExecute: ");
            if (data != null) {
                mProgressDialog.dismiss();
                configAdapter(ShareActivity.this, data);
                mListView.setAdapter(mAdapter);
            }
        }

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute: show progress dialog");
            mProgressDialog = LoadingDialogFragment.createAndShow(ShareActivity.this, getFragmentManager(),
                    "LodingDialog");

        }

        @Override
        protected List<ShareItemBean> doInBackground(Void... params) {
            PackageManager pm = getPackageManager();
            List<PackageInfo> packageInfoList = pm.getInstalledPackages(0);
            List<ShareItemBean> itemBeanList = new ArrayList<ShareItemBean>();
            for (int i = 0; i < packageInfoList.size(); i++) {
                Drawable drawable = getAppIcon(packageInfoList.get(i), pm);
                String appName = getAppName(packageInfoList.get(i), pm);
                String appPath = getAppPathUri(packageInfoList.get(i));
                String appSize = getAppSize(packageInfoList.get(i)) + " MB";
                String appUpdateTime = getAppUpdateTime(packageInfoList.get(i));
                itemBeanList.add(new ShareItemBean(drawable, appName, appPath, appSize, appUpdateTime));
            }
            Log.d(TAG, "doInBackground: itemBeanList : ");
            return itemBeanList;
        }

        private String getAppName(PackageInfo pi, PackageManager pm) {
            String appName = null;
            appName = (String) pm.getApplicationLabel(pi.applicationInfo);
            Log.d(TAG, "getAppName: " + appName);
            return appName;
        }

        private String getAppPathUri(PackageInfo packageInfo) {
            return packageInfo.applicationInfo.sourceDir;
        }

        private String getAppSize(PackageInfo packageInfo) {
            File apkFile = new File(getAppPathUri(packageInfo));
            double size = (double) apkFile.length() / 1024 / 1024;
            Log.d(TAG, "getAppSize: " + size + ",");
            return String.format("%.2f", size);
        }

        private Drawable getAppIcon(PackageInfo pi, PackageManager pm) {
            Drawable drawable = null;
            drawable = pm.getApplicationIcon(pi.applicationInfo);
            return drawable;
        }

        private String getAppUpdateTime(PackageInfo packageInfo) {
            long updateTimeMs = packageInfo.lastUpdateTime;
            Date date = new Date(updateTimeMs);//long 构造
            DateFormat updateTimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            String updateTimeStr = updateTimeFormat.format(date);
            Log.d(TAG, "getAppUpdateTime: " + updateTimeStr);
            return updateTimeStr;
        }
    }
}
