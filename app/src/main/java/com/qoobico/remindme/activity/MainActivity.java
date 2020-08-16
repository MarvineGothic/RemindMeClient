package com.qoobico.remindme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
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

import static com.qoobico.remindme.utils.Constants.*;

public class MainActivity extends AppCompatActivity {

    public static TabsFragmentAdapter tabsFragmentAdapter;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;

    private int id = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(DEFAULT_STYLE);
        super.onCreate(savedInstanceState);

        setContentView(ACTIVITY_MAIN_LAYOUT);

        initToolBar();
        initNavigationView();
        initFloatingActionButton();
        initTabs();
    }

    private void initToolBar() {
        toolbar = findViewById(TOOL_BAR_ID);
        toolbar.setTitle(APP_NAME);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
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
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
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
                    }
                });
    }

    private void initFloatingActionButton() {
        FloatingActionButton fab = findViewById(FAB_ID);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*new RestAsync().sendData("{\"id\":" + id + ",\"title\":\"First reminder" + id + "\",\"remindDate\":1568035941094}");
                id++;*/
                Intent intent = new Intent(MainActivity.this, AddReminderActivity.class);
                startActivityForResult(intent, ADD_REMINDER_ACTIVITY_CODE);
            }
        });
    }

    private void initTabs() {
        viewPager = findViewById(VIEW_PAGER_ID);
        tabsFragmentAdapter = new TabsFragmentAdapter(this, getSupportFragmentManager()).
                initTabsMap(ALL_REMINDERS, IDEAS, TODO, BIRTHDAYS);
        viewPager.setAdapter(tabsFragmentAdapter);

        new RestAsync().getData();

        TabLayout tabLayout = findViewById(TAB_LAYOUT_ID);
        tabLayout.setupWithViewPager(viewPager);

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
