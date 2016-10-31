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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;



public class RestaurantsList extends AppCompatActivity {

    private ArrayList<Integer> idlist;
    private ArrayList<String> restaurantList;
    private ListView listView;
    private HashMap<String, List<String>> viewChildren;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_list);
        listView = (ListView) findViewById(R.id.listView);
        idlist = fillView();
        Toast.makeText(getApplicationContext(), "Liste chargée", Toast.LENGTH_SHORT).show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                describeRestaurant(listView.getItemAtPosition(position).toString());
            }
        });
    }

    private void describeRestaurant(String clickedrestaurant) {
        int i = 0;
        while(!restaurantList.get(i).equals(clickedrestaurant))
            i++;

        int clickedrestaurantID = idlist.get(i);
        Intent intent = new Intent(getApplicationContext(),RestaurantsList.class);
        intent.putExtra("restaurantID", clickedrestaurantID);
        startActivity(intent);

    }

    private ArrayList<Integer> fillView() {

        DownloadTask dlTask = new DownloadTask();
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        String webContent = new String();
        List<ArrayList<String>> parentsList = null;
        ArrayList<String> tmpList;
        int tmpId;

        //Arrays de coordonnées a mettre ici


        dlTask.execute("GET", "https://developers.zomato.com/api/v2.1/search?lat=42.360083&lon=-71.05888&radius=3000", "TXT");

        try {
            webContent = dlTask.get();

            JSONObject parentObject = new JSONObject(webContent);
            JSONArray parentArray = parentObject.getJSONArray("nearby_restaurants");


            //JSON MAGIC HERE JSON JSON JSON JSON JSON JSON JSON JSON jSON
            for(int i = 0 ; i < parentArray.length() ; i++) {

                JSONObject finalObject =  parentArray.getJSONObject(i);
                String restaurantName = finalObject.getJSONObject("restaurant").getString("name");
                int id = finalObject.getJSONObject("restaurant").getJSONObject("R").getInt("id");
                restaurantList.add(restaurantName);
                idlist.add(id);

            }

        } catch(InterruptedException | ExecutionException e) {

        } catch (JSONException e){}

        return idlist;

    }
}