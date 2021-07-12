package com.health.immunity;


import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment implements IConstant {

    protected Context context;
    protected Activity activity;
    protected String TAG;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        this.activity = getActivity();
        TAG = this.getClass().getSimpleName();
    }
}

