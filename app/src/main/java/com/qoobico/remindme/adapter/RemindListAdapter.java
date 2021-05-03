package com.qoobico.remindme.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.qoobico.remindme.R;
import com.qoobico.remindme.activity.SaveReminderActivity;
import com.qoobico.remindme.dto.RemindDTO;
import com.qoobico.remindme.manager.ReminderManager;

import java.util.List;

import static com.qoobico.remindme.utils.Constants.*;

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
        holder.tabName.setText(remindDTO.getTabName().toUpperCase());
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
        TextView tabName;
        TextView id;
        TextView date;

        public RemindViewHolder(@NonNull final View itemView, final Context context) {
            super(itemView);

            cardView = itemView.findViewById(CARD_VIEW_ID);
            title = itemView.findViewById(REMINDER_TITLE);
            tabName = itemView.findViewById(REMINDER_TYPE);
            id = itemView.findViewById(REMINDER_ID);
            date = itemView.findViewById(REMINDER_DATE);

            itemView.setOnLongClickListener(view -> {
                new MaterialAlertDialogBuilder(new ContextThemeWrapper(context, R.style.AppTheme2))
                        .setTitle("Reminder")
                        .setMessage("Edit reminder")
                        .setNeutralButton(DIALOG_CANCEL, (dialogInterface, i) -> {

                        })
                        .setNegativeButton(DIALOG_DELETE, (dialogInterface, i) ->
                                new MaterialAlertDialogBuilder(new ContextThemeWrapper(context, R.style.AppTheme2))
                                        .setTitle("You're about to delete this reminder")
                                        .setMessage("Delete anyway?")
                                        .setNeutralButton(DIALOG_CANCEL, (deleteDialogInterface, j) -> {

                                        })
                                        .setPositiveButton(DIALOG_DELETE, (deleteDialogInterface, j) -> {
                                            ReminderManager.deleteReminder(id.getText().toString());
                                        })
                                        .show())
                        .setPositiveButton(DIALOG_EDIT, (dialogInterface, i) -> {
                            System.out.println("Click on: " + id.getText());
                            Intent intent = new Intent(context, SaveReminderActivity.class);
                            intent.putExtra("id", id.getText());
                            intent.putExtra("title", title.getText());
                            intent.putExtra("tabName", tabName.getText());
                            intent.putExtra("date", date.getText());
                            ((AppCompatActivity) context).startActivityForResult(intent, ADD_REMINDER_ACTIVITY_CODE);
                        })
                        .show();
                return true;
            });
        }
    }
}
