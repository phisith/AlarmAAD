package com.example.alarmaad;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotiChannel extends ContextWrapper {
    public static final String channelID = "ChannelID";
    public static final String channelName = "Channel Name";

    private NotificationManager notificationManager;


    public NotiChannel(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){ //if the android verison is higher than 26 need to create the channel for the notification
            createChannel();

        }
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel(){
        NotificationChannel notificationChannel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);

        getManager().createNotificationChannel(notificationChannel);
    }

    NotificationManager getManager(){
        if(notificationManager == null){
            notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return notificationManager;
    }

    public NotificationCompat.Builder getChannelNotification(){ //set the title and the text in the notification
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("Alarm!")
                .setContentText("Wake up broooooooooooo!!!!!!")
                .setSmallIcon(R.mipmap.ic_launcher);
    }

}

