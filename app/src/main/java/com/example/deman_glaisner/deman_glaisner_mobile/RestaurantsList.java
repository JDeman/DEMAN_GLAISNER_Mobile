package com.example.deman_glaisner.deman_glaisner_mobile;

/**
 * Created by julo on 29/10/16.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;



public class RestaurantsList extends AppCompatActivity {

    private String currentSort = "categories";
    private ArrayList<Integer> beerID, beerTypeID, beerOriginID, categoryID;
    private ArrayList<String> beerName;
    private ExpandableListView expandableListView;
    private List<String> viewParents;
    private HashMap<String, List<String>> viewChildren;
    private CustomExpListAdapter expandableListAdapter;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.beer_list);
        downloadAllBeers();

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

        fillView();

        Toast.makeText(getApplicationContext(), "Liste chargée", Toast.LENGTH_SHORT).show();
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                describeBeer(viewChildren.get(viewParents.get(groupPosition)).get(childPosition));
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_beerlist, menu);
        return true;
    }

    private void describeBeer(String clickedBeer) {
        int i = 0;
        while(!beerName.get(i).equals(clickedBeer))
            i++;

        int clickedBeerId = beerID.get(i);
        Intent intent = new Intent(getApplicationContext(),RestaurantsList.class);
        intent.putExtra("beerID", clickedBeerId);
        startActivity(intent);

    }

    private void fillView() {

        DownloadTask dlTask = new DownloadTask();
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        String webContent = new String();
        List<ArrayList<String>> parentsList = null;
        ArrayList<String> tmpList;
        int tmpId;

        categoryID = new ArrayList<Integer>();

        dlTask.execute("GET", "http://binouze.fabrigli.fr/" + currentSort + ".json", "TXT");

        try {
            webContent = dlTask.get();
        }
        catch(InterruptedException | ExecutionException e) {}

        try {
            jsonArray = new JSONArray(webContent);
        }
        catch (JSONException e){}

        parentsList = new ArrayList<>();
        viewParents = new ArrayList<>();
        viewChildren = new HashMap<>();

        try {

            for(int i = 0 ; i < jsonArray.length() ; i++) {
                jsonObject = jsonArray.getJSONObject(i);
                viewParents.add(jsonObject.getString("name"));
                categoryID.add(jsonObject.getInt("id"));
                tmpList = new ArrayList<String>();
                parentsList.add(tmpList);

            }

        }

        catch (JSONException e) {}

        viewParents.add("Non classé");
        categoryID.add(-1);
        tmpList = new ArrayList<String>();
        parentsList.add(tmpList);


        ArrayList<Integer> filterApplied;
        currentSort.equals("categories");
        filterApplied = beerTypeID;

        for(int i = 0 ; i < categoryID.size() ; i++) {

            tmpId = categoryID.get(i);
            tmpList = parentsList.get(i);

            for(int j = 0 ; j < beerID.size() ; j++) {
                if(filterApplied.get(j) == tmpId)
                    tmpList.add(beerName.get(j));
            }
        }

        for(int i = 0 ; i < viewParents.size() ; i++)
            viewChildren.put(viewParents.get(i), parentsList.get(i));


        expandableListAdapter = new CustomExpListAdapter(this, viewParents, viewChildren);
        expandableListView.setAdapter(expandableListAdapter);

    }

    private void downloadAllBeers() {

        DownloadTask dlTask;
        JSONArray jsonArray = null;
        JSONObject jsonObject = null, jsonOrigin;
        String jsonString = new String();

        beerID = new ArrayList<Integer>();
        beerTypeID = new ArrayList<Integer>();
        beerOriginID = new ArrayList<Integer>();
        beerName = new ArrayList<String>();

        dlTask = new DownloadTask();
        dlTask.execute("GET", "http://binouze.fabrigli.fr/bieres.json", "BTXT");

        try {
            jsonString = dlTask.get();
        }
        catch(InterruptedException | ExecutionException e) {}

        try {
            jsonArray = new JSONArray(jsonString);
        }
        catch(JSONException e) {}

        for(int i = 0 ; i < jsonArray.length() ; i++) {

            try {
                jsonObject = jsonArray.getJSONObject(i);
            }
            catch (JSONException e) {}

            int beerIdTmp, beerTypeIdTmp, beerOriginTmp;
            String beerNameTmp;

            try {
                beerIdTmp = jsonObject.getInt("id");
            }
            catch(JSONException e) {
                beerIdTmp = -1;
            }

            try {
                beerTypeIdTmp = jsonObject.getInt("category_id");
            }
            catch(JSONException e) {
                beerTypeIdTmp = -1;
            }

            try {
                beerNameTmp = jsonObject.getString("name");
            }
            catch(JSONException e) {
                beerNameTmp = "Sans nom";
            }

            try {
                jsonOrigin = jsonObject.getJSONObject("country");
                beerOriginTmp = jsonOrigin.getInt("id");
            }
            catch(JSONException e) {
                beerOriginTmp = -1;
            }

            beerID.add(beerIdTmp);
            beerTypeID.add(beerTypeIdTmp);
            beerName.add(beerNameTmp);
            beerOriginID.add(beerOriginTmp);

        }

    }

}