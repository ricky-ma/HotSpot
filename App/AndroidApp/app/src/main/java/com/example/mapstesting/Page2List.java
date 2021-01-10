package com.example.mapstesting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Page2List extends AppCompatActivity {

    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);

        //creates a new button for each restaurant nearby
        //TODO: have restList contain Restaurant info from API
//        for (Restaurant r : restList){
//            LinearLayout mainLayout = findViewById(R.id.ButtonLayout);
//
//            Button newButton = new Button(this);
//            newButton.setText(r.getName + "   " + r.getPop);
//
//            mainLayout.addView(newButton);
//        }

//        //display some text if there are no restaurants nearby
//        if(!restList.isEmpty){
//
//        }

        List<Integer> buttonList = new ArrayList<>();

        //below is temporary code until we can get data from API
        for (int i = 0; i < 50; i++) {

            LinearLayout mainLayout = findViewById(R.id.ButtonLayout);

            Button newButton = new Button(this);
            newButton.setText("Restaurant " + i);

            newButton.setId(i);
            buttonList.add(newButton.getId());
            mainLayout.addView(newButton);
        }


        for (Integer b : buttonList){
            button = findViewById(b);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    infoRestaurant();
                }
            });
        }
    }

    /**
     * moves to info page (page 3)
     */
    public void infoRestaurant(){
        Intent intent = new Intent(this, Page3Info.class);
        startActivity(intent);
    }
}