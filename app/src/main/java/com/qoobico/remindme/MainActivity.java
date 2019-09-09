package com.qoobico.remindme;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.qoobico.remindme.adapter.TabsFragmentAdapter;
import com.qoobico.remindme.dto.RemindDTO;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_main;
    private Toolbar toolbar;
    private static TabsFragmentAdapter adapter;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        initToolBar();
        initNavigationView();
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

    private void initTabs() {
        viewPager = findViewById(R.id.view_pager);
        adapter = new TabsFragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        new RemindMeTask().execute();

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

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
                }
                return false;
            }
        });
    }

    private void showNotificationTab() {
        viewPager.setCurrentItem(Constants.TAB_TWO);
    }

    private static class RemindMeTask extends AsyncTask<Void, Void, List<RemindDTO>> {
        @Override
        protected List<RemindDTO> doInBackground(Void... voids) {
            List<RemindDTO> remindDTOS = new ArrayList<>();
            HttpJsonParser parser = new HttpJsonParser();
            JSONArray response = parser.makeHttpRequest(Constants.URL.GET_REMIND_ITEM, "GET", null);

            try {
                for (int i = 0; i < response.length(); i++) {
                    remindDTOS.add(new RemindDTO(response.getJSONObject(i)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return remindDTOS;
        }

        @Override
        protected void onPostExecute(List<RemindDTO> remindDTOS) {
            adapter.setData(remindDTOS);
        }

        /*@Override
        protected RemindDTO doInBackground(Void... voids) {
            RestTemplate template = new RestTemplate();
            template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return template.getForObject(Constants.URL.GET_REMIND_ITEM, RemindDTO.class);
        }

        @Override
        protected void onPostExecute(RemindDTO remindDTO) {
            List<RemindDTO> list = new ArrayList<>();
            list.add(remindDTO);
            adapter.setData(list);
        }*/
    }
}
