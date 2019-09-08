package com.qoobico.remindme.fragment;

import android.content.Context;
import android.os.Bundle;

import com.qoobico.remindme.R;

public class HistoryFragment extends BaseFragment {

    public static HistoryFragment getInstance(Context context) {
        Bundle args = new Bundle();
        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.menu_item_history));
        return fragment;
    }
}
