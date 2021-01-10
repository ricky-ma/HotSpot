package com.example.mapstesting;

import java.util.List;

/**
 * Restaurant parent class
 */
public class Restaurant {
    private final String id;
    private final String name;
    private final String address;
    private final List<String> types;
    private final Double lat;
    private final Double lng;
    private final Double rating;
    private final Integer rating_n;
    private final Integer current_popularity;
    private final List<Integer> poptime_mon;
    private final List<Integer> poptime_tue;
    private final List<Integer> poptime_wed;
    private final List<Integer> poptime_thr;
    private final List<Integer> poptime_fri;
    private final List<Integer> poptime_sat;
    private final List<Integer> poptime_sun;

    public Restaurant(String id, String name, String address, List<String> types, Double lat, Double lng,
                      Double rating, Integer rating_n, Integer current_popularity, List<Integer> poptime_mon,
                      List<Integer> poptime_tue, List<Integer> poptime_wed, List<Integer> poptime_thr,
                      List<Integer> poptime_fri, List<Integer> poptime_sat, List<Integer> poptime_sun) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.types = types;
        this.lat = lat;
        this.lng = lng;
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

    public String getName(){
        return name;
    }

    public String getAddress(){
        return address;
    }


    public List<String> getTypes() {
        return types;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public Double getRating() {
        return rating;
    }

    public Integer getRating_n() {
        return rating_n;
    }

    public Integer getCurrent_popularity() {
        return current_popularity;
    }

    public List<Integer> getPoptime_mon() {
        return poptime_mon;
    }

    public List<Integer> getPoptime_tue() {
        return poptime_tue;
    }

    public List<Integer> getPoptime_wed() {
        return poptime_wed;
    }

    public List<Integer> getPoptime_thr() {
        return poptime_thr;
    }

    public List<Integer> getPoptime_fri() {
        return poptime_fri;
    }

    public List<Integer> getPoptime_sat() {
        return poptime_sat;
    }

    public List<Integer> getPoptime_sun() {
        return poptime_sun;
    }
}
