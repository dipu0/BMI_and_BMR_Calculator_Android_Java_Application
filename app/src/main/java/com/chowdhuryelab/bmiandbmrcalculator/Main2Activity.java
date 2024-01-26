package com.chowdhuryelab.bmiandbmrcalculator;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Main2Activity extends AppCompatActivity {

    private TextView resultBMI;
    private TextView resultBMR;
    private ImageView img;
    private TextView calories_per_day,calories_summary;


    private String bmiTxt;
    private String activities_calories;

    private double bmi;
    private double bmr;



    private AdView myAdView_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        resultBMI = (TextView) findViewById(R.id.resultBMI);
        resultBMR = (TextView) findViewById(R.id.resultBMR);
        img = (ImageView) findViewById(R.id.img);
        calories_per_day = (TextView) findViewById(R.id.calories_per_day);
        calories_summary = (TextView) findViewById(R.id.calories_summary);

        myAdView_2 = findViewById(R.id.myAdView_2);

        AdRequest adRequest = new AdRequest.Builder().build();
        myAdView_2.loadAd(adRequest);


        Bundle b = getIntent().getExtras();
        if(b != null){
            bmi = b.getDouble("BMI",0);
            bmr = b.getDouble("BMR",0);
            bmiTxt = b.getString("Activity","Your daily Activity level");
            //System.out.println("BMI:"+bmi +"      BMR"+ bmr+ "     BMR text :"+bmiTxt);
        }

        displayBMI(bmi);
        Calculating_calorie(bmr, bmiTxt);
    }


    private void displayBMI(double bmi) {

        try
            {
                String bmiLabel = "";

                if (Double.compare(bmi, 15f) <= 0) {
                    bmiLabel = getString(R.string.very_severely_underweight);
                    resultBMI.setTextColor(Color.MAGENTA);
                    img.setImageResource(R.drawable.img1);
                } else if (Double.compare(bmi, 15f) > 0 && Double.compare(bmi, 16f) <= 0) {
                    img.setImageResource(R.drawable.img1);
                    bmiLabel = getString(R.string.severely_underweight);
                    resultBMI.setTextColor(Color.MAGENTA);
                } else if (Double.compare(bmi, 16f) > 0 && Double.compare(bmi, 18.5f) <= 0) {
                    img.setImageResource(R.drawable.img1);
                    bmiLabel = getString(R.string.underweight);
                    resultBMI.setTextColor(Color.MAGENTA);
                } else if (Double.compare(bmi, 18.5f) > 0 && Double.compare(bmi, 25f) <= 0) {
                    img.setImageResource(R.drawable.img2);
                    bmiLabel = getString(R.string.normal);
                    resultBMI.setTextColor(Color.GREEN);
                } else if (Double.compare(bmi, 25f) > 0 && Double.compare(bmi, 30f) <= 0) {
                    img.setImageResource(R.drawable.img3);
                    bmiLabel = getString(R.string.overweight);
                    resultBMI.setTextColor(Color.parseColor("#CCCC00"));
                } else if (Double.compare(bmi, 30f) > 0 && Double.compare(bmi, 35f) <= 0) {
                    img.setImageResource(R.drawable.img4);
                    bmiLabel = getString(R.string.obese_class_i);
                    resultBMI.setTextColor(Color.RED);
                } else if (Double.compare(bmi, 35f) > 0 && Double.compare(bmi, 40f) <= 0) {
                    img.setImageResource(R.drawable.img4);
                    bmiLabel = getString(R.string.obese_class_ii);
                    resultBMI.setTextColor(Color.RED);
                } else {
                    img.setImageResource(R.drawable.img4);
                    bmiLabel = getString(R.string.obese_class_iii);
                    resultBMI.setTextColor(Color.RED);
                }

                bmiLabel = "BMI= " + String.format("%.2f", bmi) + " (" + bmiLabel + ")";
                resultBMI.setText(bmiLabel);

            }catch(NullPointerException e){
                System.out.print("NullPointerException caught");
                Toast.makeText(getApplicationContext(),"Something wrong happened",Toast.LENGTH_SHORT).show();
            }
                catch (NumberFormatException e){
                System.out.println("NumberFormatException caught");
                Toast.makeText(getApplicationContext(),"something wrong happened",Toast.LENGTH_SHORT).show();
            }
    }

    private void Calculating_calorie(double bmr, String bmiTxt) {
        try {

            if (bmiTxt.equals("No exercise or little exercise")) {

                resultBMR.setText("BMR= " + String.format("%.2f", bmr));
                activities_calories = String.format("%.2f", bmr * 1.2);

                calories_per_day.setText("Maintenance calories per day:\n" + activities_calories + " cal");

                calories_summary.setText("Summary: Your body will burn " + String.format("%.2f", bmr) + " calories each day to perform necessary functions." +
                        " The estimate for maintaining your current weight (based upon your chosen activity level: " + bmiTxt + ") is " + activities_calories + " calories." +
                        " This calculation used the Revised Harris-Benedict equation.");

            } else if (bmiTxt.equals("Sports or light exercise 1-3 days/week")) {
                resultBMR.setText("BMR= " + String.format("%.2f", bmr));
                activities_calories = String.format("%.2f", bmr * 1.375);

                calories_per_day.setText("Maintenance calories per day:\n" + activities_calories + " cal");

                calories_summary.setText("Summary: Your body will burn " + String.format("%.2f", bmr) + " calories each day to perform necessary functions." +
                        " The estimate for maintaining your current weight (based upon your chosen activity level: " + bmiTxt + ") is " + activities_calories + " calories." +
                        " This calculation used the Revised Harris-Benedict equation.");

            } else if (bmiTxt.equals("Average exercise 3-5 days/week")) {
                resultBMR.setText("BMR= " + String.format("%.2f", bmr));
                activities_calories = String.format("%.2f", bmr * 1.55);

                calories_per_day.setText("Maintenance calories per day:\n" + activities_calories + " cal");

                calories_summary.setText("Summary: Your body will burn " + String.format("%.2f", bmr) + " calories each day to perform necessary functions." +
                        " The estimate for maintaining your current weight (based upon your chosen activity level: " + bmiTxt + ") is " + activities_calories + " calories." +
                        " This calculation used the Revised Harris-Benedict equation.");

            } else if (bmiTxt.equals("Full week hard exercise")) {
                resultBMR.setText("BMR= " + String.format("%.2f", bmr));
                activities_calories = String.format("%.2f", bmr * 1.725);

                calories_per_day.setText("Maintenance calories per day:\n" + activities_calories + " cal");

                calories_summary.setText("Summary: Your body will burn " + String.format("%.2f", bmr) + " calories each day to perform necessary functions." +
                        " The estimate for maintaining your current weight (based upon your chosen activity level: " + bmiTxt + ") is " + activities_calories + " calories." +
                        " This calculation used the Revised Harris-Benedict equation.");

            } else if (bmiTxt.equals("A physical job and very hard exercise every day")) {
                resultBMR.setText("BMR= " + String.format("%.2f", bmr));
                activities_calories = String.format("%.2f", bmr * 1.9);

                calories_per_day.setText("Maintenance calories per day:\n" + activities_calories + " cal");

                calories_summary.setText("Summary: Your body will burn " + String.format("%.2f", bmr) + " calories each day to perform necessary functions." +
                        " The estimate for maintaining your current weight (based upon your chosen activity level: " + bmiTxt + ") is " + activities_calories + " calories." +
                        " This calculation used the Revised Harris-Benedict equation.");

            } else if (bmiTxt.equals("Your daily Activity level")) {
                resultBMR.setText("BMR= " + String.format("%.2f", bmr));
                calories_per_day.setTextColor(Color.RED);
                calories_per_day.setText("Set your activity to get per day Calories");

            }
        }
        catch(NullPointerException e){
        System.out.print("NullPointerException caught");
        Toast.makeText(getApplicationContext(),"Something wrong happened",Toast.LENGTH_SHORT).show();
        }
        catch (NumberFormatException e){
        System.out.println("NumberFormatException caught");
        Toast.makeText(getApplicationContext(),"something wrong happened",Toast.LENGTH_SHORT).show();
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
