package com.example.mapstesting;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.util.JsonReader;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.lang.Integer.parseInt;


public class MapAndSearchHolder extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mMapView;
    private static String GOOGLE_API_KEY = "AIzaSyCHDoAJTYRv9yPKmuI_FVNlPZBS5hPXFEU";
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    public static ArrayList<Restaurant> exList = new ArrayList<>();
    public static final String EXTRA_MESSAGE = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=";

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
        mMapView = findViewById(R.id.mapView);
        mMapView.onCreate(mapViewBundle);
//        GOOGLE_API_KEY = getResources().getString(R.string.google_maps_key);
        new DatabaseConnectionHandler().execute();
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, Page2List.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, EXTRA_MESSAGE + message + "&inputtype=textquery&fields=place_id&locationbias=circle:2000@49.2827,-123.1207&key=" + GOOGLE_API_KEY);
        startActivity(intent);
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
        /****
        add user input to toggle between live view and average view
         if live, just set average parameter to "false"
         otherwise, set average to true, get dayOfWeek and hour from user to pass in
        ****/
        List<WeightedLatLng> points = parseRestaurants(exList, false, 0, 0);
        if (!points.isEmpty()) {
            HeatmapTileProvider provider = new HeatmapTileProvider.Builder()
                    .radius(50)
                    .weightedData(points)
                    .build();
            TileOverlay heatmap = map.addTileOverlay(new TileOverlayOptions().tileProvider(provider));
        }

        // display all the restaurants
        for (Restaurant r : exList) {
            if (r.getCurrent_popularity() > 75) {
                LatLng point = new LatLng(r.getLat(), r.getLng());
                map.addMarker(new MarkerOptions().position(point).title(r.getName()));
            }
        }
    }

    private ArrayList<WeightedLatLng> parseRestaurants (List<Restaurant> restaurantList,
                                                        boolean average, int dayOfWeek, int hour) {
        ArrayList<WeightedLatLng> outputList = new ArrayList<>();
        if (average) {
            for (Restaurant rest : restaurantList) {
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
                LatLng pos = new LatLng(rest.getLat(), rest.getLng());
                outputList.add(new WeightedLatLng(pos, busyness));
            }
            return outputList;
        } else {
            for (Restaurant rest : restaurantList) {
                LatLng pos = new LatLng(rest.getLat(), rest.getLng());
                outputList.add(new WeightedLatLng(pos, rest.getCurrent_popularity()));
            }
            return outputList;
        }
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

    private class DatabaseConnectionHandler extends AsyncTask<Void, Void, Void> {

        private String AUTH_TOKEN = null;
        private ArrayList<Restaurant> restaurants = new ArrayList<>();

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Void doInBackground(Void... voids) {
            setAuthToken();
            restaurants = getRestaurantsFromDB();
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            System.out.println("finished loading data");
            super.onPostExecute(aVoid);
            exList = restaurants;
            mMapView.getMapAsync(MapAndSearchHolder.this);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        private void setAuthToken() {
            URL dbEndpoint = null;
            try {
                dbEndpoint = new URL("https://f8219673-b356-46a8-bcd3-5428dde04d19-us-east1.apps.astra.datastax.com/api/rest/v1/auth");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                if (dbEndpoint != null) {
                    HttpsURLConnection conn = (HttpsURLConnection) dbEndpoint.openConnection();

                    // set authorization token
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);
                    String str =  "{\"username\": \"user1\",\"password\":\"nwHacks2021\"}";
                    byte[] outputInBytes = str.getBytes(StandardCharsets.UTF_8);
                    OutputStream os = conn.getOutputStream();
                    os.write(outputInBytes);
                    os.close();
                    int responseCode = conn.getResponseCode();
                    if (responseCode == 200 || responseCode == 201) {
                        InputStream responseBody = conn.getInputStream();
                        InputStreamReader responseBodyReader = new InputStreamReader(responseBody, StandardCharsets.UTF_8);
                        JsonReader jsonReader = new JsonReader(responseBodyReader);
                        jsonReader.beginObject(); // Start processing the JSON object
                        while (jsonReader.hasNext()) { // Loop through all keys
                            String key = jsonReader.nextName(); // Fetch the next key
                            if (key.equals("authToken")) { // Check if desired key
                                AUTH_TOKEN = jsonReader.nextString();
                                break; // Break out of the loop
                            } else {
                                jsonReader.skipValue(); // Skip values of other keys
                            }
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private Restaurant readRow(JsonReader jsonReader) throws IOException {
            String id = null, name = null, address = null;
            ArrayList<String> types = new ArrayList<>();
            Double lat = null, lng = null, rating = null;
            Integer rating_n = null, current_popularity = null;
            ArrayList<Integer> poptime_mon = new ArrayList<>(), poptime_tue = new ArrayList<>(),
                    poptime_wed = new ArrayList<>(), poptime_thr = new ArrayList<>(), poptime_fri = new ArrayList<>(),
                    poptime_sat = new ArrayList<>(), poptime_sun = new ArrayList<>();

            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String key = jsonReader.nextName();
                switch (key) {
                    case "id":
                        id = jsonReader.nextString();
                        break;
                    case "name":
                        try {
                            name = jsonReader.nextString();
                        } catch (IllegalStateException e) {
                            name = "";
                            jsonReader.skipValue();
                        }
                        break;
                    case "address":
                        try {
                            address = jsonReader.nextString();
                        } catch (IllegalStateException e) {
                            name = "";
                            jsonReader.skipValue();
                        }
                        break;
                    case "types":
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()) {
                            types.add(jsonReader.nextString());
                        }
                        jsonReader.endArray();
                        break;
                    case "latitude":
                        lat = jsonReader.nextDouble();
                        break;
                    case "longitude":
                        lng = jsonReader.nextDouble();
                        break;
                    case "rating":
                        rating = jsonReader.nextDouble();
                        break;
                    case "rating_n":
                        rating_n = jsonReader.nextInt();
                        break;
                    case "current_popularity":
                        try {
                            current_popularity = jsonReader.nextInt();
                        } catch (IllegalStateException e) {
                            current_popularity = 0;
                            jsonReader.skipValue();
                        }
                        break;
                    case "poptime_mon":
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()) {
                            poptime_mon.add(parseInt(jsonReader.nextString()));
                        }
                        jsonReader.endArray();
                        break;
                    case "poptime_tue":
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()) {
                            poptime_tue.add(parseInt(jsonReader.nextString()));
                        }
                        jsonReader.endArray();
                        break;
                    case "poptime_wed":
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()) {
                            poptime_wed.add(parseInt(jsonReader.nextString()));
                        }
                        jsonReader.endArray();
                        break;
                    case "poptime_thr":
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()) {
                            poptime_thr.add(parseInt(jsonReader.nextString()));
                        }
                        jsonReader.endArray();
                        break;
                    case "poptime_fri":
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()) {
                            poptime_fri.add(parseInt(jsonReader.nextString()));
                        }
                        jsonReader.endArray();
                        break;
                    case "poptime_sat":
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()) {
                            poptime_sat.add(parseInt(jsonReader.nextString()));
                        }
                        jsonReader.endArray();
                        break;
                    case "poptime_sun":
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()) {
                            poptime_sun.add(parseInt(jsonReader.nextString()));
                        }
                        jsonReader.endArray();
                        break;
                    default:
                        jsonReader.skipValue();
                        break;
                }
            }
            jsonReader.endObject();
            return new Restaurant(id, name, address, types, lat, lng, rating, rating_n, current_popularity, poptime_mon,
                    poptime_tue, poptime_wed, poptime_thr, poptime_fri, poptime_sat, poptime_sun);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        private ArrayList<Restaurant> getRestaurantsFromDB() {
            ArrayList<Restaurant> restaurants = new ArrayList<>();

            URL dbEndpoint = null;
            try {
                dbEndpoint = new URL("https://f8219673-b356-46a8-bcd3-5428dde04d19-us-east1.apps.astra.datastax.com/api/rest/v1/keyspaces/nwhax_data/tables/places/rows");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                if (dbEndpoint != null) {
                    HttpsURLConnection conn = (HttpsURLConnection) dbEndpoint.openConnection();
                    conn.setRequestProperty("accept", "application/json");
                    conn.setRequestProperty("x-cassandra-token", AUTH_TOKEN);
                    int responseCode = conn.getResponseCode();
                    if (responseCode == 200 || responseCode == 201) {
                        InputStream responseBody = conn.getInputStream();
                        InputStreamReader responseBodyReader = new InputStreamReader(responseBody, StandardCharsets.UTF_8);
                        JsonReader jsonReader = new JsonReader(responseBodyReader);
                        jsonReader.beginObject(); // Start processing the JSON object
                        while (jsonReader.hasNext()) { // Loop through all keys
                            String key = jsonReader.nextName(); // Fetch the next key
                            if (key.equals("rows")) { // Check if desired key
                                jsonReader.beginArray();
                                while (jsonReader.hasNext()) {
                                    Restaurant r = readRow(jsonReader);
                                    restaurants.add(r);
                                }
                                jsonReader.endArray();
                                break; // Break out of the loop
                            } else {
                                jsonReader.skipValue(); // Skip values of other keys
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return restaurants;
        }
    }
}
