package com.example.deman_glaisner.deman_glaisner_mobile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DownloadTask extends AsyncTask<String, Void, String> {

    private String webMethod, myURL, postContent, fileType;

    @Override
    protected String doInBackground(String... args) {
        try {

            webMethod = args[0];
            myURL = args[1];
            fileType = args[2];

            System.out.println("args ===> webMethod: " +webMethod);
            System.out.println("args ===> myurl: " +myURL);
            System.out.println("args ===> filetype: " +fileType);

            if(args.length == 4)
                postContent = args[3];

            System.out.println("avant downloadUrl");
            return downloadUrl();


        } catch (IOException e) {
            return "Error while downloading";
        }

    }

    private String downloadUrl() throws IOException {

        System.out.println("On est dans downloadUrl");
        String result = "Erreur";
        String API_KEY = "92bf152a1c366bc032ffce163a0f5d44";
        System.out.println("On y est toujours");
        ArrayList<Integer> idlist = null;


        System.out.println("Debut du try");
        URL url = new URL(myURL);
        System.out.println("URL passée: " + myURL);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        System.out.println("instanciation de la connection");

        conn.setRequestMethod(webMethod);

        if(fileType.equals("JSON")) {
            System.out.println("Filetype est bien equal JSON");
            conn.setRequestProperty("Content-Type", "application/json");
            System.out.println("avant envoi de user key");
            conn.setRequestProperty("user-key", " " + API_KEY);
        }
        conn.connect();

        System.out.println("CONNEXION CONNEXION CONNEXION CONNEXION !!!!");

        InputStream is = conn.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        StringBuilder buffer = new StringBuilder();
        String line = "";
        //String stringsecondaire = "";

        int checker = 0;

        // PUTIN DE MERDE !!

        while((line = reader.readLine()) != null){
            checker++;
            System.out.println("line length = " + line.length() + " and is equal to : " +line);
            System.out.println("bufferisation checker: " + checker +" fois");
            buffer.append(line);
        }


        System.out.println("la boucle de buffurisation a tourné " + checker +" fois");
        is.close();
        result = buffer.toString();
        System.out.println("buffer vaut: " +buffer);
        System.out.println("buffer taille: " +buffer.length());

        /*

        try{

            String webContent = result;
            System.out.println("webcontent vaut:" +webContent);
            JSONObject parentObject = new JSONObject(webContent);
            JSONArray parentArray = parentObject.getJSONArray("nearby_restaurants");

            //JSON MAGIC HERE JSON JSON JSON JSON JSON JSON JSON JSON jSON
            for(int i = 0 ; i < parentArray.length() ; i++) {
                System.out.println("RECUPERATION DES VALEURS JSON");
                JSONObject finalObject = parentArray.getJSONObject(i);
                String restaurantName = finalObject.getJSONObject("restaurant").getString("name");
                System.out.println("Nom restaurant recupere" + restaurantName);
                int id = finalObject.getJSONObject("restaurant").getJSONObject("R").getInt("id");
                System.out.println("restaurad ID recupere" + id);
                System.out.println("Ajout d'une valeur dans restaurantList");
                //restaurantList.add(restaurantName);
                System.out.println("AJOUT d'une valeur dans idList");
                idlist.add(id);
            }

        } catch (JSONException e){}*/






        System.out.println("retour de downloadUrl: " +result);
        return result;
    }
}
