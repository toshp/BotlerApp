package com.heybotler.botlerapp.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.heybotler.botlerapp.R;
import com.heybotler.botlerapp.helpers.FontChanger;
import com.heybotler.botlerapp.helpers.MessageAdapter;
import com.heybotler.botlerapp.helpers.NavDrawerSetup;
import com.heybotler.botlerapp.helpers.ThemeManager;
import com.heybotler.botlerapp.helpers.UserManagement;
import com.heybotler.botlerapp.models.Message;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private MessageAdapter messageAdapter;
    private SharedPreferences userInfo;
    private NavDrawerSetup nds;
    private ThemeManager themeManager;

    /**
     * 1) Set app bar title
     * 2) Set up drawer
     * 3) Set up chat history date
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        userInfo = this.getSharedPreferences(getResources().getString(R.string.user_info_file), this.MODE_PRIVATE);

        // Top bar setup
        Toolbar myToolbar = (Toolbar) findViewById(R.id.chat_toolbar);
        setSupportActionBar(myToolbar);

        nds = new NavDrawerSetup(this, "Today's Messages");
        nds.setupNavDrawer();

        getSupportActionBar().setTitle("Today's Messages");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Setup theme manager
        themeManager = new ThemeManager(this);

        ListView messageList = (ListView) findViewById(R.id.chat_history);
        View chatHeader = LayoutInflater.from(getApplicationContext()).inflate(R.layout.chat_heading, null);

        // Change font of chat header
        TextView headTitle = (TextView) chatHeader.findViewById(R.id.chat_day);
        FontChanger.changeFont(getAssets(), headTitle, "bold");
        headTitle = (TextView) chatHeader.findViewById(R.id.chat_date);
        FontChanger.changeFont(getAssets(), headTitle, "regular");
        messageList.addHeaderView(chatHeader);
    }

    // Every time activity resumes get messages
    @Override
    protected void onResume() {
        super.onResume();
        String userID = userInfo.getString(getResources().getString(R.string.user_id), "whoops");
        // Set theme onResume in case changed
        themeManager.setBarTheme(getSupportActionBar());
        // Get messages in ArrayList
        ArrayList<Message> messages = UserManagement.getUserMessages(userID);
        // Instantiate adapter
        messageAdapter = new MessageAdapter(getApplicationContext(), messages);
        ListView messageList = (ListView) findViewById(R.id.chat_history);
        // Bind to ListView
        messageList.setAdapter(messageAdapter);
    }

    /**
     * Called from send click
     * Outgoing Message enqueued in MessageAdapter
     * Response retrieved and enqueued
     * @param v
     */
    public void sendMessage(View v) {
        EditText newMessage = (EditText) findViewById(R.id.message_entry);
        String messageText = newMessage.getText().toString();
        messageAdapter.add(
                new Message(
                        messageText,
                        "11:02 PM",
                        userInfo.getString(getResources().getString(R.string.first_name), null)
                                + userInfo.getString(getResources().getString(R.string.last_name), null),
                        "https://heybotler.com/images/user-icon.png"
                )
        );

        messageAdapter.add(UserManagement.getMessageResponse(
                userInfo.getString(getResources().getString(R.string.email), "whoops"),
                messageText));
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
