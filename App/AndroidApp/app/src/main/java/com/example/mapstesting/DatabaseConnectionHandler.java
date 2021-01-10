package com.example.mapstesting;

import android.os.AsyncTask;
import android.os.Build;
import android.util.JsonReader;
import androidx.annotation.RequiresApi;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import static java.lang.Integer.parseInt;

public class DatabaseConnectionHandler {

    private static String AUTH_TOKEN = null;
    private static ArrayList<Restaurant> restaurants = new ArrayList<>();

    public DatabaseConnectionHandler() {
        AsyncTask.execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                // All your networking logic
                // should be here
                // Create URL
                setAuthToken();
                restaurants = getRestaurantsFromDB();
            }
        });
    }

    public ArrayList<Restaurant> getRestaurants() {
        return restaurants;
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
