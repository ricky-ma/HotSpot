package com.example.mapstesting;

import android.widget.Toast;
import androidx.annotation.RawRes;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;

import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MapAndSearchHolder extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mMapView;
    private List<RestaurantDummy> exList = new ArrayList<>();


    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_and_search_holder);

        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);
        List<Integer> busyT2 = new ArrayList<>();
        List<Integer> busyT = new ArrayList<>();
        busyT.add(5); busyT.add(1); busyT.add(4); busyT.add(6); busyT.add(18);
        busyT.add(25);busyT.add(5); busyT.add(1); busyT.add(4); busyT.add(6); busyT.add(18);
        busyT.add(25);busyT.add(5); busyT.add(1); busyT.add(4); busyT.add(6); busyT.add(18);
        busyT.add(25);busyT.add(5); busyT.add(1); busyT.add(4); busyT.add(6); busyT.add(18);
        busyT.add(25);
        busyT2.add(2); busyT2.add(3); busyT2.add(12); busyT2.add(10); busyT2.add(14); busyT2.add(1);
        busyT2.add(2); busyT2.add(3); busyT2.add(12); busyT2.add(10); busyT2.add(14); busyT2.add(1);
        busyT2.add(2); busyT2.add(3); busyT2.add(12); busyT2.add(10); busyT2.add(14); busyT2.add(1);
        busyT2.add(2); busyT2.add(3); busyT2.add(12); busyT2.add(10); busyT2.add(14); busyT2.add(1);
        List<String> types = new ArrayList<>();
        types.add("pizza");
        RestaurantDummy rest1 = new RestaurantDummy("1", "A", "abc",
                types, 49.285931, -123.114924, 4, 3,
                4, busyT2, busyT2, busyT2, busyT2, busyT2, busyT2, busyT2);
        RestaurantDummy rest2 = new RestaurantDummy("1", "A", "abc",
                types, 49.285298, -123.113889, 4, 3,
                4, busyT2, busyT2, busyT2, busyT2, busyT2, busyT2, busyT2);
        exList.add(rest1); exList.add(rest2);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng van = new LatLng(49.2827, -123.1207);
        map.addMarker(new MarkerOptions().position(van).title("Vancouoouuver"));
        map.moveCamera(CameraUpdateFactory.newLatLng(van));
        List<WeightedLatLng> points = parseRestaurants(exList);
        HeatmapTileProvider provider = new HeatmapTileProvider.Builder()
                .radius(50)
                .weightedData(points)
                .build();
        TileOverlay heatmap = map.addTileOverlay(new
                TileOverlayOptions().tileProvider(provider));
    }

    private ArrayList<WeightedLatLng> parseRestaurants
            (List<RestaurantDummy> restaurantList) {
        ArrayList<WeightedLatLng> outputList = new ArrayList<>();
        Date timeDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(timeDate);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        int hour = c.get(Calendar.HOUR_OF_DAY);

        for (RestaurantDummy rest : restaurantList) {
            List<Integer> busyTimes = new ArrayList<>();
            switch (dayOfWeek) {
                case 1:
                    busyTimes = rest.getPoptime_sun();
                    break;
                case 2:
                    busyTimes = rest.getPoptime_mon();
                    break;
                case 3:
                    busyTimes = rest.getPoptime_tue();
                    break;
                case 4:
                    busyTimes = rest.getPoptime_wed();
                    break;
                case 5:
                    busyTimes = rest.getPoptime_thr();
                    break;
                case 6:
                    busyTimes = rest.getPoptime_fri();
                    break;
                case 7:
                    busyTimes = rest.getPoptime_sat();
                    break;
                default:
                    break;
            }
            int busyness = busyTimes.get(hour);
            LatLng pos = new LatLng(rest.getLatitude(), rest.getLongitude());
            outputList.add(new WeightedLatLng(pos, busyness));
        }
        return outputList;
    }


    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
