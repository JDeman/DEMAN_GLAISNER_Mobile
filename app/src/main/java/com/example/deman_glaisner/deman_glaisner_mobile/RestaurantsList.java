package com.example.deman_glaisner.deman_glaisner_mobile;

/**
 * Created by julo on 29/10/16.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;



public class RestaurantsList extends AppCompatActivity {


    private List<String> nameList = new ArrayList<>();
    private List<Integer> idList = new ArrayList<>();
    private ListView listView;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_list);
        listView = (ListView) findViewById(R.id.restaurant);
        String webContent = null;
        DownloadTask dlTask = new DownloadTask();

        try {

            dlTask.execute("GET", "https://developers.zomato.com/api/v2.1/search?lat=25.76168&lon=-80.19179&radius=1200", "JSON");
            webContent = dlTask.get();

        } catch (InterruptedException | ExecutionException e) {
        }

        System.out.println("webcontent vaut: " + webContent);
        System.out.println("webcontent taille: " + webContent.length());

        Toast.makeText(getApplicationContext(), "ID premier: ", Toast.LENGTH_SHORT).show();

        // JSON PARSING POUR NAME ET RESTAURANT_ID

        try {

            JSONObject parentObject = new JSONObject(webContent);
            JSONArray parentArray = parentObject.getJSONArray("restaurants");


            //JSON MAGIC HERE JSON JSON JSON JSON JSON JSON JSON JSON jSON
            for (int i = 0; i < parentArray.length(); i++) {
                System.out.println("RECUPERATION DES VALEURS JSON");
                JSONObject finalObject = parentArray.getJSONObject(i);
                String restaurantName = finalObject.getJSONObject("restaurant").getString("name");
                System.out.println("Nom restaurant recupere: " + restaurantName);
                int id = finalObject.getJSONObject("restaurant").getJSONObject("R").getInt("res_id");
                System.out.println("restaurad ID recupere" + id);
                System.out.println("Ajout d'une valeur dans nameList");
                nameList.add(restaurantName);
                System.out.println("AJOUT d'une valeur dans idList");
                idList.add(id);

            }

            for(int i = 0; i < nameList.size(); i++) {
                System.out.println("VALEURS de nameList: " + nameList.get(i));
            }


            for(int i = 0; i < idList.size(); i++) {
                System.out.println("VALEURS DE idList: " + idList.get(i));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        listView = (ListView) findViewById(R.id.restaurant);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                nameList);

        listView.setAdapter(arrayAdapter);


    }
}