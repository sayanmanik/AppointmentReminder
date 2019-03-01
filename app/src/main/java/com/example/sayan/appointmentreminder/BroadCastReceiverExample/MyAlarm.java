package com.example.sayan.appointmentreminder.BroadCastReceiverExample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.provider.Settings;
import android.support.v4.media.app.NotificationCompat;

import com.example.sayan.appointmentreminder.Activity.MainActivity;
import com.example.sayan.appointmentreminder.R;

/**
 * Created by 1605476 and 24-May-18
 **/
public class MyAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


            NotificationManager notificationManager=
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            Notification.Builder builder = new Notification.Builder(context)
                    .setSmallIcon(R.drawable.ic_watch_later_black_24dp)
                    //example for large icon
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                    .setContentTitle("my title")
                    .setContentText("my message")
                    .setOngoing(false)
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                    ;


            Intent intent1=new Intent(context, MainActivity.class);
            PendingIntent pendingIntent=
                    PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_ONE_SHOT);

            builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            builder.setContentIntent(pendingIntent);
            notificationManager.notify(0,builder.build());


           MediaPlayer mediaPlayer=
                MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.start();
        }
    }
