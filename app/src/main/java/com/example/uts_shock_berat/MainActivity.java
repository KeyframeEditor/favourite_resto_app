package com.example.uts_shock_berat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.view.MenuInflater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private ListView listview_resto;
    private boolean isFragmentDisplayed = false;
    String listTest[] = {"canon","ballz","in","da","jaw"};
    static List<Map<String, Object>> itemList = new ArrayList<>();

    //for sensors
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private static final int SHAKE_THRESHOLD = 25;
    private long lastUpdateTime;
    private boolean shakeConfirmation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview_resto = findViewById(R.id.listview_resto);

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
                    displayMapFragment(longitudeResto, latitudeResto, namaResto);
                }
            }
        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lastUpdateTime = System.currentTimeMillis();
    }

    private void displayFragment(String namaResto, String placeResto, String coordsResto) {
        fragment_resto_description simple_fragment = com.example.uts_shock_berat.fragment_resto_description.newInstance(namaResto, placeResto, coordsResto);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, simple_fragment).addToBackStack(null).commit();
        isFragmentDisplayed = true;
    }

    private void displayMapFragment(float longitude, float latitude, String namaResto) {
        fragment_map simple_fragment = com.example.uts_shock_berat.fragment_map.newInstance(longitude, latitude, namaResto);
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
        item1.put("name", "Warung Jepun");
        item1.put("place", "Jalan Kaliurang KM 5.5, Caturtunggal, Kec. Depok, Kabupaten Sleman, Daerah Istimewa Yogyakarta 55281");
        item1.put("latitude", -7.743837);
        item1.put("longitude", 110.383864);
        itemList.add(item1);

        Map<String, Object> item2 = new HashMap<>();
        item2.put("name", "Gudeg Bu Tjitro");
        item2.put("place", "Jl. Janti No. 165, Karang Jambe, Kec. Umbulharjo, Kota Yogyakarta, Daerah Istimewa Yogyakarta 55166");
        item2.put("latitude", -7.813390);
        item2.put("longitude", 110.383911);
        itemList.add(item2);

        Map<String, Object> item3 = new HashMap<>();
        item3.put("name", "Bakmi Jawa Pak Pele");
        item3.put("place", "Jalan Bantul No.61, Gowongan, Kec. Jetis, Kota Yogyakarta, Daerah Istimewa Yogyakarta 55231");
        item3.put("latitude", -7.799746);
        item3.put("longitude", 110.372590);
        itemList.add(item3);

        Map<String, Object> item4 = new HashMap<>();
        item4.put("name", "Soto Ayam Pak Pangat");
        item4.put("place", "Jl. Raya Solo KM. 11 No.105, Kec. Ngemplak, Kabupaten Sleman, Daerah Istimewa Yogyakarta 55584");
        item4.put("latitude", -7.747548);
        item4.put("longitude", 110.414444);
        itemList.add(item4);

        Map<String, Object> item5 = new HashMap<>();
        item5.put("name", "Sate Klathak Pak Pong");
        item5.put("place", "Jl. Kaliurang KM 8, Krawitan, Hargobinangun, Kec. Pakem, Kabupaten Sleman, Daerah Istimewa Yogyakarta 55582");
        item5.put("latitude", -7.668314);
        item5.put("longitude", 110.393148);
        itemList.add(item5);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.changeLanguage:
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - lastUpdateTime) > 100) {
            long diffTime = (currentTime - lastUpdateTime);
            lastUpdateTime = currentTime;

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float acceleration = (float) Math.sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH;
            if (acceleration > SHAKE_THRESHOLD && shakeConfirmation == false) {
                setShakeConfirmation(true);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.promo_get));
                builder.setMessage(getString(R.string.promo_desc));
                builder.setPositiveButton(getString(R.string.promo_subscribe), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setShakeConfirmation(false);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setShakeConfirmation(false);
                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        setShakeConfirmation(false);
                    }
                });
                builder.show();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void setShakeConfirmation(boolean value) {
        shakeConfirmation = value;
    }

    public static String getLatestResto() {
        Random indexResto = new Random();
        int randomIndexResto = indexResto.nextInt(itemList.size());
        String index = String.valueOf(itemList.get(randomIndexResto).get("name"));
        return index;
    }
}