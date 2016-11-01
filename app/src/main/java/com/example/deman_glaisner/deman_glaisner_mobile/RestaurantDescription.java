package com.example.deman_glaisner.deman_glaisner_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.concurrent.ExecutionException;

/**
 * Created by julo on 31/10/16.
 */


public class RestaurantDescription extends AppCompatActivity {
    String nameString, locationString, latitudeString, longitudeString, cuisineString, costString, ratingString, votesString;
    String webContent2 = null;
    TextView name, location, latitude, longitude,cuisine, cost, rating, votes, nonoo;

    private boolean comesFromAuth = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.restaurant_description);

        DownloadTask dlTask2 = new DownloadTask();



        name = (TextView) findViewById(R.id.name);
        location = (TextView) findViewById(R.id.location);
        cuisine= (TextView) findViewById(R.id.cuisine);
        cost = (TextView) findViewById(R.id.cost);
        rating = (TextView) findViewById(R.id.note);
        votes = (TextView) findViewById(R.id.vote);


        Intent intent = getIntent();
        int IDrecupere = intent.getExtras().getInt("restaurantID");
        String IDurl = String.valueOf(IDrecupere);
        Toast.makeText(getApplicationContext(), "ID du resto: " + IDrecupere, Toast.LENGTH_SHORT).show();


        try {

            dlTask2.execute("GET", "https://developers.zomato.com/api/v2.1/restaurant?res_id=" +IDurl, "JSON", "2");
            webContent2 = dlTask2.get();

        } catch (InterruptedException | ExecutionException e) {
        }

        System.out.println("webcontent vaut: " + webContent2);
        System.out.println("webcontent taille: " + webContent2.length());

        Toast.makeText(getApplicationContext(), "ID premier: ", Toast.LENGTH_SHORT).show();

        // JSON PARSING POUR NAME ET RESTAURANT_ID

        try {

            JSONObject parentObject = new JSONObject(webContent2);

            nameString = parentObject.getString("name");
            System.out.println("Nom restaurant recupere: " + nameString);
            locationString = parentObject.getJSONObject("location").getString("address");
            cuisineString = parentObject.getString("cuisines");
            System.out.println("type de cuisine" + cuisineString);
            costString = parentObject.getString("average_cost_for_two");
            ratingString = parentObject.getJSONObject("user_rating").getString("aggregate_rating");
            votesString = parentObject.getJSONObject("user_rating").getString("aggregate_rating");
            System.out.println("Nb votes:" + votesString);
            latitudeString = parentObject.getJSONObject("location").getString("latitude");
            longitudeString = parentObject.getJSONObject("location").getString("longitude");


            name.setText(nameString);

            cuisine.setText(cuisineString);
            votes.setText(votesString);
            cost.setText(costString);
            rating.setText(ratingString);
            location.setText(locationString);

            Toast.makeText(getApplicationContext(), "212121212121", Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }






    }


}
