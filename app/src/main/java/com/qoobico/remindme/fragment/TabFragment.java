package com.qoobico.remindme.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qoobico.remindme.adapter.RemindListAdapter;
import com.qoobico.remindme.dto.RemindDTO;

import java.util.ArrayList;
import java.util.List;

import static com.qoobico.remindme.utils.Constants.RECYCLER_VIEW_ID;

/**
 * Class for the tab window
 */
public class TabFragment extends Fragment {
    private int tabLayout;
    private String tabTitle;
    private Context context;
    private RemindListAdapter remindListAdapter;

    public TabFragment(Context context, int[] fragment_data, List<RemindDTO> data) {
        Bundle args = new Bundle();

        remindListAdapter = new RemindListAdapter(data, context);
        this.setArguments(args);
        this.context = context;
        this.tabLayout = fragment_data[1];
        this.tabTitle = context.getString(fragment_data[2]);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(tabLayout, container, false);

        RecyclerView recyclerView = view.findViewById(RECYCLER_VIEW_ID);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(remindListAdapter);
        return view;
    }

    /**
     * Refresh data for each tab accordingly to the tab name.
     * So all tabs will be sorted by the tab name.
     *
     * @param data
     */
    public void refreshData(List<RemindDTO> data) {

        List<RemindDTO> current_data = new ArrayList<>();
        String tab_name = this.getTabTitle().toUpperCase();
        if (tab_name.equals("ALL")) {
            current_data = data;
        } else
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getTab_name().toUpperCase().equals(tab_name)) {
                    current_data.add(data.get(i));
                }
            }
        remindListAdapter.setData(current_data);
        remindListAdapter.notifyDataSetChanged();
    }

    /**
     * Tab name. For example: IDEAS or BIRTHDAYS
     * @return
     */
    public String getTabTitle() {
        return tabTitle;
    }
}
