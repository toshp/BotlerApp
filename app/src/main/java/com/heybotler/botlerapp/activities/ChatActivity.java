package com.heybotler.botlerapp.activities;

import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.heybotler.botlerapp.R;
import com.heybotler.botlerapp.helpers.FontChanger;
import com.heybotler.botlerapp.helpers.MessageAdapter;
import com.heybotler.botlerapp.helpers.UserManagement;
import com.heybotler.botlerapp.models.Message;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private MessageAdapter messageAdapter;
    private SharedPreferences userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.chat_toolbar);
        setSupportActionBar(myToolbar);

        mDrawerList = (ListView) findViewById(R.id.nav_list);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = "Today's Messages";

        addDrawerItems();

        getSupportActionBar().setTitle("Today's Messages");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setupDrawer();
        userInfo = this.getSharedPreferences(getResources().getString(R.string.user_info_file), this.MODE_PRIVATE);

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
        // Get messages in ArrayList
        ArrayList<Message> messages = UserManagement.getUserMessages(userID);
        // Instantiate adapter
        messageAdapter = new MessageAdapter(getApplicationContext(), messages);
        ListView messageList = (ListView) findViewById(R.id.chat_history);
        // Bind to ListView
        messageList.setAdapter(messageAdapter);
    }

    private void addDrawerItems() {
        String[] osArray = { "Android", "iOS", "Windows", "OS X", "Linux" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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
}
