package com.example.mapstesting;

import java.util.ArrayList;
import java.util.List;

/**
 * Restaurant parent class
 */
public class Restaurant {
    private String id;
    private String name;
    private String address;
    private List<String> types;
    private double latitude;
    private double longitude;
    private double rating;
    private int rating_n;
    private int current_popularity;
    private List<Integer> poptime_mon;
    private List<Integer> poptime_tue;
    private List<Integer> poptime_wed;
    private List<Integer> poptime_thr;
    private List<Integer> poptime_fri;
    private List<Integer> poptime_sat;
    private List<Integer> poptime_sun;

    public Restaurant(String id, String name, String address, List<String> types, double latitude,
                      double longitude, double rating, int rating_n, int current_popularity,
                      List<Integer> poptime_mon, List<Integer> poptime_tue,
                      List<Integer> poptime_wed, List<Integer> poptime_thr,
                      List<Integer> poptime_fri, List<Integer> poptime_sat,
                      List<Integer> poptime_sun) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.types = types;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
        this.rating_n = rating_n;
        this.current_popularity = current_popularity;
        this.poptime_mon = poptime_mon;
        this.poptime_tue = poptime_tue;
        this.poptime_wed = poptime_wed;
        this.poptime_thr = poptime_thr;
        this.poptime_fri = poptime_fri;
        this.poptime_sat = poptime_sat;
        this.poptime_sun = poptime_sun;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getTypes() {
        return new ArrayList<>(types);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getRating() {
        return rating;
    }

    public int getRating_n() {
        return rating_n;
    }

    public int getCurrent_popularity() {
        return current_popularity;
    }

    public List<Integer> getPoptime_mon() {
        return new ArrayList<>(poptime_mon);
    }

    public List<Integer> getPoptime_tue() {
        return new ArrayList<>(poptime_tue);
    }

    public List<Integer> getPoptime_wed() {
        return new ArrayList<>(poptime_wed);
    }

    public List<Integer> getPoptime_thr() {
        return new ArrayList<>(poptime_thr);
    }

    public List<Integer> getPoptime_fri() {
        return new ArrayList<>(poptime_fri);
    }

    public List<Integer> getPoptime_sat() {
        return new ArrayList<>(poptime_sat);
    }

    public List<Integer> getPoptime_sun() {
        return new ArrayList<>(poptime_sun);
    }
}
