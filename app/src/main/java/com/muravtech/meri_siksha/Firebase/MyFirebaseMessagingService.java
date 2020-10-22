package com.muravtech.meri_siksha.Firebase;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.muravtech.meri_siksha.R;
import com.muravtech.meri_siksha.admin_ui.AdminDashBoardScreenActivity;
import com.muravtech.meri_siksha.student_ui.StudentDashBoardScreenActivity;
import com.muravtech.meri_siksha.teacher_ui.TeacherDashBoardScreenActivity;
import com.muravtech.meri_siksha.utils.AppPreferences;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    AppPreferences appPreferences;
    Intent intent;

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        appPreferences=new AppPreferences(this);
        Log.e(TAG, "onNewToken: "+s);
        appPreferences.setAccessToken(s);


      /*  getSharedPreferences("MERISIKASHA"
                , MODE_PRIVATE).edit().putString("NEW_TOKEN", s).apply();*/
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "onMessageReceived: >>>>>>>>>"+remoteMessage );
        if(appPreferences.getStringValue(AppPreferences.Type).equalsIgnoreCase("9")) {
            intent = new Intent(this, AdminDashBoardScreenActivity.class);

        }else if(appPreferences.getStringValue(AppPreferences.Type).equalsIgnoreCase("3")) {
            intent = new Intent(this, StudentDashBoardScreenActivity.class);

        }else {
            intent = new Intent(this, TeacherDashBoardScreenActivity.class);
        }

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("order_id", remoteMessage.getNotification().getTicker());
        startActivity(intent);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        String channelId = "Default";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody()).
                        setAutoCancel(true).setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new
                    NotificationChannel(channelId, "Default channel",
                    NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());

    }


}
