package com.muhammadhilman.gistugas1gcd;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import java.util.HashMap;

import java.util.Map;

import static com.google.android.gms.maps.SupportMapFragment.newInstance;
import static com.muhammadhilman.gistugas1gcd.GeoLocation.*;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, OnMarkerClickListener, OnMapClickListener {

//    private Marker marker;
    private Map<String, Marker> markers = new HashMap<String, Marker>();
    private GoogleMap mMap;
    private Polyline line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_main);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

//        ea.zoomControlsEnabled(true);


        mapFragment.getMapAsync(this);


    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.

        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);

    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        line.remove();
        markers.remove(marker.getId());
        marker.remove();

        Log.d("marker", "marker clicked");
        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

    @Override
    public void onMapClick(LatLng point){
        Log.d("test", "map clicked");
        if(markers.size() != 2){
            if(markers.size() < 2){
                Marker marker = mMap.addMarker(new MarkerOptions().position(point)
                        .title("position 1"));
                markers.put(marker.getId(), marker);

            }

            if(markers.size() == 2){

                DistanceCalculator CircleDistance = new DistanceCalculator();
                GeoLocation test = fromDegrees(markers.get(markers.keySet().toArray()[0]).getPosition().latitude, markers.get(markers.keySet().toArray()[0]).getPosition().longitude);
                line = mMap.addPolyline(new PolylineOptions()
                        .add(markers.get(markers.keySet().toArray()[0]).getPosition(), markers.get(markers.keySet().toArray()[1]).getPosition())
                        .width(5)
                        .color(Color.RED));
                line.setGeodesic(true);

                Toast.makeText(this,
                        "Based on Jan Philip Matuschek theory: " + test.distanceTo(fromDegrees(markers.get(markers.keySet().toArray()[1]).getPosition().latitude, markers.get(markers.keySet().toArray()[1]).getPosition().longitude), 6371.01)
                +" Km",
                        Toast.LENGTH_SHORT).show();
                Toast.makeText(this,
                        "Based on John M. (Intel) theory" +
                                ": " + CircleDistance.greatCircleInKilometers(markers.get(markers.keySet().toArray()[0]).getPosition().latitude, markers.get(markers.keySet().toArray()[0]).getPosition().longitude, markers.get(markers.keySet().toArray()[1]).getPosition().latitude, markers.get(markers.keySet().toArray()[1]).getPosition().longitude)+" Km",
                        Toast.LENGTH_SHORT).show();
                Toast.makeText(this,
                        "SphericalUtil.computeDistanceBetween: " + SphericalUtil.computeDistanceBetween(markers.get(markers.keySet().toArray()[0]).getPosition(), markers.get(markers.keySet().toArray()[1]).getPosition())+" M",
                        Toast.LENGTH_SHORT).show();
            }
        }






    }




}
