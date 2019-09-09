package com.qoobico.remindme.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.qoobico.remindme.MainActivity;
import com.qoobico.remindme.R;
import com.qoobico.remindme.dto.RemindDTO;
import com.qoobico.remindme.rest_api.RestAsync;

import java.util.List;

import static com.qoobico.remindme.Constants.URL.DELETE_REMIND_ITEM;

public class RemindListAdapter extends RecyclerView.Adapter<RemindListAdapter.RemindViewHolder> {

    private List<RemindDTO> data;

    public RemindListAdapter(List<RemindDTO> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RemindViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.remind_item, parent, false);
        return new RemindViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RemindViewHolder holder, int position) {
        RemindDTO remindDTO = data.get(position);
        holder.title.setText(remindDTO.getTitle());
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
        TextView id;

        public RemindViewHolder(@NonNull final View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_view);
            title = itemView.findViewById(R.id.title);
            id = itemView.findViewById(R.id.id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new RestAsync().deleteData(DELETE_REMIND_ITEM + id.getText());
                }
            });
        }
    }
}
