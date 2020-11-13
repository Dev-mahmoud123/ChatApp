package com.mah_awad.chatapp.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Build;

import androidx.media.app.NotificationCompat;

public class OreoNotification extends ContextWrapper {

    private static final String CHANEL_ID ="com.mah_awad.chatapp";
    private static final String CHANEL_NAME ="chatapp";
    private NotificationManager notificationManager;

    public OreoNotification(Context base) {
        super(base);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChanel();

        }
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void createChanel() {

        NotificationChannel channel = new NotificationChannel(CHANEL_ID,CHANEL_NAME,
                                         NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel);
    }

    public  NotificationManager getManager(){
        if (notificationManager == null){
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return  notificationManager;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder getOreoNotification(String title , String body, PendingIntent pendingIntent,
                                                      Uri soundUri , String icon){
        return  new Notification.Builder(getApplicationContext(),CHANEL_ID)
                .setSmallIcon(Integer.parseInt(icon))
                .setContentIntent(pendingIntent)
                .setContentText(body)
                .setContentTitle(title)
                .setSound(soundUri)
                .setAutoCancel(true);

    }

}
