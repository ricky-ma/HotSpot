package com.example.mapstesting;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Page2List extends AppCompatActivity {

    private Button button;
    private String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);

        //creates a new button for each restaurant nearby
        //TODO: have restList contain Restaurant info from API
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        message = intent.getStringExtra(MapAndSearchHolder.EXTRA_MESSAGE);
        new PlaceSearchHandler().execute();
    }


    /**
     * moves to info page (page 3)
     */
    public void infoRestaurant(Restaurant chosen){
        Intent intent = new Intent(Page2List.this, Page3Info.class);
        intent.putExtra("name", chosen.getName());
        intent.putExtra("curr_pop", chosen.getCurrent_popularity());
        intent.putExtra("rating", chosen.getRating());
        intent.putExtra("address", chosen.getAddress());
        startActivity(intent);
    }

    private class PlaceSearchHandler extends AsyncTask<Void, Void, Void> {

        private ArrayList<Restaurant> resultList = new ArrayList<>();

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Void doInBackground(Void... voids) {
            StringBuilder jsonResults = getSearchResults(message);
            resultList = processJson(jsonResults);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            System.out.println("finished loading data");
            super.onPostExecute(aVoid);

            ArrayList<Restaurant> displayRestaurants = new ArrayList<>();
            LinearLayout mainLayout = findViewById(R.id.ButtonLayout);
            displayRestaurants = MapAndSearchHolder.exList;
            // TODO: BUG, UNCOMMENT TO MATCH QUERY RESULTS WITH POPULAR TIMES DATA
            // PROBLEM: NOT ENOUGH POPULAR TIMES DATA, RESULTS DON'T MATCH
//            for (Restaurant r1 : MapAndSearchHolder.exList) {
//                for (Restaurant r2 : resultList) {
//                    if (r1.getId().equals(r2.getId())) {
//                        displayRestaurants.add(r1);
//                    }
//                }
//            }

            List<Integer> buttonList = new ArrayList<>();
            HashMap<Integer, Restaurant> restaurantHashMap = new HashMap<>();
            for (Restaurant r : displayRestaurants) {
                Button newButton = new Button(Page2List.this);
                newButton.setText(r.getName());
                newButton.setId(r.hashCode());
                restaurantHashMap.put(r.hashCode(), r);
                buttonList.add(newButton.getId());
                mainLayout.addView(newButton);
            }

            if(displayRestaurants.isEmpty()){
                TextView error = new TextView(Page2List.this);
                error.setGravity(Gravity.CENTER_VERTICAL);
                error.setText("No Restaurants were Found");
                mainLayout.addView(error);
            }

            for (Integer b : buttonList){
                button = findViewById(b);
                Restaurant chosen = restaurantHashMap.get(b);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        infoRestaurant(chosen);
                    }
                });
            }

        }

        private StringBuilder getSearchResults(String message) {
            StringBuilder jsonResults = new StringBuilder();
            HttpURLConnection conn = null;
            try {
                URL url = new URL(message);
                conn = (HttpURLConnection) url.openConnection();
                InputStreamReader in = new InputStreamReader(conn.getInputStream());

                int read;
                char[] buff = new char[1024];
                while ((read = in.read(buff)) != -1) {
                    jsonResults.append(buff, 0, read);
                }
            } catch (MalformedURLException e) {
                Log.e("app", "Error processing Places API URL", e);
            } catch (IOException e) {
                Log.e("app", "Error connecting to Places API", e);
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            return jsonResults;
        }

        private ArrayList<Restaurant> processJson(StringBuilder jsonResults) {
            ArrayList<Restaurant> resultList = new ArrayList<>();
            try {
                // Create a JSON object hierarchy from the results
                JSONObject jsonObj = new JSONObject(jsonResults.toString());
                JSONArray predsJsonArray = jsonObj.getJSONArray("candidates");

                // Extract the Place descriptions from the results
                resultList = new ArrayList<Restaurant>(predsJsonArray.length());
                for (int i = 0; i < predsJsonArray.length(); i++) {
                    String id = predsJsonArray.getJSONObject(i).getString("place_id");
                    Restaurant r = new Restaurant(id);
                    resultList.add(r);
                }
            } catch (JSONException e) {
                Log.e("app", "Error processing JSON results", e);
            }
            return resultList;
        }


    }
}