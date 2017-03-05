package com.heybotler.botlerapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 * Created by toshitpanigrahi on 3/5/17.
 */

public class HttpRequestManager {
    public static String postWithResponse(String url, String params) throws java.io.IOException {
        URL endpoint = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) endpoint.openConnection();

        try {
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept-Language", "UTF-8");
            con.setDoOutput(true);

            OutputStreamWriter outWriter = new OutputStreamWriter(con.getOutputStream());
            outWriter.write(params);
            outWriter.flush();

            int responseCode = con.getResponseCode();
            System.out.println("Sending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + params);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println(response.toString());
            return response.toString();
        } catch (java.io.IOException e) {
            return null;
        } finally {
            con.disconnect();
        }
    }

    public static String getWithResponse(String url) throws java.io.IOException {
        URL endpoint = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) endpoint.openConnection();

        try {
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
            return response.toString();
        } catch (java.io.IOException e) {
            return null;
        } finally {
            con.disconnect();
        }
    }
}
