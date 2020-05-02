package com.example.locationbased;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;

public class MainActivity extends Activity {
    private static final int REQUEST_CODE_PERMISSION = 2;
    Button btnShowLocation, sendEmail, sendSMS;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    Location_Tracker gps;
    Double latitude, longitude;
    TextView display, choice;
    LocationManager locationManager;
    Criteria criteria;
    CheckBox fineacc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        btnShowLocation = (Button) findViewById(R.id.button);
        sendEmail = findViewById(R.id.semail);
        sendSMS = findViewById(R.id.ssms);
        display = findViewById(R.id.disp);
        fineacc = findViewById(R.id.check);
        choice = findViewById(R.id.choice);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);


        // we will show the location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if(fineacc.isChecked()){
                    criteria.setAccuracy(Criteria.ACCURACY_FINE);
                    choice.setText("Fine Accuracy Selected");
                }
                else {
                    criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                    choice.setText("Coarse Accuracy Selected ");
                }
                // we will create a class object
                gps = new Location_Tracker(MainActivity.this);
                // Now we will check if GPS is enabled
                if (gps.canGetLocation(locationManager.getBestProvider(criteria, false))) {
                     latitude = gps.getLatitude();
                     longitude = gps.getLongitude();
                    Toast.makeText(getApplicationContext(), "This is your Location : \nLatitude: " + latitude + "\nLongitude: " + longitude, Toast.LENGTH_LONG).show();
                    display.setText("Your Location is : \nLatitude: " + latitude + "\nLongitude: " + longitude);
                }
                else {
                    gps.showSettings();
                }
            }
        });

    sendEmail.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("position",latitude + " " + longitude );
            Intent intent = new Intent(MainActivity.this, SendEmail.class);
            intent.putExtra("lat", latitude);
            intent.putExtra("lang", longitude);
            startActivity(intent);
        }
    });

    sendSMS.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("position",latitude + " " + longitude );
            Intent intent = new Intent(MainActivity.this, SendSMS.class);
            intent.putExtra("lat", latitude);
            intent.putExtra("lang", longitude);
            startActivity(intent);

        }
    });

    }



}
