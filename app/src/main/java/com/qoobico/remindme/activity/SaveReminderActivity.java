package com.qoobico.remindme.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.qoobico.remindme.notifications.AlertReceiver;
import com.qoobico.remindme.manager.ReminderManager;
import com.qoobico.remindme.utils.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static com.qoobico.remindme.activity.MainActivity.reminderTypes;
import static com.qoobico.remindme.utils.Constants.*;

public class SaveReminderActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextInputEditText reminderTitleInput;
    private AutoCompleteTextView reminderTypeInput;
    private TextInputEditText reminderDateInput;
    private String id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(REMINDER_FORM_THEME);
        super.onCreate(savedInstanceState);

        id = null;
        String title = "";
        String tabName = "";
        String date = "";

        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                id = extras.getString("id");
                title = extras.getString("title");
                tabName = extras.getString("tabName");
                date = extras.getString("date");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentView(ACTIVITY_ADD_REMINDER_LAYOUT);

        initReminderTitleField(title);
        initReminderTypeField(tabName);
        initReminderDatePickerField(date);
        initToolBar();
    }

    private void initReminderTitleField(String title) {
        reminderTitleInput = findViewById(TITLE_INPUT);
        reminderTitleInput.setText(title);
    }

    private void initReminderTypeField(String reminderType) {
        reminderTypeInput = findViewById(REMINDER_TYPE_INPUT);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, REMINDER_TYPE_ITEM_LAYOUT, reminderTypes);

        reminderTypeInput.setAdapter(adapter);
        reminderType = reminderType.isEmpty() ? reminderTypes.get(0) : reminderType;
        reminderTypeInput.setText(reminderType, false);
    }

    private void initReminderDatePickerField(String date) {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder
                .datePicker()
                .setTitleText("Select Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        reminderDateInput = findViewById(DATE_INPUT);

        reminderDateInput.setOnClickListener(view -> {
            Utils.debugLog("click on date picker");
            datePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
        });

        datePicker.addOnPositiveButtonClickListener(selection -> reminderDateInput.setText(datePicker.getHeaderText()));
        reminderDateInput.setText(date);
    }

    private void initToolBar() {
        toolbar = findViewById(TOOL_BAR_ID);
        toolbar.setTitle(ADD_REMINDER_NAME);
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case CLOSE_ID:
                    returnToMainActivity();
                    break;
                case CHECK_ID:
                    saveReminderItem();
            }
            return false;
        });
        toolbar.inflateMenu(MENU_ADD_REMINDER);
    }

    /**
     * Adds a new reminder to DB
     */
    private void saveReminderItem() {
        try {
            String title = Objects.requireNonNull(reminderTitleInput.getText()).toString();
            String tabName = String.valueOf(reminderTypeInput.getText());
            Date date = new Date(String.valueOf(reminderDateInput.getText()));

            if (reminderTypes.contains(tabName)) {
                ReminderManager.saveReminder(id, tabName, title, date);
                Utils.debugLog("DatePicker setDate");

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, date.getYear());
                calendar.set(Calendar.MONTH, date.getMonth());
                calendar.set(Calendar.DAY_OF_MONTH, date.getDay());
                calendar.set(Calendar.HOUR_OF_DAY, 19);
                calendar.set(Calendar.MINUTE, 27);
                calendar.set(Calendar.SECOND, 0);

                startAlarm(calendar, tabName, title);
            }

            returnToMainActivity();
        } catch (Exception e) {
            e.printStackTrace();
            Utils.debugLog("Error when saving item: " + e.getMessage());
        }
    }

    /**
     * Switch window from SaveReminderActivity to MainActivity
     */
    private void returnToMainActivity() {
        Intent intent = new Intent(SaveReminderActivity.this, MainActivity.class);
        startActivityForResult(intent, MAIN_ACTIVITY_CODE);
    }

    private void startAlarm(Calendar calendar, String title, String message){
        Utils.debugLog("DatePicker start Alarm");
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("title", title);
        intent.putExtra("message", message);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        Objects.requireNonNull(alarmManager).setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
    }
}
