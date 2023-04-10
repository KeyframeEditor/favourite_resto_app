package com.example.uts_shock_berat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_resto_description#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_resto_description extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String namaResto;
    private String placeResto;
    private String coordsResto;

    public fragment_resto_description() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_resto_description.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_resto_description newInstance(String param1, String param2) {
        fragment_resto_description fragment = new fragment_resto_description();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // grab data
        if (getArguments() != null) {
            if (getArguments() != null) {
                namaResto = getArguments().getString("namaResto");
                placeResto = getArguments().getString("placeResto");
                coordsResto = getArguments().getString("coordsResto");
            }
        }

        final TextView tv_namaResto = view.findViewById(R.id.tv_namaResto);
        final TextView tv_lokasiResto = view.findViewById(R.id.tv_lokasiResto);
        final TextView tv_koordinatResto = view.findViewById(R.id.tv_koordinatResto);
        tv_namaResto.setText(namaResto);
        tv_lokasiResto.setText(placeResto);
        tv_koordinatResto.setText(coordsResto);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resto_description, container, false);
    }

    public static fragment_resto_description newInstance(String namaResto, String placeResto, String coordsResto) {
        fragment_resto_description fragment = new fragment_resto_description();
        Bundle args = new Bundle();
        args.putString("namaResto", namaResto);
        args.putString("placeResto", placeResto);
        args.putString("coordsResto", coordsResto);
        fragment.setArguments(args);
        return fragment;
    }
}