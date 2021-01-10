package com.example.mapstesting;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ExtraSettings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    int counter = 0;
    int dayOfWeek;
    int hour;
    boolean avg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_settings);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        if (counter >= 2) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("dayOfWeek", dayOfWeek);
            returnIntent.putExtra("hour", hour);
            returnIntent.putExtra("avg", avg);
            setResult(RESULT_OK, returnIntent);
            finish();
        }
        if (parent.getItemAtPosition(pos) instanceof Integer) {
            counter++;
            this.hour = (int) parent.getItemAtPosition(pos);
            return;
        }
        String choice = parent.getItemAtPosition(pos).toString();
        switch(choice) {
            case "Sunday":
                dayOfWeek = 1;
                break;
            case "Monday":
                dayOfWeek = 2;
                break;
            case "Tuesday":
                dayOfWeek = 3;
                break;
            case "Wednesday":
                dayOfWeek = 4;
                break;
            case "Thursday":
                dayOfWeek = 5;
                break;
            case "Friday":
                dayOfWeek = 6;
                break;
            case "Saturday":
                dayOfWeek = 7;
                break;
        }
        counter++;
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }
}
