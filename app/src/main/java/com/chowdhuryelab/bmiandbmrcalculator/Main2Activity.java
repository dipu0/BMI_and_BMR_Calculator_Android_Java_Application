package com.chowdhuryelab.bmiandbmrcalculator;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Main2Activity extends AppCompatActivity {

    private TextView resultBMI;
    private TextView resultBMR;
    private ImageView img;
    private TextView calories_per_day;
    String BMR;

    private AdView myAdView_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        resultBMI = (TextView) findViewById(R.id.resultBMI);
        resultBMR = (TextView) findViewById(R.id.resultBMR);
        img = (ImageView) findViewById(R.id.img);
        calories_per_day = (TextView) findViewById(R.id.calories_per_day);
        displayBMI(Global_Data.bmi);
        Calculating_calorie(Global_Data.bmr);

        myAdView_2 = findViewById(R.id.myAdView_2);
        AdRequest adRequest = new AdRequest.Builder().build();
        myAdView_2.loadAd(adRequest);
    }


    private void displayBMI(float bmi) {
        String bmiLabel = "";

        if (Float.compare(bmi, 15f) <= 0) {
            bmiLabel = getString(R.string.very_severely_underweight);
            resultBMI.setTextColor(Color.MAGENTA);
            img.setImageResource(R.drawable.img1);
        } else if (Float.compare(bmi, 15f) > 0 && Float.compare(bmi, 16f) <= 0) {
            img.setImageResource(R.drawable.img1);
            bmiLabel = getString(R.string.severely_underweight);
            resultBMI.setTextColor(Color.MAGENTA);
        } else if (Float.compare(bmi, 16f) > 0 && Float.compare(bmi, 18.5f) <= 0) {
            img.setImageResource(R.drawable.img1);
            bmiLabel = getString(R.string.underweight);
            resultBMI.setTextColor(Color.MAGENTA);
        } else if (Float.compare(bmi, 18.5f) > 0 && Float.compare(bmi, 25f) <= 0) {
            img.setImageResource(R.drawable.img2);
            bmiLabel = getString(R.string.normal);
            resultBMI.setTextColor(Color.GREEN);
        } else if (Float.compare(bmi, 25f) > 0 && Float.compare(bmi, 30f) <= 0) {
            img.setImageResource(R.drawable.img3);
            bmiLabel = getString(R.string.overweight);
            resultBMI.setTextColor(Color.parseColor("#CCCC00"));
        } else if (Float.compare(bmi, 30f) > 0 && Float.compare(bmi, 35f) <= 0) {
            img.setImageResource(R.drawable.img4);
            bmiLabel = getString(R.string.obese_class_i);
            resultBMI.setTextColor(Color.RED);
        } else if (Float.compare(bmi, 35f) > 0 && Float.compare(bmi, 40f) <= 0) {
            img.setImageResource(R.drawable.img4);
            bmiLabel = getString(R.string.obese_class_ii);
            resultBMI.setTextColor(Color.RED);
        } else {
            img.setImageResource(R.drawable.img4);
            bmiLabel = getString(R.string.obese_class_iii);
            resultBMI.setTextColor(Color.RED);
        }

        bmiLabel = "BMI= " + bmi + "\n" + bmiLabel;
        resultBMI.setText(bmiLabel);
    }

    private void Calculating_calorie(double bmr) {
        BMR= String.valueOf(bmr);

        if (Global_Data.bmiTxt.equals("No exercise or little exercise")) {

            resultBMR.setText("BMR= " + String.format("%.2f", bmr));
            calories_per_day.setText("Calories per day: " + String.format("%.2f", bmr * 1.2)+" cal for "+Global_Data.bmiTxt);

        } else if (Global_Data.bmiTxt.equals("Sports or light exercise 1-3 days/week")) {
            resultBMR.setText("BMR= " + String.format("%.2f", bmr));
            calories_per_day.setText("Calories per day: " + String.format("%.2f", bmr * 1.375)+" cal for "+Global_Data.bmiTxt);

        } else if (Global_Data.bmiTxt.equals("Average exercise 3-5 days/week")) {
            resultBMR.setText("BMR= " + String.format("%.2f", bmr));
            calories_per_day.setText("Calories per day: " + String.format("%.2f", bmr * 1.55)+" cal for "+Global_Data.bmiTxt);

        } else if (Global_Data.bmiTxt.equals("Full week hard exercise")) {
            resultBMR.setText("BMR= " + String.format("%.2f", bmr));
            calories_per_day.setText("Calories per day: " + String.format("%.2f", bmr * 1.725)+" cal for "+Global_Data.bmiTxt);

        } else if (Global_Data.bmiTxt.equals("A physical job and very hard exercise every day")) {
            resultBMR.setText("BMR= " + String.format("%.2f", bmr));
            calories_per_day.setText("Calories per: " + String.format("%.2f", bmr * 1.9)+" cal for "+Global_Data.bmiTxt);

        } else if (Global_Data.bmiTxt.equals("Your daily Activity level")) {
            resultBMR.setText("BMR= " + String.format("%.2f", bmr));
            calories_per_day.setTextColor(Color.RED);
            calories_per_day.setText("Set your activity to get per day Calories");

        }

        Button privacyPolicyButton = (Button) this.findViewById(R.id.privacyPolicyButton);
        privacyPolicyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent myintent = new Intent(Main2Activity.this,PrivacyPolicyActivity.class);
                startActivity(myintent);
            }
        });
    }

}
