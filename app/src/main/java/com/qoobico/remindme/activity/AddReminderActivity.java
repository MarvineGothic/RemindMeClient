package com.qoobico.remindme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.qoobico.remindme.adapter.TabsFragmentAdapter;
import com.qoobico.remindme.rest_api.RestAsync;

import java.util.Date;
import java.util.Locale;

import static com.qoobico.remindme.utils.Constants.ACTIVITY_ADD_REMINDER_LAYOUT;
import static com.qoobico.remindme.utils.Constants.ADD_REMINDER_NAME;
import static com.qoobico.remindme.utils.Constants.BIRTHDAYS;
import static com.qoobico.remindme.utils.Constants.CHECK_ID;
import static com.qoobico.remindme.utils.Constants.CLOSE_ID;
import static com.qoobico.remindme.utils.Constants.DEFAULT_STYLE;
import static com.qoobico.remindme.utils.Constants.IDEAS;
import static com.qoobico.remindme.utils.Constants.MAIN_ACTIVITY_CODE;
import static com.qoobico.remindme.utils.Constants.MENU_ADD_REMINDER;
import static com.qoobico.remindme.utils.Constants.TAB_LAYOUT_ID;
import static com.qoobico.remindme.utils.Constants.TODO;
import static com.qoobico.remindme.utils.Constants.TOOL_BAR_ID;
import static com.qoobico.remindme.utils.Constants.VIEW_PAGER_ID;

public class AddReminderActivity extends AppCompatActivity {
    public static TabsFragmentAdapter tabsFragmentAdapter;
    private Toolbar toolbar;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(DEFAULT_STYLE);
        super.onCreate(savedInstanceState);

        setContentView(ACTIVITY_ADD_REMINDER_LAYOUT);

        initToolBar();

        initTabs();
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

    /**
     * Adds a new reminder to DB
     */
    private void addReminderItem() {
        String tab_name = (String) tabsFragmentAdapter.getPageTitle(viewPager.getCurrentItem());
        String item = String.format(Locale.ENGLISH,
                "{\"id\":\"0\",\"tab_name\":%s,\"title\":\"First reminder\",\"remindDate\":%d}",
                tab_name, new Date().getTime());
        new RestAsync().sendData(item);
        returnToMainActivity();
    }

    /**
     * Switch window from AddReminderActivity to MainActivity
     */
    private void returnToMainActivity() {
        Intent intent = new Intent(AddReminderActivity.this, MainActivity.class);
        startActivityForResult(intent, MAIN_ACTIVITY_CODE);
    }

    private void initTabs() {
        viewPager = findViewById(VIEW_PAGER_ID);
        tabsFragmentAdapter = new TabsFragmentAdapter(this, getSupportFragmentManager()).
                initTabsMap(IDEAS, TODO, BIRTHDAYS);
        viewPager.setAdapter(tabsFragmentAdapter);

        new RestAsync().getData();

        TabLayout tabLayout = findViewById(TAB_LAYOUT_ID);
        tabLayout.setupWithViewPager(viewPager);

    }
}
