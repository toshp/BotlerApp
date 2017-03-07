package com.heybotler.botlerapp.helpers;

import com.heybotler.botlerapp.models.Message;

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

    // Stub for now
    public static ArrayList<Message> getUserMessages(String userID) {
        ArrayList<Message> messages = new ArrayList<Message>();
        Message m1 = new Message("Hello world!", "10:00 AM", "You", "https://heybotler.com/images/user_icon.png");
        Message m2 = new Message("Hi again", "10:05 AM", "You", "https://heybotler.com/images/user_icon.png");
        messages.add(m1);
        messages.add(m2);
        return messages;
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
