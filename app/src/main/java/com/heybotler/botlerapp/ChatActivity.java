package com.heybotler.botlerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.chat_toolbar);
        setSupportActionBar(myToolbar);
    }
}
