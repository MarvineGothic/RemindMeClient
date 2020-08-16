package com.qoobico.remindme.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.qoobico.remindme.dto.RemindDTO;
import com.qoobico.remindme.rest_api.RestAsync;

import java.util.List;

import static com.qoobico.remindme.utils.Constants.CARD_VIEW_ID;
import static com.qoobico.remindme.utils.Constants.REMIND_ITEM_LAYOUT;
import static com.qoobico.remindme.utils.Constants.URL.DELETE_REMIND_ITEM;
import static com.qoobico.remindme.utils.Constants.VIEW_ID_ID;
import static com.qoobico.remindme.utils.Constants.VIEW_TAB_ID;
import static com.qoobico.remindme.utils.Constants.VIEW_TITLE_ID;

public class RemindListAdapter extends RecyclerView.Adapter<RemindListAdapter.RemindViewHolder> {

    private List<RemindDTO> data;

    public RemindListAdapter(List<RemindDTO> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RemindViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(REMIND_ITEM_LAYOUT, parent, false);
        return new RemindViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RemindViewHolder holder, int position) {
        RemindDTO remindDTO = data.get(position);
        holder.title.setText(remindDTO.getTitle());
        holder.tab_name.setText(remindDTO.getTab_name().toUpperCase());
        holder.id.setText(String.valueOf(remindDTO.getId()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<RemindDTO> data) {
        this.data = data;
    }

    public static class RemindViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView title;
        TextView tab_name;
        TextView id;

        public RemindViewHolder(@NonNull final View itemView) {
            super(itemView);

            cardView = itemView.findViewById(CARD_VIEW_ID);
            title = itemView.findViewById(VIEW_TITLE_ID);
            tab_name = itemView.findViewById(VIEW_TAB_ID);
            id = itemView.findViewById(VIEW_ID_ID);
            // todo: make a pop up window with options edit or delete
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new RestAsync().deleteData(DELETE_REMIND_ITEM + id.getText());
                }
            });
        }
    }
}
