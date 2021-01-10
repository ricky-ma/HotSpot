package com.example.mapstesting;

import android.widget.ImageView;
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
        LinearLayout txtLayout = findViewById(R.id.textLayout);

        ImageView populationImage = findViewById(R.id.imageView_pos_people);
        ImageView ratingImage = findViewById(R.id.imageView_pos_stars);

        //display number of stars for restaurant
//        int rating = (int) Math.round(restaurant.getRating());
//        switch (rating) {
//            case 0:
//                 ratingImage.setImageResource(R.drawable.zero_stars);
//            case 1:
//                 ratingImage.setImageResource(R.drawable.one_star);
//            case 2:
//                 ratingImage.setImageResource(R.drawable.two_stars);
//            case 3:
//                 ratingImage.setImageResource(R.drawable.three_stars);
//            case 4:
//                 ratingImage.setImageResource(R.drawable.four_stars);
//            case 5:
//                 ratingImage.setImageResource(R.drawable.five_stars);
//            default:
//                 ratingImage.setImageResource(R.drawable.five_stars);
//        }

//        int currPop = (int) Math.round(restaurant.getCurrent_popularity());
//        switch (currPop) {
//            case 0:
//                populationImage.setImageResource(R.drawable.safe_guy);
//            case 1:
//                populationImage.setImageResource(R.drawable.lonely_guy);
//            case 2:
//                populationImage.setImageResource(R.drawable.two_guys);
//            case 3:
//                populationImage.setImageResource(R.drawable.three_guys);
//            case 4:
//                populationImage.setImageResource(R.drawable.four_guys);
//            case 5:
//                populationImage.setImageResource(R.drawable.five_guys);
//            default:
//                populationImage.setImageResource(R.drawable.five_guys);
//        }

        TextView name = new TextView(this);
//        name.setText("Name: " + restaurant.getName);
        txtLayout.addView(name);

        TextView address = new TextView(this);
//        address.setText("Address: " + restaurant.getAddress);
        txtLayout.addView(address);
    }
}
