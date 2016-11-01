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
    //private HashMap<String, List<String>> viewChildren;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_list);
        listView = (ListView) findViewById(R.id.listView);
        String webContent = null;
        DownloadTask dlTask = new DownloadTask();

        try{

            dlTask.execute("GET", "https://developers.zomato.com/api/v2.1/search?lat=32.776664&lon=-96.796988&radius=2000", "JSON");
            webContent = dlTask.get();

        }catch (InterruptedException | ExecutionException e){}

        System.out.println("webcontent vaut: ");
        System.out.println("webcontent taille: " + webContent.length());
        //idlist = fillView();

        Toast.makeText(getApplicationContext(), "ID premier: ", Toast.LENGTH_SHORT).show();
        //System.out.println("valeur: " +idlist.get(1));

        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                describeRestaurant(listView.getItemAtPosition(position).toString());
            }
        });*/
    }

    /*
    private void describeRestaurant(String clickedrestaurant) {
        int i = 0;
        while(!restaurantList.get(i).equals(clickedrestaurant))
            i++;

        int clickedrestaurantID = idlist.get(i);
        Intent intent = new Intent(getApplicationContext(),RestaurantsList.class);
        intent.putExtra("restaurantID", clickedrestaurantID);
        startActivity(intent);

    }*/

    private ArrayList<Integer> fillView() {


        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        String webContent;
        List<ArrayList<String>> parentsList = null;
        ArrayList<String> tmpList;
        ArrayList<Integer> aalist = new ArrayList<>();
        int tmpId;

        //Arrays de coordonnées a mettre ici


        //return dlTask.execute("GET", "https://developers.zomato.com/api/v2.1/search?lat=32.776664&lon=-96.796988&radius=3000", "JSON");
        //System.out.println("récupération du JSON");

        /*try {

            JSONObject parentObject = new JSONObject(webContent);
            JSONArray parentArray = parentObject.getJSONArray("nearby_restaurants");


            //JSON MAGIC HERE JSON JSON JSON JSON JSON JSON JSON JSON jSON
            for(int i = 0 ; i < parentArray.length() ; i++) {
                System.out.println("RECUPERATION DES VALEURS JSON");
                JSONObject finalObject =  parentArray.getJSONObject(i);
                String restaurantName = finalObject.getJSONObject("restaurant").getString("name");
                System.out.println("Nom restaurant recupere" + restaurantName);
                int id = finalObject.getJSONObject("restaurant").getJSONObject("R").getInt("id");
                System.out.println("restaurad ID recupere" + id);
                System.out.println("Ajout d'une valeur dans restaurantList");
                restaurantList.add(restaurantName);
                System.out.println("AJOUT d'une valeur dans idList");
                idlist.add(id);

            }

        }catch (InterruptedException | ExecutionException | JSONException e){}

        */
        System.out.println("Return de la fonction filllll");

        aalist.add(0,23);
        aalist.add(1,34);
        aalist.add(2,45);
        return aalist;


    }
}