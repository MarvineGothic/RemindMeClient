package com.qoobico.remindme.adapter;

import android.content.Context;
import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.qoobico.remindme.dto.RemindDTO;
import com.qoobico.remindme.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import static com.qoobico.remindme.Constants.BIRTHDAYS;
import static com.qoobico.remindme.Constants.HISTORY;
import static com.qoobico.remindme.Constants.IDEAS;
import static com.qoobico.remindme.Constants.TODO;

public class TabsFragmentAdapter extends FragmentPagerAdapter {

    private SparseArray<BaseFragment> tabs = new SparseArray<>();

    private List<RemindDTO> data;

    public TabsFragmentAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm);
        this.data = new ArrayList<>();
        initTabsMap(context);
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

    public void setData(List<RemindDTO> data) {
        this.data = data;
        for (int i = 0; i < tabs.size(); i++)
            tabs.valueAt(i).refreshData(data);
    }

    private void initTabsMap(Context context) {
        tabs.put(0, new BaseFragment(context, HISTORY, data));
        tabs.put(1, new BaseFragment(context, IDEAS, data));
        tabs.put(2, new BaseFragment(context, TODO, data));
        tabs.put(3, new BaseFragment(context, BIRTHDAYS, data));
    }
}
