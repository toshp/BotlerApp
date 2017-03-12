package com.heybotler.botlerapp.helpers;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by toshitpanigrahi on 3/12/17.
 */

public class ThemeManager {
    enum Theme {
        BLUE, GRAY, SUIT, RED
    }

    private AppCompatActivity activity;

    public ThemeManager(AppCompatActivity activity) {
        this.activity = activity;
    }

    private void saveTheme(Theme t) {
        
    }
}
