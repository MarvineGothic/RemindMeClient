package com.qoobico.remindme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.qoobico.remindme.rest_api.RestAsync;
import com.qoobico.remindme.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.qoobico.remindme.activity.MainActivity.reminderIO;
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

        id = "0";
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
        List<String> items = new ArrayList<>();
        for (int i = 1; i < TABS.length; i++) {
            items.add(this.getString(TABS[i][2]));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, REMINDER_TYPE_ITEM_LAYOUT, items);

        reminderTypeInput.setAdapter(adapter);
        reminderType = reminderType.isEmpty() ? items.get(0) : reminderType;
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
        Utils.debugLog("Saving item");
        try {
            String title = Objects.requireNonNull(reminderTitleInput.getText()).toString();
            String tabName = String.valueOf(reminderTypeInput.getText());
            Date date = new Date(String.valueOf(reminderDateInput.getText()));

            if (MOCK) {
                if (id != null) {
                    reminderIO.patchReminder(id, tabName, title, date);
                } else {
                    reminderIO.putReminder(tabName, title, date);
                }
            } else {
                String item = String.format(Locale.ENGLISH,
                        "{\"id\":\"0\",\"tab_name\":%s,\"title\":%s,\"remindDate\":%s}",
                        tabName, title, date.getTime());
                new RestAsync().sendData(item);
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
}
