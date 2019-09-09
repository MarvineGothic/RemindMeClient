package com.qoobico.remindme;

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
import com.qoobico.remindme.adapter.TabsFragmentAdapter;
import com.qoobico.remindme.rest_api.RestAsync;

public class MainActivity extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_main;
    public static TabsFragmentAdapter tabsFragmentAdapter;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;

    private int id = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        initToolBar();
        initNavigationView();
        initFloatingActionButton();
        initTabs();
    }

    private void initToolBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        toolbar.inflateMenu(R.menu.menu);
    }

    private void initNavigationView() {
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.view_navigation_open, R.string.view_navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
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
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RestAsync().sendData("{\"id\":" + id + ",\"title\":\"First reminder" + id + "\",\"remindDate\":1568035941094}");
                id++;
            }
        });
    }

    private void initTabs() {
        viewPager = findViewById(R.id.view_pager);
        tabsFragmentAdapter = new TabsFragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(tabsFragmentAdapter);

        new RestAsync().getData();

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void showNotificationTab() {
        viewPager.setCurrentItem(Constants.HISTORY[0]);
    }

    private void showBookmarksTab() {
        viewPager.setCurrentItem(Constants.TODO[0]);
    }

    private void showHistoryTab() {
        viewPager.setCurrentItem(Constants.BIRTHDAYS[0]);
    }

    private void showSettingsTab() {
        viewPager.setCurrentItem(Constants.IDEAS[0]);
    }

    private void showAboutTab() {
        viewPager.setCurrentItem(Constants.HISTORY[0]);
    }
}
