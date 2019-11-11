package com.tablelayout.javacodegeeks.mallamba;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.util.TimeUtils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(final Context context, Intent intent) {
        //this will update the UI with message
        Log.d("MyActivity", "alarm starting maybe");

        int nbr = 0;
        while(true) {
            nbr++;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {

            }


            Log.d("MyActivity", "End of while loop");
            Log.d("MyActivity", "....... " + nbr);
            if(nbr > 1000)
                break;
        }


        //this will sound the alarm tone
        //this will sound the alarm once, if you wish to
        //raise alarm in loop continuously then use MediaPlayer and setLooping(true)
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();

        //this will send a notification message
        ComponentName comp = new ComponentName(context.getPackageName(),
                AlarmService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}