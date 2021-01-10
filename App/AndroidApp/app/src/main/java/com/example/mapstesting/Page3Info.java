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
        int rating = (int) Math.round(extras.getDouble("rating"));
        switch (rating) {
            case 0:
                 ratingImage.setImageResource(R.drawable.zero_stars);
                 break;
            case 1:
                 ratingImage.setImageResource(R.drawable.one_star);
                 break;
            case 2:
                 ratingImage.setImageResource(R.drawable.two_stars);
                 break;
            case 3:
                 ratingImage.setImageResource(R.drawable.three_stars);
                 break;
            case 4:
                 ratingImage.setImageResource(R.drawable.four_stars);
                break;
            case 5:
                 ratingImage.setImageResource(R.drawable.five_stars);
                break;
            default:
                 ratingImage.setImageResource(R.drawable.five_stars);
        }

        int currPop = extras.getInt("curr_pop") / 20;
        switch (currPop) {
            case 0:
                populationImage.setImageResource(R.drawable.safe_guy);
                break;
            case 1:
                populationImage.setImageResource(R.drawable.lonely_guy);
                break;
            case 2:
                populationImage.setImageResource(R.drawable.two_guys);
                break;
            case 3:
                populationImage.setImageResource(R.drawable.three_guys);
                break;
            case 4:
                populationImage.setImageResource(R.drawable.four_guys);
                break;
            case 5:
                populationImage.setImageResource(R.drawable.five_guys);
                break;
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
