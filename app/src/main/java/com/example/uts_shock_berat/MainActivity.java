package com.example.uts_shock_berat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    private ListView listview_resto;
    private Button button;
    private boolean isFragmentDisplayed = false;
    String listTest[] = {"canon","ballz","in","da","jaw"};
    List<Map<String, Object>> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview_resto = findViewById(R.id.listview_resto);
        button = findViewById(R.id.button);

        addRestoData();

//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (this, R.layout.activity_listview_resto, R.id.listview_textview, listTest);
//        listview_resto.setAdapter(arrayAdapter);
        RestoListAdapter restoListAdapter = new RestoListAdapter(this, R.layout.activity_listview_resto, itemList);
        listview_resto.setAdapter(restoListAdapter);


        listview_resto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("i: ", String.valueOf(i));

                // pasing variables
                String namaResto = String.valueOf(itemList.get(i).get("name"));
                String placeResto = String.valueOf(itemList.get(i).get("place"));
                float longitudeResto = Float.valueOf(itemList.get(i).get("longitude").toString());
                float latitudeResto = Float.valueOf(itemList.get(i).get("latitude").toString());
                String coordsResto = String.valueOf(longitudeResto) + ", "+ String.valueOf(latitudeResto);

                Log.i("NAMA: ", namaResto);
                Log.i("PLACE: ", placeResto);
                Log.i("COORDS: ", coordsResto);

                if (!isFragmentDisplayed){
                    displayFragment(namaResto, placeResto, coordsResto);
                    closeMapFragment();
                }
                else {
                    closeFragment();
                    displayMapFragment();
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void displayFragment(String namaResto, String placeResto, String coordsResto) {
        fragment_resto_description simple_fragment = com.example.uts_shock_berat.fragment_resto_description.newInstance(namaResto, placeResto, coordsResto);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, simple_fragment).addToBackStack(null).commit();
        isFragmentDisplayed = true;
    }

    private void displayMapFragment() {
        fragment_map simple_fragment = com.example.uts_shock_berat.fragment_map.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, simple_fragment).commit();
    }

    private void closeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment_resto_description simple_fragment= (com.example.uts_shock_berat.fragment_resto_description) fragmentManager.findFragmentById(R.id.fragment_container);
        if (simple_fragment != null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(simple_fragment).commit();
        }
        isFragmentDisplayed = false;
    }

    private void closeMapFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment_map simple_fragment= (com.example.uts_shock_berat.fragment_map) fragmentManager.findFragmentById(R.id.fragment_container);
        if (simple_fragment != null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(simple_fragment).commit();
        }
    }

    private void addRestoData(){
        Map<String, Object> item1 = new HashMap<>();
        item1.put("name", "Warung Jepun ygy");
        item1.put("place", "ConCat region");
        item1.put("longitude", -7.7498738753937175);
        item1.put("latitude", 110.39572713200663);
        itemList.add(item1);
    }
}