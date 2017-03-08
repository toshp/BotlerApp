package com.heybotler.botlerapp.helpers;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.heybotler.botlerapp.R;
import com.heybotler.botlerapp.models.Message;

import java.util.ArrayList;

/**
 * Created by toshitpanigrahi on 3/6/17.
 * Adapter binds message list to format xml
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
        TextView messageTime = (TextView) convertView.findViewById(R.id.message_time);

        senderText.setText(m.getSender());
        messageText.setText(m.getText());
        messageTime.setText(m.getTime());

        AssetManager am = getContext().getAssets();
        FontChanger.changeFont(am, senderText, "bold");
        FontChanger.changeFont(am, messageText, "regular");

        // Finally load the image
        ImageView icon = (ImageView) convertView.findViewById(R.id.sender_icon);
        ImageLoadTask imgLoad = new ImageLoadTask(m.getPicSrc(), icon);
        imgLoad.execute();

        return convertView;
    }
}
