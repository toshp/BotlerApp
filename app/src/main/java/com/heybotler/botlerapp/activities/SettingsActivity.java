package com.heybotler.botlerapp.activities;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;


import com.heybotler.botlerapp.R;
import com.heybotler.botlerapp.fragments.ThemeFragment;
import com.heybotler.botlerapp.helpers.NavDrawerSetup;
import com.heybotler.botlerapp.helpers.ThemeManager;

public class SettingsActivity extends AppCompatActivity {
    private SharedPreferences userInfo;
    private String mActivityTitle = "Theme Settings";
    private NavDrawerSetup nds;
    private ThemeManager themeManager;
    private ThemeManager.Theme currentTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        userInfo = getSharedPreferences(getResources().getString(R.string.user_info_file), MODE_PRIVATE);
        String screen = getIntent().getStringExtra("screen");

        // Find and set toolbar as activity action bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.chat_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setTitle(mActivityTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Setup theme manager
        themeManager = new ThemeManager(this);
        themeManager.setBarTheme(getSupportActionBar());

        // Setup drawer
        nds = new NavDrawerSetup(this, mActivityTitle);
        nds.setupNavDrawer();

        loadFragment(screen);
    }

    // Load the appropriate fragment to fill in settings page
    public void loadFragment(String screen) {
        if (screen.equals("theme")) {
            // Load the themes fragment
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ThemeFragment theme_fragment = new ThemeFragment();
            ft.add(R.id.fragment_area, theme_fragment, "SETTINGS_THEME");
            ft.commit();
        }
    }

    public void setTheme(View v) {
        // Get the tag for the button clicked
        String theme = v.getTag().toString();

        if (theme.equals("blue")) {
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.app_bar_blue, null));
        } else if (theme.equals("gray")) {
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.app_bar_gray, null));
        } else if (theme.equals("red")) {
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.app_bar_red, null));
        } else if (theme.equals("suit")) {
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.app_bar_suit, null));
        } else {
            return;
        }

        saveUserTheme(theme);
    }

    private void saveUserTheme(String theme) {
        themeManager.saveTheme(ThemeManager.Theme.getThemeFromString(theme));
    }


    /**
     * Overriding method to make menu icon clickable
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Activate the navigation drawer toggle
        if (nds.getToggle().onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
