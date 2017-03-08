package com.heybotler.botlerapp.helpers;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by toshitpanigrahi on 3/4/17.
 * Change font of a TextView to Open Sans
 */

public class FontChanger {
    public static void changeFont(AssetManager am, TextView tv, String weight) {
        if (weight.equals("bold")) {
            Typeface custom_font = Typeface.createFromAsset(am,  "fonts/OpenSans-Bold.ttf");
            tv.setTypeface(custom_font);
        } else if (weight.equals("light")) {
            Typeface custom_font = Typeface.createFromAsset(am,  "fonts/OpenSans-Light.ttf");
            tv.setTypeface(custom_font);
        } else {
            Typeface custom_font = Typeface.createFromAsset(am,  "fonts/OpenSans-Regular.ttf");
            tv.setTypeface(custom_font);
        }
    }
}
