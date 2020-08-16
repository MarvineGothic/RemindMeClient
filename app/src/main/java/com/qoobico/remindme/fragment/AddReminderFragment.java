/*
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

import java.util.List;

import static com.qoobico.remindme.utils.Constants.RECYCLER_VIEW_ID;

public class AddReminderFragment extends Fragment {
    private int layout;
    private String title;
    private Context context;
    private RemindListAdapter remindListAdapter;

    public AddReminderFragment(Context context, int[] fragment_data, List<RemindDTO> data) {
        Bundle args = new Bundle();

        remindListAdapter = new RemindListAdapter(data);
        this.setArguments(args);
        this.context = context;
        this.layout = fragment_data[1];
        this.title = context.getString(fragment_data[2]);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layout, container, false);

        RecyclerView recyclerView = view.findViewById(RECYCLER_VIEW_ID);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(remindListAdapter);
        return view;
    }

    public void refreshData(List<RemindDTO> data) {
        remindListAdapter.setData(data);
        remindListAdapter.notifyDataSetChanged();
    }

    public String getTabTitle() {
        return title;
    }
}
*/
