package com.qoobico.remindme.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.qoobico.remindme.activity.AddReminderActivity;
import com.qoobico.remindme.activity.MainActivity;
import com.qoobico.remindme.dto.RemindDTO;
import com.qoobico.remindme.rest_api.RestAsync;

import java.util.List;

import static com.qoobico.remindme.utils.Constants.*;
import static com.qoobico.remindme.utils.Constants.URL.DELETE_REMIND_ITEM;
import static com.qoobico.remindme.utils.Constants.REMINDER_ID;
import static com.qoobico.remindme.utils.Constants.REMINDER_TYPE;
import static com.qoobico.remindme.utils.Constants.REMINDER_TITLE;

public class RemindListAdapter extends RecyclerView.Adapter<RemindListAdapter.RemindViewHolder> {

    private List<RemindDTO> data;
    private Context context;

    public RemindListAdapter(List<RemindDTO> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public RemindViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(REMIND_ITEM_LAYOUT, parent, false);
        return new RemindViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RemindViewHolder holder, int position) {
        RemindDTO remindDTO = data.get(position);
        holder.title.setText(remindDTO.getTitle());
        holder.tab_name.setText(remindDTO.getTab_name().toUpperCase());
        holder.id.setText(String.valueOf(remindDTO.getId()));
        holder.date.setText(String.valueOf(remindDTO.getRemindDate()));
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
        TextView date;

        public RemindViewHolder(@NonNull final View itemView, final Context context) {
            super(itemView);

            cardView = itemView.findViewById(CARD_VIEW_ID);
            title = itemView.findViewById(REMINDER_TITLE);
            tab_name = itemView.findViewById(REMINDER_TYPE);
            id = itemView.findViewById(REMINDER_ID);
            date = itemView.findViewById(REMINDER_DATE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Click on: " + id.getText());
//                    new RestAsync().deleteData(DELETE_REMIND_ITEM + id.getText());
                    Intent intent = new Intent(context, AddReminderActivity.class);
                    ((AppCompatActivity) context).startActivityForResult(intent, ADD_REMINDER_ACTIVITY_CODE);
                }
            });
        }
    }
}
