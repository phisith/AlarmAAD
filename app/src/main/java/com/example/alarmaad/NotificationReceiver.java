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
    public void onReceive(Context context, Intent intent) {
        NotiChannel notiChannel = new NotiChannel(context);
        NotificationCompat.Builder nb = notiChannel.getChannelNotification();
        notiChannel.getManager().notify(1, nb.build());

        Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] mVibratePattern = new long[]{0, 400, 1000, 600, 1000, 800, 1000, 1000};
        VibrationEffect effect = VibrationEffect.createWaveform(mVibratePattern, 0);
        vibrator.vibrate(effect);

        if (vibrator.hasVibrator()) {
            Log.v("Can Vibrate", "YES");
        } else {
            Log.v("Can Vibrate", "NO");
        }

        //vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));





        //Notification notification = new Notification.Builder(context)
              //  .setContentTitle("Alarm is On")
            //    .setContentText("You had set up the alarm")
          //      .setSmallIcon(R.mipmap.ic_launcher).build();

        //NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        //notification.flags|= Notification.FLAG_AUTO_CANCEL;
        //notificationManager.notify(0, notification);

        Uri RingRing = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

        Ringtone ringtone = RingtoneManager.getRingtone(context,RingRing);
        ringtone.play();


    }



}
