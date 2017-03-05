package com.heybotler.botlerapp;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 * Created by toshitpanigrahi on 3/5/17.
 */

public class HttpGetRequest extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... url) {

        try {
            URL endpoint = new URL(url[0]);
            HttpsURLConnection con = (HttpsURLConnection) endpoint.openConnection();
            con.setRequestMethod("GET");

            System.out.println("\nSending request to URL : " + url);
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
        } catch (Exception e) {
            System.out.println("FAILED " + e);
            return null;
        }
    }
}
