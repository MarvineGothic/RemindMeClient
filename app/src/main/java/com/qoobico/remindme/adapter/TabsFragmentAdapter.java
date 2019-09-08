package com.qoobico.remindme.adapter;

import android.content.Context;
import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.qoobico.remindme.fragment.BaseFragment;
import com.qoobico.remindme.fragment.FragmentFactory;

import static com.qoobico.remindme.Constants.BIRTHDAYS;
import static com.qoobico.remindme.Constants.HISTORY;
import static com.qoobico.remindme.Constants.IDEAS;
import static com.qoobico.remindme.Constants.TODO;

public class TabsFragmentAdapter extends FragmentPagerAdapter {

    private SparseArray<BaseFragment> tabs = new SparseArray<>();
    private Context context;

    public TabsFragmentAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
        initTabsMap();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        BaseFragment fragment = tabs.get(position);
        return fragment == null ? "" : fragment.getTitle();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        BaseFragment fragment = tabs.get(position);
        return fragment == null ? new Fragment() : fragment;
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    private void initTabsMap() {
        tabs.put(0, FragmentFactory.getInstance(context, HISTORY));
        tabs.put(1, FragmentFactory.getInstance(context, IDEAS));
        tabs.put(2, FragmentFactory.getInstance(context, TODO));
        tabs.put(3, FragmentFactory.getInstance(context, BIRTHDAYS));
    }
}
