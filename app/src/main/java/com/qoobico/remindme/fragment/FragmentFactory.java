package com.qoobico.remindme.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.qoobico.remindme.R;

public class FragmentFactory extends Fragment {
    private static final int LAYOUT = R.layout.fragment_example;

    private String title;
    private Context context;

    public static BaseFragment getInstance(Context context, int title) {
        Bundle args = new Bundle();
        BaseFragment fragment = new BaseFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(title));
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(LAYOUT, container, false);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}