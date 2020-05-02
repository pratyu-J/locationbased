package com.example.locationbased;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SendEmail extends AppCompatActivity {

    EditText to,msg,sub;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        Bundle b = getIntent().getExtras();
        final double lat = b.getDouble("lat");
        final double lang = b.getDouble("lang");
        Log.d("EMAIL",lat + " " + lang );

        to=findViewById(R.id.txtTo);
        msg=findViewById(R.id.txtMsg);
        msg.setText("Latitude: " + lat + "\t Longitude: " + lang, TextView.BufferType.EDITABLE);
        sub=findViewById(R.id.txtSub);
        sub.setText("Position",TextView.BufferType.EDITABLE);
        send=findViewById(R.id.btnSend);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(Intent.ACTION_SEND);
                it.putExtra(Intent.EXTRA_EMAIL,new String[]{to.getText().toString()});
                it.putExtra(Intent.EXTRA_SUBJECT, sub.getText().toString());
                it.putExtra(Intent.EXTRA_TEXT, msg.getText().toString());
                it.setType("message/rfc822");
                startActivity(Intent.createChooser(it,"Choose Mail App"));
            }
        });
    }
}
