package com.example.alarmaad;

import android.accessibilityservice.AccessibilityService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) { // this is the connector between the alarm function the notification function
        NotiChannel notiChannel = new NotiChannel(context);
        NotificationCompat.Builder nb = notiChannel.getChannelNotification(); //to get the notification start
        notiChannel.getManager().notify(1, nb.build());

        Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE); // get the vibrator start
        long[] mVibratePattern = new long[]{0, 250, 800, 600, 1000, 800, 1000, 1000}; // set the pattern of the vibrate
        VibrationEffect effect = VibrationEffect.createWaveform(mVibratePattern, 0);
        vibrator.vibrate(effect);

        if (vibrator.hasVibrator()) { // to check that the device got the vibrator
            Log.v("Can Vibrate", "YES");
        } else {
            Log.v("Can Vibrate", "NO");
        }






    }



}
