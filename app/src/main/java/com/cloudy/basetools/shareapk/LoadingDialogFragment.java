package com.cloudy.basetools.shareapk;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.cloudy.basetools.enterpage.R;

/**
 * using DialogFragment show progressDialog
 */
public class LoadingDialogFragment extends DialogFragment {
    private static final String TAG = "Cloudy/LodingDialog";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage(getActivity().getString(R.string.loading_share_message));
        return dialog;
    }

    public static DialogFragment createAndShow(Context context, FragmentManager fm, String tag) {
        final LoadingDialogFragment fragment = new LoadingDialogFragment();
        fragment.show(fm, tag);
        return fragment;
    }
}
