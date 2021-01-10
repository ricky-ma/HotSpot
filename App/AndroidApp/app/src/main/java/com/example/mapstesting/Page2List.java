package com.example.mapstesting;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class Page2List extends AppCompatActivity {

    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);
        LinearLayout mainLayout = findViewById(R.id.ButtonLayout);
        List<Integer> buttonList = new ArrayList<>();

//        //display some text if there are no restaurants nearby
//        if(!restList.isEmpty){
//            TextView error = new TextView(this);
//            error.setGravity(Gravity.CENTER | Gravity.TOP);
//            error.setText("No Restaurants were Found");
//            mainLayout.addView(error);
//        } else {
//            //creates a new button for each restaurant nearby
//            int i = 0;
//            for (Restaurant r : restList){
//                Button newButton = new Button(this);
//                newButton.setId(i);
//                buttonList.add(newButton.getId());
//                mainLayout.addView(newButton);
//                i++;
//            }
//        }

        //below is temporary code until we can get data from API
//        for (int i = 0; i < 50; i++) {
//
//            Button newButton = new Button(this);
//            newButton.setText("Restaurant " + i);
//
//            newButton.setId(i);
//            buttonList.add(newButton.getId());
//            mainLayout.addView(newButton);
//        }


        for (Integer b : buttonList){
            button = findViewById(b);
//            Restaurant chosen = restList.get(button.getId());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    infoRestaurant(); //delete this line later
//                    infoRestaurant(chosen);
                }
            });
        }
    }

    /**
     * moves to info page (page 3)
     */
//    public void infoRestaurant(Restaurant chosen){
    public void infoRestaurant(){ //delete this line later
        Intent intent = new Intent(this, Page3Info.class);
//        intent.putExtra("Name", chosen.getName());
//        intent.putExtra("curr_pop", chosen.getCurrent_popularity());
//        intent.putExtra("rating", chosen.getRating());
//        intent.putExtra("address", chosen.getRating());
        startActivity(intent);
    }
}