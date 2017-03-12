package com.heybotler.botlerapp.helpers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.heybotler.botlerapp.R;
import com.heybotler.botlerapp.activities.SettingsActivity;

/**
 * This cclass is used to populate the nav drawer on a View
 * Created by toshitpanigrahi on 3/11/17.
 */

public class NavDrawerSetup {
    private final AppCompatActivity activity;
    private final String title;
    private final SharedPreferences userInfo;
    private final DrawerLayout layout;
    private final ListView drawerList;
    private ActionBarDrawerToggle toggle;

    public NavDrawerSetup(AppCompatActivity activity, String title) {
        this.activity = activity;
        userInfo = activity.getSharedPreferences(activity.getResources().getString(R.string.user_info_file), activity.MODE_PRIVATE);
        layout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        drawerList = (ListView) activity.findViewById(R.id.nav_list);
        this.title = title;
    }

    public void setupNavDrawer() {
        // Inflate the header and footer to put in drawer listview
        View navHeader = LayoutInflater.from(activity).inflate(R.layout.nav_heading, null);
        TextView navName = (TextView) navHeader.findViewById(R.id.nav_name);
        navName.setText(userInfo.getString("firstName", "No") + " " + userInfo.getString("lastName", "Name"));
        FontChanger.changeFont(activity.getAssets(), navName, "bold");
        View navSignOut = LayoutInflater.from(activity).inflate(R.layout.nav_sign_out, null);

        drawerList.addHeaderView(navHeader);
        drawerList.addFooterView(navSignOut);

        addDrawerItems();
        setupDrawer();

        drawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    private void addDrawerItems() {
        String[] osArray = {"Today's Messages", "Themes", "Recent Bots"};
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(activity, R.layout.nav_opt_layout, R.id.nav_opt_text, osArray);
        drawerList.setAdapter(mAdapter);
    }


    private void setupDrawer() {
        toggle = new ActionBarDrawerToggle(activity, layout, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                activity.getSupportActionBar().setTitle("Navigation");
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                activity.getSupportActionBar().setTitle(title);
            }
        };

        toggle.setDrawerIndicatorEnabled(true);
        layout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Closes drawer, passes the selection to changeActivity */
    private void selectItem(int position) {
        // Highlight the selected item, update the title, and close the drawer
        drawerList.setItemChecked(position, true);
        layout.closeDrawer(drawerList);
        changeActivity(position);
    }

    private void changeActivity(int position) {
        if (position == 2) {
            // Launch themes
            Intent next = new Intent(activity, SettingsActivity.class);
            next.putExtra("screen", "theme");
            activity.startActivity(next);
        } else if (position == 3) {
            // Recent bots
            Intent next = new Intent(activity, SettingsActivity.class);
            next.putExtra("screen", "history");
            activity.startActivity(next);
        } else if (position == 4) {
            // Sign out
        }
    }

    public ActionBarDrawerToggle getToggle() {
        return toggle;
    }
}
