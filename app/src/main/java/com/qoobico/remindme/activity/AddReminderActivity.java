package com.qoobico.remindme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.qoobico.remindme.rest_api.RestAsync;
import com.qoobico.remindme.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.qoobico.remindme.activity.MainActivity.reminderIO;
import static com.qoobico.remindme.utils.Constants.*;

public class AddReminderActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextInputLayout titleInputLayout;
    private AutoCompleteTextView reminderTypeInputLayout;
    private String tab_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(REMINDER_FORM_THEME);
        super.onCreate(savedInstanceState);

        setContentView(ACTIVITY_ADD_REMINDER_LAYOUT);

        initReminderForm();
        initToolBar();
    }

    private void initToolBar() {
        toolbar = findViewById(TOOL_BAR_ID);
        toolbar.setTitle(ADD_REMINDER_NAME);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case CLOSE_ID:
                        returnToMainActivity();
                        break;
                    case CHECK_ID:
                        addReminderItem();
                }
                return false;
            }
        });
        toolbar.inflateMenu(MENU_ADD_REMINDER);
    }

    private void initReminderForm() {
        titleInputLayout = findViewById(TITLE_INPUT);
        reminderTypeInputLayout = findViewById(REMINDER_TYPE_INPUT);
        List<String> items = new ArrayList<>();
        for (int i = 1; i < TABS.length; i++) {
            items.add(this.getString(TABS[i][2]));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, REMINDER_TYPE_ITEM_LAYOUT, items);
        reminderTypeInputLayout.setAdapter(adapter);
        reminderTypeInputLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tab_name = adapterView.getItemAtPosition(i).toString();
                Utils.debugLog(tab_name);
            }
        });
    }

    /**
     * Adds a new reminder to DB
     */
    private void addReminderItem() {
        Utils.debugLog("Saving item");
        Editable text = titleInputLayout.getEditText().getText();

        Utils.debugLog("Item: " + text);
        if (MOCK) {
            reminderIO.putReminder(tab_name, text.toString(), new Date());
        } else {
            String item = String.format(Locale.ENGLISH,
                    "{\"id\":\"0\",\"tab_name\":%s,\"title\":\"First reminder\",\"remindDate\":%s}",
                    "IDEAS", new Date());
            new RestAsync().sendData(item);
        }
//        String tab_name = (String) tabsFragmentAdapter.getPageTitle(viewPager.getCurrentItem());
        returnToMainActivity();
    }

    /**
     * Switch window from AddReminderActivity to MainActivity
     */
    private void returnToMainActivity() {
        Intent intent = new Intent(AddReminderActivity.this, MainActivity.class);
        startActivityForResult(intent, MAIN_ACTIVITY_CODE);
    }
}
