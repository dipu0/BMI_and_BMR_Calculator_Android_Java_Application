package com.chowdhuryelab.bmiandbmrcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class MainActivity extends AppCompatActivity {
    private EditText age;
    private EditText heightFt,heightIn;
    private EditText weight;

    Button btnPopup,btn,btnPopupCALO;
    private AdView myAdView_1;

    private double bmi;
    private double bmr;

    private String bmiTxt;
    private String gender;

    RadioButton radiomale,radiofemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        myAdView_1 = findViewById(R.id.myAdView_1);
        AdRequest adRequest = new AdRequest.Builder().build();
        myAdView_1.loadAd(adRequest);

        age = (EditText) findViewById(R.id.age);
        heightFt = (EditText) findViewById(R.id.heightFt);
        heightIn = (EditText) findViewById(R.id.heightIn);
        weight = (EditText) findViewById(R.id.weight);
        btnPopupCALO = (Button) findViewById(R.id.calo);
        btn = (Button) findViewById(R.id.calc);
        radiomale=(RadioButton)findViewById(R.id.radiomale);
        radiofemale=(RadioButton)findViewById(R.id.radiofemale);

        final RadioGroup radioattend=(RadioGroup)findViewById(R.id.radioattendgroup);

        LayoutInflater inflater = getLayoutInflater(); // Retrieve the Layout Inflater and inflate the layout from xml
        final View layout = inflater.inflate(R.layout.custom_toast_layout,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        final TextView toastTextView = (TextView) layout.findViewById(R.id.toastTextView); // get the reference of TextView and ImageVIew from inflated layout
       // final ImageView toastImageView = (ImageView) layout.findViewById(R.id.toastImageView);

        radioattend.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radiomale:

                        gender="Male";
                        Toast.makeText(getApplicationContext(),"Select:-"+radiomale.getText().toString(),Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.radiofemale:

                        gender="Female";
                        Toast.makeText(getApplicationContext(),"Select:-"+radiofemale.getText().toString(),Toast.LENGTH_SHORT).show();

                        break;
                    default:
                        break;
                }
            }
        });


        btnPopupCALO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(MainActivity.this,btnPopupCALO);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu_calorie, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        bmiTxt= String.valueOf(btnPopupCALO.getText());
                        btnPopupCALO.setText(item.getTitle());
                        return true;
                    }
                });

                popup.show();//showing popup menu


            }

        });


        btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {

                    boolean error = false;

                    int id = radioattend.getCheckedRadioButtonId();


                    String ageStr = age.getText().toString();
                    String weightStr = weight.getText().toString();
                    String heightFtStr = heightFt.getText().toString();
                    String heightInStr = heightIn.getText().toString();

                    if (TextUtils.isEmpty(ageStr) || Integer.valueOf(ageStr) <= 0 || Integer.valueOf(ageStr) >= 150) {
                        age.setError("Error");
                        error = true;
                        //return;
                    }

                    if (TextUtils.isEmpty(weightStr) || Double.valueOf(weightStr) <= 0 || Double.valueOf(weightStr) >= 1000) {
                        weight.setError("Error");
                        error = true;
                        //return;
                    }
                    if (TextUtils.isEmpty(heightFtStr) || Integer.valueOf(heightFtStr) <= 0) {
                        heightFt.setError("Error");
                        error = true;
                        //return;
                    }

                    if (TextUtils.isEmpty(heightInStr) || Integer.valueOf(heightInStr) < 0 || Integer.valueOf(heightInStr) > 12) {
                        heightIn.setError("Error");
                        heightIn.setText("0");
                        //error = true;
                        //return;
                    }
                    if (id == -1) {
                        // Select Gender
                        error = true;
                        toastTextView.setText("Select Gender");
                        //toastImageView.setImageResource(R.drawable.drawable_red_gradient);
                        Toast toast = new Toast(getApplicationContext());
                        toast.setDuration(Toast.LENGTH_SHORT); // set the duration for the Toast
                        toast.setView(layout); // set the inflated layout
                        toast.show();
                    }

                    if (error == false) {


                        int ageValue = (int) Integer.parseInt(ageStr);
                        double heightFtValue = (double) Float.parseFloat(heightFtStr);
                        double heightInValue = (double) Float.parseFloat(heightInStr);
                        double weightValue = (double) Float.parseFloat(weightStr);

                        // convert height into Me , cm
                        float heightValue_m = (float) ((((heightFtValue * 12.0) + heightInValue) / 39.37));
                        float heightValue_Cm = (float) ((((heightFtValue * 12.00) + heightInValue) * 2.54));

                        bmi = weightValue / Math.pow(heightValue_m, 2);

                        if ("Male".equals(gender)) {

                            //Harris-Benedict(Revised)
                            bmr = 88.362 + (13.397 * weightValue) + (4.799 * heightValue_Cm) - (5.667 * ageValue);

                            //Mifflin- St Jeor
                            //bmr = ((10 * weightValue) + (6.25 * heightValue_Cm) - (5 * ageValue) + 5);

                        } else if ("Female".equals(gender)) {

                            //Harris-Benedict(Revised)
                            bmr = 447.593 + (9.247 * weightValue) + (3.098 * heightValue_Cm) - (4.330 * ageValue);

                            //Mifflin- St Jeor
                            //bmr = ((10 * weightValue) + (6.25 * heightValue_Cm) - (5 * ageValue) - 161);
                        }

                        if (ageStr != null && !ageStr.isEmpty() && heightFtStr != null && !heightFtStr.isEmpty() && weightStr != null && !weightStr.isEmpty()) {

                            bmiTxt = btnPopupCALO.getText().toString();

                            Intent intent = new Intent(MainActivity.this, Main2Activity.class);

                            intent.putExtra("BMI", bmi);
                            intent.putExtra("BMR", bmr);
                            intent.putExtra("Activity", bmiTxt);
                            intent.putExtra("GENDER", gender);

                            startActivity(intent);
                        }


                    }
                }catch(NullPointerException e){
                        System.out.print("NullPointerException caught");
                        Toast.makeText(getApplicationContext(),"Provide valid information",Toast.LENGTH_LONG).show();
                    }
                catch (NumberFormatException e){
                        System.out.println("NumberFormatException caught");
                        Toast.makeText(getApplicationContext(),"Provide valid information",Toast.LENGTH_LONG).show();
                    }

                }
            });

    }
}



