package com.heybotler.botlerapp.helpers;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.heybotler.botlerapp.R;

/**
 * Created by toshitpanigrahi on 3/12/17.
 */

public class ThemeManager {
    public enum Theme {
        BLUE, GRAY, SUIT, RED
    }

    private AppCompatActivity activity;
    private SharedPreferences userInfo;

    public ThemeManager(AppCompatActivity activity) {
        this.activity = activity;
        userInfo = activity.getSharedPreferences(activity.getResources().getString(R.string.user_info_file), activity.MODE_PRIVATE);
    }

    public void saveTheme(Theme t) {

    }

    public Theme getCurrentTheme() {
        String t = userInfo.getString(activity.getString(R.string.user_theme), "blue");
        switch (t) {
            case "gray":
                return Theme.GRAY;
            case "red":
                return Theme.RED;
            case "suit":
                return Theme.SUIT;
        }
        // default
        return Theme.BLUE;
    }
}
