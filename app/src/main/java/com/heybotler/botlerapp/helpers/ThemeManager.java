package com.heybotler.botlerapp.helpers;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.heybotler.botlerapp.R;

/**
 * Created by toshitpanigrahi on 3/12/17.
 */

public class ThemeManager {
    public enum Theme {
        BLUE, GRAY, SUIT, RED;

        public static Theme getThemeFromString(String t) {
            switch (t) {
                case "gray":return Theme.GRAY;
                case "red": return Theme.RED;
                case "suit":return Theme.SUIT;
            }
            // default
            return Theme.BLUE;
        }

        public static String getStringFromTheme(Theme t) {
            switch (t) {
                case GRAY:  return "gray";
                case RED:   return "red";
                case SUIT:  return "suit";
            }
            return "blue";
        }
    }

    private AppCompatActivity activity;
    private SharedPreferences userInfo;

    public ThemeManager(AppCompatActivity activity) {
        this.activity = activity;
        userInfo = activity.getSharedPreferences(activity.getResources().getString(R.string.user_info_file), activity.MODE_PRIVATE);
    }

    public void saveTheme(Theme t) {
        SharedPreferences.Editor editor = userInfo.edit();
        editor.putString(activity.getResources().getString(R.string.user_theme), Theme.getStringFromTheme(t));
        editor.commit();
    }

    public Theme getCurrentTheme() {
        String t = userInfo.getString(activity.getString(R.string.user_theme), "blue");
        return Theme.getThemeFromString(t);
    }

    public void setBarTheme(ActionBar appBar) {
        Theme userTheme = getCurrentTheme();
        switch (userTheme) {
            case GRAY:
                appBar.setBackgroundDrawable(activity.getDrawable(R.drawable.app_bar_gray));
                break;
            case RED:
                appBar.setBackgroundDrawable(activity.getDrawable(R.drawable.app_bar_red));
                break;
            case SUIT:
                appBar.setBackgroundDrawable(activity.getDrawable(R.drawable.app_bar_suit));
                break;
            case BLUE:
                appBar.setBackgroundDrawable(activity.getDrawable(R.drawable.app_bar_blue));
                break;
        }
    }
}
