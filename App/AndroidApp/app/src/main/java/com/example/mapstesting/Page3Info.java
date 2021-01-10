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

        Bundle extras = getIntent().getExtras();

        LinearLayout mainLayout = findViewById(R.id.ButtonLayout);
        LinearLayout txtLayout = findViewById(R.id.textLayout);

        ImageView populationImage = findViewById(R.id.imageView_pos_people);
        ImageView ratingImage = findViewById(R.id.imageView_pos_stars);

        //display number of stars for restaurant
        int rating = Math.toIntExact(Math.round(extras.getDouble("rating")) / 20);
        switch (rating) {
            case 0:
                 ratingImage.setImageResource(R.drawable.zero_stars);
            case 1:
                 ratingImage.setImageResource(R.drawable.one_star);
            case 2:
                 ratingImage.setImageResource(R.drawable.two_stars);
            case 3:
                 ratingImage.setImageResource(R.drawable.three_stars);
            case 4:
                 ratingImage.setImageResource(R.drawable.four_stars);
            case 5:
                 ratingImage.setImageResource(R.drawable.five_stars);
            default:
                 ratingImage.setImageResource(R.drawable.five_stars);
        }

        int currPop = extras.getInt("curr_pop") / 20;
        switch (currPop) {
            case 0:
                populationImage.setImageResource(R.drawable.safe_guy);
            case 1:
                populationImage.setImageResource(R.drawable.lonely_guy);
            case 2:
                populationImage.setImageResource(R.drawable.two_guys);
            case 3:
                populationImage.setImageResource(R.drawable.three_guys);
            case 4:
                populationImage.setImageResource(R.drawable.four_guys);
            case 5:
                populationImage.setImageResource(R.drawable.five_guys);
            default:
                populationImage.setImageResource(R.drawable.five_guys);
        }

        TextView name = new TextView(this);
        name.setText("Name: " + extras.getString("name"));
        txtLayout.addView(name);

        TextView address = new TextView(this);
        address.setText("Address: " + extras.getString("address"));
        txtLayout.addView(address);
    }
}
