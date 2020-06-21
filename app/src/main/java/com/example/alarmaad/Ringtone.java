package com.example.alarmaad;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class Ringtone extends Service { // this is the class for the alarm song
    private MediaPlayer mediaPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void onCreate(){
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.wakeup); // set the alarm music or song
        mediaPlayer.setLooping(false);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
            mediaPlayer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy(){
        mediaPlayer.stop();

    }
}
