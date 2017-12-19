package com.hoarom.ezMobile.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hoarom.ezMobile.R;
import com.hoarom.ezMobile.activities.DetailActivity;

import static com.hoarom.ezMobile.Settings.STRING_API;
import static com.hoarom.ezMobile.Settings.STRING_COMPANY_NAME;
import static com.hoarom.ezMobile.Settings.VNI;
import static com.hoarom.ezMobile.api.api.APIS;

public class SmsFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.w("MessagingService", "onMessageReceived: " + remoteMessage.getFrom());
        if(remoteMessage.getData().size() > 0){
            Log.w("MessagingService", "onMessageReceived: " + remoteMessage.getData());
            /*check if data needs to be precessed by long running job*/
            if(true){
                scheduleJob();
            }else{
                handleNow();
            }
        }
        if(remoteMessage.getNotification() != null){
            Log.w("MessagingService", "onMessageReceived: " + remoteMessage.getNotification().getBody());

            showNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle());
        }
    }

    private  void scheduleJob(){
        Log.w("MessagingService", "scheduleJob: ");
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder().setTag(getString(R.string.app_name)).build();
//                .setService(SmsJobService.class)
//                .setTag("my-job-tag")
//                .build();
        dispatcher.schedule(myJob);

        // [END dispatch_job]
    }
    private  void handleNow(){
        Log.w("MessagingService", "handleNow: short lived task is done");
    }

    private void showNotification(String messageBody, String content) {
        Log.w("MessagingService", "showNotification: ");
        Intent intent = new Intent(this, DetailActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable(STRING_API, APIS.get(1));
        bundle.putSerializable(STRING_COMPANY_NAME, VNI);

        intent.putExtras(bundle);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, "0")
                        .setSmallIcon(R.drawable.icon_notification_sms)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(messageBody)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(content))
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
