package com.qoobico.remindme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import static com.qoobico.remindme.utils.Constants.*;

public class AddReminderActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextInputLayout titleInputLayout;
    private AutoCompleteTextView reminderTypeInputLayout;

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
    }

    /**
     * Adds a new reminder to DB
     */
    private void addReminderItem() {
        System.out.println("Saving item");
        Editable text = titleInputLayout.getEditText().getText();

        System.out.println(text);
//        String tab_name = (String) tabsFragmentAdapter.getPageTitle(viewPager.getCurrentItem());
//        String item = String.format(Locale.ENGLISH,
//                "{\"id\":\"0\",\"tab_name\":%s,\"title\":\"First reminder\",\"remindDate\":%d}",
//                tab_name, new Date().getTime());
//        new RestAsync().sendData(item);
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
