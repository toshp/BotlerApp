package com.heybotler.botlerapp;

import java.net.URLEncoder;

/**
 * Created by toshitpanigrahi on 3/4/17.
 * User management functions, such as account creation,
 * and authentication
 */

public class UserManagement {

    public static boolean validateUser(String email, String password) {
        // Make a separate class to handle post requests
        StringBuilder params = new StringBuilder("email=");
        try {
            params.append(URLEncoder.encode(email, "UTF-8"));
            params.append("&password=");
            params.append(URLEncoder.encode(password, "UTF-8"));

            String response = HttpRequestManager.postWithResponse("https://heybotler.com/php/user_mgmt/json_authenticate.php", params.toString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}