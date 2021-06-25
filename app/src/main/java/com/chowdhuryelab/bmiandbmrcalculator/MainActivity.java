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

                        Global_Data.gender="Male";
                        Toast.makeText(getApplicationContext(),"Select:-"+radiomale.getText().toString(),Toast.LENGTH_LONG).show();

                        break;
                    case R.id.radiofemale:

                        Global_Data.gender="Female";
                        Toast.makeText(getApplicationContext(),"Select:-"+radiofemale.getText().toString(),Toast.LENGTH_LONG).show();

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
                        btnPopupCALO.setText(item.getTitle());
                        Global_Data.bmiTxt= String.valueOf(btnPopupCALO.getText());
                        return true;
                    }
                });

                popup.show();//showing popup menu


            }

        });


        btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int id = radioattend.getCheckedRadioButtonId();

                    final String ageStr = age.getText().toString();
                    final String weightStr = weight.getText().toString();
                    final String heightFtStr = heightFt.getText().toString();
                    final String heightInStr = heightIn.getText().toString();

                    if (TextUtils.isEmpty(ageStr)) {
                        age.setError("Empty");
                        return;
                    }
                    if (TextUtils.isEmpty(weightStr)) {
                        weight.setError("Empty");
                        return;
                    }
                    if (TextUtils.isEmpty(heightFtStr)) {
                        heightFt.setError("Empty");
                        return;
                    }

                    if (TextUtils.isEmpty(heightInStr)) {
                        heightIn.setText("0");
                        return;
                    }

                    final float ageValue = (float) (Float.parseFloat(ageStr));
                    float heightFtValue = (float) Float.parseFloat(heightFtStr);
                    float heightInValue = (float) Float.parseFloat(heightInStr);
                    final float weightValue = Float.parseFloat(weightStr);

                    // convert height into Me , cm
                    final float heightValue_m = Float.parseFloat(String.format("%.2f", (((heightFtValue * 12.00) + heightInValue) / 39.37)));
                    final float heightValue_Cm = Float.parseFloat(String.format("%.2f", (((heightFtValue * 12.00) + heightInValue) * 2.54)));

                    Global_Data.bmi = weightValue / (heightValue_m * heightValue_m);

                    if ("Male".equals(Global_Data.gender)) {

                        Global_Data.bmr = ((10 * weightValue) + (6.25 * heightValue_Cm) - (5 * ageValue) + 5);

                    } else if ("Female".equals(Global_Data.gender)) {

                        Global_Data.bmr = ((10 * weightValue) + (6.25 * heightValue_Cm) - (5 * ageValue) - 161);
                    }


                    if (id == -1) {
                       // Select Gender

                        toastTextView.setText("Select Gender");
                        //toastImageView.setImageResource(R.drawable.drawable_red_gradient);
                        Toast toast = new Toast(getApplicationContext());
                        toast.setDuration(Toast.LENGTH_LONG); // set the duration for the Toast
                        toast.setView(layout); // set the inflated layout
                        toast.show();
                    }
                    else
                    {
                        // one of the radio buttons is checked
                        if(ageStr!=null && !"".equals(ageStr)&& heightFtStr != null && !"".equals(heightFtStr)
                                && weightStr != null  &&  !"".equals(weightStr)){

                            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                            Global_Data.bmiTxt= String.valueOf(btnPopupCALO.getText());
                            startActivity(intent);

                        }
                    }


                }
            });


        AppUpdateChecker appUpdateChecker=new AppUpdateChecker(this);  //pass the activity in constructure
        appUpdateChecker.checkForUpdate(false); //mannual check false here

    }
}



