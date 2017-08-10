package com.example.koo.kit;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by wonwoo on 2017-08-10.
 */

public class Map extends AppCompatActivity
        implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap map) {

        LatLng SEOUL = new LatLng(37.56, 126.97);
        LatLng SEOUL1 = new LatLng(37.54, 126.99);

        MarkerOptions markerOptions = new MarkerOptions();
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions1.position(SEOUL1);
        markerOptions.title("서울");
        markerOptions1.title("어딘가");
        markerOptions.snippet("한국의 수도");
        markerOptions1.snippet("어디일까요?");
        map.addMarker(markerOptions);
        map.addMarker(markerOptions1);

        map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        map.animateCamera(CameraUpdateFactory.zoomTo(10));
    }

}