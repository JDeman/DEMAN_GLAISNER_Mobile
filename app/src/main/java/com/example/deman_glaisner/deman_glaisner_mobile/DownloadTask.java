package com.example.deman_glaisner.deman_glaisner_mobile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Void, String> {

    private String webMethod, myURL, postContent, fileType;

    @Override
    protected String doInBackground(String... args) {
        try {

            webMethod = args[0];
            myURL = args[1];
            fileType = args[2];

            if(args.length == 4)
                postContent = args[3];

            return downloadUrl();

        } catch (IOException e) {
            return "Error while downloading";
        }

    }

    private String downloadUrl() throws IOException {

        InputStream is = null;
        String result = "Erreur";
        String API_KEY = "92bf152a1c366bc032ffce163a0f5d44";

        try {

            URL url = new URL(myURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(webMethod);

            if(fileType.equals("JSON"))
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("user-key", " "+API_KEY);
            conn.connect();

            if(webMethod.equals("POST")) {
                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());

                wr.writeBytes(postContent);
                wr.flush();
                wr.close();
            }

            is = conn.getInputStream();

            if(fileType.equals("IMG")) {
                Bitmap bitmapBeer;
                String stringBeer;
                byte[] byteBeer;
                ByteArrayOutputStream byteOS;
                bitmapBeer = BitmapFactory.decodeStream(is);
                byteOS = new ByteArrayOutputStream();
                bitmapBeer.compress(Bitmap.CompressFormat.PNG, 100, byteOS);
                byteBeer = byteOS.toByteArray();
                stringBeer = Base64.encodeToString(byteBeer, Base64.DEFAULT);

                result = stringBeer;
            }

            else if(fileType.equals("TXT") || fileType.equals("JSON"))
                result = readText(is, 1000);

            else if(fileType.equals("BTXT"))
                result = readText(is, 20000);
        }

        finally {
            if (is != null)  {
                is.close();
            }
        }

        return result;
    }

    private String readText(InputStream stream, int len) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"), len);
        String string = br.readLine();

        return string;
    }
}
