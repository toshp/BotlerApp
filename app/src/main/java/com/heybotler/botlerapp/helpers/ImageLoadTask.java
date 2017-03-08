package com.heybotler.botlerapp.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by toshitpanigrahi on 3/7/17.
 * SRC http://stackoverflow.com/questions/18953632/how-to-set-image-from-url-for-imageview
 * MODIFIED to implement a basic HashMap bitmap cache
 */

public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {
    // Static cache for icons
    private static final HashMap<String, Bitmap> iconCache = new HashMap<String, Bitmap>();

    private String url;
    private ImageView imageView;

    public ImageLoadTask(String url, ImageView imageView) {
        this.url = url;
        this.imageView = imageView;
    }

    /**
     * Checks the iconCache before execution
     * and cancels if hit
     */
    @Override
    protected void onPreExecute() {
        Bitmap icon = iconCache.get(url);
        if (icon != null) {
            imageView.setImageBitmap(icon);
            cancel(true);
        }
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            URL urlConnection = new URL(url);
            HttpURLConnection connection =
                    (HttpURLConnection) urlConnection.openConnection();
            System.out.println("GETTING IMAGE FROM " + url);
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        // Cache icon for future
        iconCache.put(url, result);
        // Set imageView
        imageView.setImageBitmap(result);
    }

}