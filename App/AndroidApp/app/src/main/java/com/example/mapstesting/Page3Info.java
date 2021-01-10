package com.example.mapstesting;

import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Page3Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);

        LinearLayout mainLayout = findViewById(R.id.ButtonLayout);

        //display number of stars for restaurant
//        int rating = (int) Math.round(restaurant.getRating());
//        switch (rating) {
//            case 1:
//
//            case 2:
//
//            case 3:
//
//            case 4:
//
//            case 5:
//
//            default:
//        }

//        int currPop = (int) Math.round(restauraunt.getCurrent_popularity());
//        switch (currPop) {
//            case 1:
//
//            case 2:
//
//            case 3:
//
//            case 4:
//
//            case 5:
//
//            default:
//        }

        TextView name = new TextView(this);
//        name.setText("Name: " + restaurant.getName);
        mainLayout.addView(name);

        TextView address = new TextView(this);
//        address.setText("Address: " + restaurant.getAddress);
        mainLayout.addView(address);
    }
}