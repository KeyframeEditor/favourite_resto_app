package com.example.uts_shock_berat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class fragment_map extends Fragment {

    private static float restoLongitude;
    private static float restoLatitude;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */


        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng pin = new LatLng(getArguments().getFloat("latitude"), getArguments().getFloat("longitude"));
            googleMap.addMarker(new MarkerOptions().position(pin).title(getArguments().getString("namaResto")));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pin, 14));
            Log.d("MAP ON LONG: ", String.valueOf(getArguments().getFloat("latitude")));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        final Button button_fullscreen = view.findViewById(R.id.button_fullscreen);
        button_fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("longitude", getArguments().getFloat("longitude"));
                intent.putExtra("latitude", getArguments().getFloat("latitude"));
                intent.putExtra("namaResto", getArguments().getString("namaResto"));
                startActivity(intent);
            }
        });
        // grab data
//        if (getArguments() != null) {
//            if (getArguments() != null) {
//                namaResto = getArguments().getString("namaResto");
//                placeResto = getArguments().getString("placeResto");
//                coordsResto = getArguments().getString("coordsResto");
//            }
//        }
    }

    public static fragment_map newInstance(float longitude, float latitude, String namaResto) {
        fragment_map fragment = new fragment_map();
        Bundle args = new Bundle();
        args.putFloat("longitude", longitude);
        args.putFloat("latitude", latitude);
        args.putString("namaResto", namaResto);
//        restoLongitude = longitude;
//        restoLatitude = latitude;
        fragment.setArguments(args);
        return fragment;
    }
}