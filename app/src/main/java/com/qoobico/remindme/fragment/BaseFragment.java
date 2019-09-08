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

import com.qoobico.remindme.R;
import com.qoobico.remindme.adapter.RemindListAdapter;
import com.qoobico.remindme.dto.RemindDTO;

import java.util.ArrayList;
import java.util.List;

public class BaseFragment extends Fragment {
    private int layout;
    private View view;
    private String title;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(layout, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new RemindListAdapter(createMockRemindListData()));
        return view;
    }

    /**
     * Mock method
     * @return
     */
    private List<RemindDTO> createMockRemindListData() {
        List<RemindDTO> data = new ArrayList<>();
        for (int i = 0; i < 6; i++)
            data.add(new RemindDTO("Item " + i));
        return data;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public String getTitle() {
        return title;
    }
}
