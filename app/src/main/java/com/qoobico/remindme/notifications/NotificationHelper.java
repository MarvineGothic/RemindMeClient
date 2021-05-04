package com.qoobico.remindme.notifications;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.qoobico.remindme.R;

public class NotificationHelper extends ContextWrapper {
    public static final String channel1ID = "channel1ID";
    public static final String channel1Name = "Channel 1";
    public static final String channel2ID = "channel2ID";
    public static final String channel2Name = "Channel 2";
    public NotificationManager mManager = null;


    public NotificationHelper(Context base) {
        super(base);
        initChannels();
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void initChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel_1 = new NotificationChannel(channel1ID, channel1Name, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel_1.enableLights(true);
            notificationChannel_1.enableVibration(true);
            notificationChannel_1.setLightColor(R.color.colorPrimary);
            notificationChannel_1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            getManager().createNotificationChannel(notificationChannel_1);

            NotificationChannel notificationChannel_2 = new NotificationChannel(channel2ID, channel2Name, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel_2.enableLights(true);
            notificationChannel_2.enableVibration(true);
            notificationChannel_2.setLightColor(R.color.colorPrimary);
            notificationChannel_2.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            getManager().createNotificationChannel(notificationChannel_2);
        }
    }

    public NotificationManager getManager(){
        if (mManager == null){
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public NotificationCompat.Builder getChannelOneNotification(String title, String message){
        return new NotificationCompat.Builder(getApplicationContext(), channel1ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_one);
    }

    public NotificationCompat.Builder getChannelTwoNotification(String title, String message){
        return new NotificationCompat.Builder(getApplicationContext(), channel2ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_two);
    }
}
