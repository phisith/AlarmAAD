package com.example.alarmaad;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

public class NotificationReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(300);

        Notification notification = new Notification.Builder(context)
                .setContentTitle("Alarm is On")
                .setContentText("You had set up the alarm")
                .setSmallIcon(R.mipmap.ic_launcher).build();

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notification.flags|= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);

        Uri RingRing = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

        Ringtone ringtone = RingtoneManager.getRingtone(context,RingRing);
        ringtone.play();


    }
}
