package com.example.locationbased;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReciever extends BroadcastReceiver {
    @Override
    public  void onReceive(Context context, Intent intent)
    {
        Bundle bundle=intent.getExtras();
        SmsMessage[] msg=null;
        String str="SMS From";
        if(bundle!=null)
        {
            Object[] recv=(Object[])bundle.get("pdus");
            msg=new SmsMessage[recv.length];
            for (int i=0;i<msg.length;i++)
            {
                msg[i]= SmsMessage.createFromPdu((byte[])recv[i]);
                if(i==0)
                {
                    str+=msg[i].getOriginatingAddress();
                    str+=":";
                }
                str+=msg[i].getMessageBody().toString();
            }
            Toast.makeText(context,str,Toast.LENGTH_LONG).show();
        }
    }
}
