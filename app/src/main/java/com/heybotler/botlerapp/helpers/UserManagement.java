package com.heybotler.botlerapp.helpers;

import com.heybotler.botlerapp.models.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by toshitpanigrahi on 3/4/17.
 * User management functions, such as account creation,
 * and authentication
 */

public class UserManagement {
    public static String getUserInfo(String email, String password) {
        // Make a separate class to handle post requests
        StringBuilder params = new StringBuilder("email=");
        try {
            params.append(URLEncoder.encode(email, "UTF-8"));
            params.append("&password=");
            // Hash password before sending. Not for production.
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            password = bytesToHexString(hash);
            params.append(URLEncoder.encode(password, "UTF-8"));

            String json = HttpRequestManager.getWithResponse("https://heybotler.com/php/user_mgmt/json_authenticate.php?" + params.toString());

            return json;
        } catch (Exception e) {
            return null;
        }
    }

    public static ArrayList<Message> getUserMessages(String userID) {
        ArrayList<Message> messages = new ArrayList<Message>();
        StringBuilder params = new StringBuilder("user_id=");
        try {
            params.append(URLEncoder.encode(userID, "UTF-8"));
            String jsonMessages = HttpRequestManager.getWithResponse("https://heybotler.com/php/user_mgmt/json_messages.php?" + params.toString());
            // Now make json object
            JSONObject jsonObject = new JSONObject(jsonMessages);
            // Get the Json array
            JSONArray jsonArray = jsonObject.getJSONArray("messages");
            // Traverse and instantiate Messages
            for (int i = 0; i < jsonArray.length(); i++) {
                String sender = jsonArray.getJSONObject(i).getString("sender");
                String time = jsonArray.getJSONObject(i).getString("time");
                String icon = jsonArray.getJSONObject(i).getString("icon");
                String text = jsonArray.getJSONObject(i).getString("text");
                Message m = new Message(text, time, sender, icon);
                messages.add(m);
            }
            return messages;
        } catch (Exception e) {
            return null;
        }
    }

    public static Message getMessageResponse(String email, String text) {
        StringBuilder params = new StringBuilder("key=");
        try {
            params.append(URLEncoder.encode("7e94eb7de155900e30824ce27165e99f", "UTF-8"));
            params.append("&message=");
            params.append(URLEncoder.encode(text, "UTF-8"));
            params.append("&identity_type=email&identifier=");
            params.append(URLEncoder.encode(email, "UTF-8"));

            String json = HttpRequestManager.getWithResponse("https://heybotler.com/listener/?" + params.toString());

            JSONObject jsonObject = new JSONObject(json);
            JSONObject botDetails = jsonObject.getJSONObject("bot_details");

            String response = jsonObject.getString("response_string");
            String timestamp = jsonObject.getString("timestamp");
            String botName = botDetails.getString("bot_name");
            String botIcon = "https://heybotler.com/" + botDetails.getString("bot_icon");

            return new Message(response, timestamp, botName, botIcon);
        } catch (Exception e) {
            return new Message("Whoops, there was an error sending your message.", "10:00AM", "Error", "https://heybotler.com/images/build-icon.png");
        }
    }

    public static boolean validateJSON(String str) {
        try {
            JSONObject json = new JSONObject(str);
            String validity = json.getString("valid");
            return validity.equals("true");
        } catch (JSONException e) {
            return false;
        }
    }

    public static HashMap<String, String> getUserMap(String str) {
        try {
            HashMap<String, String> map = new HashMap<String, String>();
            JSONObject json = new JSONObject(str);
            map.put("firstName", json.getString("first_name"));
            map.put("lastName", json.getString("last_name"));
            map.put("userID", json.getString("user_id"));
            map.put("email", json.getString("email"));
            return map;
        } catch (Exception e) {
            return null;
        }
    }

    // Helper function for hash.
    private static String bytesToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
