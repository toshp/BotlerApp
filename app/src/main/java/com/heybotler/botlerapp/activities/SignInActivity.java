package com.heybotler.botlerapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.heybotler.botlerapp.helpers.FontChanger;
import com.heybotler.botlerapp.R;
import com.heybotler.botlerapp.helpers.UserManagement;

import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {
    /**
     * Decide if user has already signed in,
     * else show sign in page
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        SharedPreferences userInfo = getApplicationContext().getSharedPreferences(
                getResources().getString(R.string.user_info_file), MODE_PRIVATE);

        if (userInfo != null) {
            // Check if user has signed in already
            if (userInfo.getString(getResources().getString(R.string.user_id), null) != null) {
                System.out.println("User already signed in");
                // Next activity
                Intent next = new Intent(this, ChatActivity.class);
                startActivity(next);
                return;
            }
        }

        TextView tv = (TextView) findViewById(R.id.login_title);
        AssetManager am = getApplicationContext().getAssets();
        FontChanger.changeFont(am, tv, "bold");

        tv = (TextView) findViewById(R.id.login_title_text);
        FontChanger.changeFont(am, tv, "regular");
    }

    /**
     * Called from sign in button.
     * @param view
     */
    public void authenticate(View view) {
        EditText et_email = (EditText) findViewById(R.id.login_email);
        EditText et_password = (EditText) findViewById(R.id.login_password);

        String email = et_email.getText().toString();
        String password = et_password.getText().toString();
        String json = UserManagement.getUserInfo(email, password);

        if (json == null) {
            // Error handling, exception happened
            TextView tv = (TextView) findViewById(R.id.login_title_text);
            tv.setText(getString(R.string.login_internet_error));
            return;
        }

        // Check if valid json object
        boolean isValid = UserManagement.validateJSON(json);

        if (isValid) {
            HashMap<String, String> userInfoMap = UserManagement.getUserMap(json);

            // Correct credentials
            TextView tv = (TextView) findViewById(R.id.login_title);
            tv.setText(getString(R.string.login_success_title) + userInfoMap.get("firstName") + ".");

            tv = (TextView) findViewById(R.id.login_title_text);
            tv.setText(getString(R.string.login_success_text));

            SharedPreferences userInfo = this.getSharedPreferences(getResources().getString(R.string.user_info_file), this.MODE_PRIVATE);
            SharedPreferences.Editor editor = userInfo.edit();

            editor.putString(getString(R.string.first_name), userInfoMap.get("firstName"));
            editor.putString(getString(R.string.last_name), userInfoMap.get("lastName"));
            editor.putString(getString(R.string.email), userInfoMap.get("email"));
            editor.putString(getString(R.string.user_id), userInfoMap.get("userID"));
            editor.commit();

            System.out.println("SIA UID: " + userInfo.getString("userID", "noooo"));

            Intent next = new Intent(this, ChatActivity.class);
            startActivity(next);
        } else {
            // Wrong credentials
            TextView tv = (TextView) findViewById(R.id.login_title_text);
            tv.setText(getString(R.string.login_failure_text));
            return;
        }
    }
}
