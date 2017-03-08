package com.heybotler.botlerapp.helpers;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 * Created by toshitpanigrahi on 3/5/17.
 *
 * Only the static method getWithResponse() should
 * be called. It will make new HRM and will handle
 * the request from the url
 */

public class HttpRequestManager {
    private class GetRequestor extends AsyncTask<String, Void, String> {
        @Override
        public String doInBackground(String... url) {
            try {
                URL endpoint = new URL(url[0]);
                HttpsURLConnection con = (HttpsURLConnection) endpoint.openConnection();

                con.setRequestMethod("GET");

                System.out.println("\nSending request to URL : " + url[0]);
                System.out.println("Response Code : " + con.getResponseCode());
                System.out.println("Response Message : " + con.getResponseMessage());

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();
                con.disconnect();

                return response.toString();
            } catch (java.io.IOException e) {
                System.out.println("HERE: " + e);
                return null;
            }
        }

    }

    /**
     * Makes a get request to a given url
     * @param url
     * @return String response
     */
    public static String getWithResponse(String url) {
        HttpRequestManager hrm = new HttpRequestManager();
        try {
            String r = hrm.new GetRequestor().execute(url).get();
            return r;
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            return null;
        }
    }
}
