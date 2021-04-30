package com.qoobico.remindme.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.qoobico.remindme.R;
import com.qoobico.remindme.adapter.TabsFragmentAdapter;
import com.qoobico.remindme.rest_api.RestAsync;
import com.qoobico.remindme.utils.ReminderIO;
import com.qoobico.remindme.utils.Utils;

import static com.qoobico.remindme.utils.Constants.*;

public class MainActivity extends AppCompatActivity {

    public static TabsFragmentAdapter tabsFragmentAdapter;
    public static ReminderIO reminderIO;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(DEFAULT_THEME);
        super.onCreate(savedInstanceState);

        setContentView(ACTIVITY_MAIN_LAYOUT);

        reminderIO = new ReminderIO(this, REMINDERS_DIR);

        initToolBar();
        initNavigationView();
        initFloatingActionButton();
        initTabs();
    }

    private void initToolBar() {
        toolbar = findViewById(TOOL_BAR_ID);
        toolbar.setTitle(APP_NAME);
        toolbar.setOnMenuItemClickListener(item -> {
            Utils.debugLog("Clicked menu Item");
            return false;
        });
        toolbar.inflateMenu(MENU);
    }

    private void initNavigationView() {
        drawerLayout = findViewById(DRAWER_LAYOUT);
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, drawerLayout, toolbar, NAV_OPEN, NAV_CLOSE);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(NAVIGATION_ID);
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    drawerLayout.closeDrawers();
                    switch (menuItem.getItemId()) {
                        case R.id.actionNotificationItem:
                            showNotificationTab();
                            break;
                        case R.id.actionBookmarksItem:
                            showBookmarksTab();
                            break;
                        case R.id.actionHistoryItem:
                            showHistoryTab();
                            break;
                        case R.id.actionSettingsItem:
                            showSettingsTab();
                            break;
                        case R.id.actionAboutItem:
                            showAboutTab();
                            break;
                    }
                    return false;
                });
    }

    private void initFloatingActionButton() {
        FloatingActionButton fab = findViewById(FAB_ID);
        fab.setOnClickListener(view -> {
            Utils.debugLog("Open add activity reminder");
            Intent intent = new Intent(MainActivity.this, SaveReminderActivity.class);
            startActivityForResult(intent, ADD_REMINDER_ACTIVITY_CODE);
        });
    }

    private void initTabs() {
        viewPager = findViewById(VIEW_PAGER_ID);
        tabsFragmentAdapter = new TabsFragmentAdapter(MainActivity.this, getSupportFragmentManager()).
                initTabsMap(TABS);
        viewPager.setAdapter(tabsFragmentAdapter);

        if (MOCK) {
            reminderIO.getAllReminders();
        } else {
            new RestAsync().getData();
        }

        TabLayout tabLayout = findViewById(TAB_LAYOUT_ID);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void showNotificationTab() {
        viewPager.setCurrentItem(ALL_REMINDERS[0]);
    }

    private void showBookmarksTab() {
        viewPager.setCurrentItem(TODO[0]);
    }

    private void showHistoryTab() {
        viewPager.setCurrentItem(BIRTHDAYS[0]);
    }

    private void showSettingsTab() {
        viewPager.setCurrentItem(IDEAS[0]);
    }

    private void showAboutTab() {
        viewPager.setCurrentItem(ALL_REMINDERS[0]);
    }
}
