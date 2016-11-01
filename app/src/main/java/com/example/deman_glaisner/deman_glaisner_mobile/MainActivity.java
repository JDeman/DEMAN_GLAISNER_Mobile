package com.example.deman_glaisner.deman_glaisner_mobile;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Hashtable;


public class MainActivity extends AppCompatActivity {

    private DownloadFragment dfrag = null;

    Hashtable<String, double[]> ht = new Hashtable<>();

    /*
    double[] bostonCord = new double[]{42.360083,-71.05888};
    double[] nyCord = {40.712784,-74.005941};
    double[] laCord= {34.052234,-118.243685};
    double[] chicagoCord= {41.878114,-87.629798};
    double[] seattleCord= {47.60621,-122.332071};
    double[] denverCord= {47.60621,-122.332071};
    double[] lasvegasCord= {36.169941,-115.13983};
    double[] miamiCord = {25.76168,-80.19179};
    double[] dallasCord = {32.776664,-96.796988};
    double[] sfCord ={37.77493,-122.419416};*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        ht.put("Boston", bostonCord);
        ht.put("New York", nyCord);
        ht.put("Los Angeles", laCord);
        ht.put("Chicago", chicagoCord);
        ht.put("Seattle", seattleCord);
        ht.put("Denver", denverCord);
        ht.put("Las Vegas", lasvegasCord);
        ht.put("Miami", miamiCord);
        ht.put("Dallas", dallasCord);
        ht.put("San Francisco", sfCord);*/

        Button searchButton = (Button) findViewById(R.id.button);
        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cities_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_spinner1 = spinner1.getSelectedItem().toString();
                System.out.println(text_spinner1);
                Toast.makeText(getApplicationContext(), "Liste pour: " + text_spinner1, Toast.LENGTH_SHORT).show();

                dfrag = new DownloadFragment();
                dfrag.show(getFragmentManager(),"test");
                System.out.println("niveau 1");
                Handler handler = new Handler();
                System.out.println("niveau 2");
                handler.postDelayed(new Runnable() {
                    public void run(){
                        System.out.println("niveau 3");
                        Intent i = new Intent(getApplicationContext(), RestaurantsList.class);
                        System.out.println("niveau 4");
                        startActivity(i);
                    }
                }, 100);
            }
        });
    }

    /*
    //Action a effectuer au click du boutton recherche
    public void displayRestaurantList(View v){
        dfrag = new DownloadFragment();
        dfrag.show(getFragmentManager(), "test");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent i = new Intent(getApplicationContext(), RestaurantsList.class);
                startActivity(i);
            }
        }, 100);
    }*/
}
