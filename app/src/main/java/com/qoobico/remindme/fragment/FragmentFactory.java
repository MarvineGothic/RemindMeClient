package com.qoobico.remindme.fragment;

import android.content.Context;
import android.os.Bundle;

public class FragmentFactory extends BaseFragment {

    public static BaseFragment getInstance(Context context, int[] fragment_data) {
        Bundle args = new Bundle();
        BaseFragment fragment = new BaseFragment();
        fragment.setLayout(fragment_data[0]);
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(fragment_data[1]));
        return fragment;
    }
}