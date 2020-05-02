package com.example.locationbased;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SendSMS extends AppCompatActivity {

    EditText mobileno,message;
    Button sendsms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);
        mobileno=(EditText)findViewById(R.id.editTextPhoneNo);
        message=(EditText)findViewById(R.id.editTextSMS);
        sendsms=(Button)findViewById(R.id.buttonSend);
        Bundle b = getIntent().getExtras();
        final double lat = b.getDouble("lat");
        final double lang = b.getDouble("lang");
        Log.d("SMS",lat + " " + lang );
        message.setText("Latitude: " + lat + "\t Longitude: " + lang, TextView.BufferType.EDITABLE);

        try{
            if(ActivityCompat.checkSelfPermission(SendSMS.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(SendSMS.this, new String[] {Manifest.permission.SEND_SMS}, 1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //Performing action on button click
        sendsms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String no=mobileno.getText().toString();
                String msg = "Latitude: " + lat + "\t Longitude: " + lang;

                //Getting intent and PendingIntent instance
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

                //Get the SmsManager instance and call the sendTextMessage method to send message



                SmsManager sms=SmsManager.getDefault();
                sms.sendTextMessage(no, null, msg, pi,null);

                Toast.makeText(getApplicationContext(), "Message Sent successfully!",
                        Toast.LENGTH_LONG).show();
            }
        });


    }
}
