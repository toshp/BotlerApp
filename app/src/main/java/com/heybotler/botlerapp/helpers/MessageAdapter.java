package com.heybotler.botlerapp.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.heybotler.botlerapp.R;
import com.heybotler.botlerapp.models.Message;

import java.util.ArrayList;

/**
 * Created by toshitpanigrahi on 3/6/17.
 */

public class MessageAdapter extends ArrayAdapter<Message> {
    public MessageAdapter(Context context, ArrayList<Message> messages) {
        super(context, 0, messages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Message at position
        Message m = getItem(position);
        // Check if view null
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_layout, parent, false);
        }

        // Populate fields
        TextView senderText = (TextView) convertView.findViewById(R.id.message_sender);
        TextView messageText = (TextView) convertView.findViewById(R.id.message_text);

        senderText.setText(m.getSender());
        messageText.setText(m.getText());

        return convertView;
    }
}
