package com.heybotler.botlerapp;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_in);

        TextView tv = (TextView) findViewById(R.id.login_title);
        AssetManager am = getApplicationContext().getAssets();
        FontChanger.changeFont(am, tv, "bold");

        tv = (TextView) findViewById(R.id.login_title_text);
        FontChanger.changeFont(am, tv, "regular");
    }

    // Get email and password and make post request to botler
    public void authenticate(View view) {
        EditText et_email = (EditText) findViewById(R.id.login_email);
        EditText et_password = (EditText) findViewById(R.id.login_password);

        String email = et_email.getText().toString();
        String password = et_password.getText().toString();
        String json = UserManagement.getUserInfo(email, password);

        if (json == null) {
            // Error handling, exception happened
            TextView tv = (TextView) findViewById(R.id.login_title_text);
            tv.setText("Whoops! Something went wrong. Please make sure you are connected" +
                    "to the Internet.");
            return;
        }

        boolean isValid = UserManagement.validateJSON(json);

        if (isValid) {
            System.out.println("WOOHOO passed");
            HashMap<String, String> userInfoMap = UserManagement.getUserMap(json);

            // Correct credentials
            TextView tv = (TextView) findViewById(R.id.login_title);
            tv.setText("Hi, " + userInfoMap.get("firstName") + ".");

            tv = (TextView) findViewById(R.id.login_title_text);
            tv.setText("Please give us a moment to set things up.");
        } else {
            // Wrong credentials
            TextView tv = (TextView) findViewById(R.id.login_title_text);
            tv.setText("Wrong username and password. Please try again.");
            return;
        }
    }
}
