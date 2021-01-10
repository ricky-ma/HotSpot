package com.example.mapstesting;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

import java.nio.file.Paths;
import java.util.ArrayList;

public class DatabaseConnectionHandler {

    private static final String CONNECT_BUNDLE = "secure-connect-database_name.zip";
    private static final String USERNAME = "user1";
    private static final String PASSWORD = "nwHacks2021";
    private static final String KEYSPACE = "nwhax_data";
    private CqlSession connection = null;

    public CqlSession getConnection() {
        return connection;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public DatabaseConnectionHandler() {
        try (CqlSession session = CqlSession.builder()
                .withCloudSecureConnectBundle(Paths.get(CONNECT_BUNDLE))
                .withAuthCredentials(USERNAME,PASSWORD)
                .withKeyspace(KEYSPACE)
                .build()) {
            connection = session;
        } catch () {
            System.out.println("An error occured");
        }
    }

    public ArrayList<Restaurant> getRestaurants() {
        ArrayList<Restaurant> result = new ArrayList<>();
        try {
            ResultSet rs = connection.execute("SELECT * FROM places");
            for (Row row : rs) {
                Restaurant restaurant = new Restaurant(
                        row.getString("id"),
                        row.getString("name"),
                        row.getString("address"),
                        row.getList("types"),
                        row.getDouble("latitude"),
                        row.getDouble("longitude"),
                        row.getDouble("rating"),
                        row.getInt("rating_n"),
                        row.getInt("current_popularity"),
                        row.getList("poptime_mon"),
                        row.getList("poptime_tue"),
                        row.getList("poptime_wed"),
                        row.getList("poptime_thr"),
                        row.getList("poptime_fri"),
                        row.getList("poptime_sat"),
                        row.getList("poptime_sun")
                );
                result.add(restaurant);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}