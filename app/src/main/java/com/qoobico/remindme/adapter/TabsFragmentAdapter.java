package com.qoobico.remindme.adapter;

import android.content.Context;
import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.qoobico.remindme.dto.RemindDTO;
import com.qoobico.remindme.fragment.TabFragment;

import java.util.ArrayList;
import java.util.List;

public class TabsFragmentAdapter extends FragmentPagerAdapter {

    private Context context;
    private SparseArray<TabFragment> tabs = new SparseArray<>();

    private List<RemindDTO> data;

    public TabsFragmentAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm);
        this.context = context;
        this.data = new ArrayList<>();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        TabFragment fragment = tabs.get(position);
        return fragment == null ? "" : fragment.getTabTitle();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        TabFragment fragment = tabs.get(position);
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

    public TabsFragmentAdapter initTabsMap(int[]... tabsID) {
        for (int i = 0; i < tabsID.length; i++)
            tabs.put(i, new TabFragment(this.context, tabsID[i], data));
        return this;
    }
}
