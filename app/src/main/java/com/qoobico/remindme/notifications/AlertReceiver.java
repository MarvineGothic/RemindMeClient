package com.qoobico.remindme.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;

import java.util.Objects;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        Bundle bundle = intent.getExtras();
        String title = Objects.requireNonNull(bundle).getString("title");
        String message = Objects.requireNonNull(bundle).getString("message");
        NotificationCompat.Builder builder = notificationHelper.getChannelOneNotification(title, message);
        notificationHelper.getManager().notify(1, builder.build());
    }
}
