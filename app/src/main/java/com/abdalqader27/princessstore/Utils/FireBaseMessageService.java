package com.abdalqader27.princessstore.Utils;
//*
// Is Created By Abd Alqader Alnajjar
// */

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.abdalqader27.princessstore.R;
import com.abdalqader27.princessstore.Models.Notificons_models;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;


public class FireBaseMessageService extends FirebaseMessagingService {
    private static ArrayList<Notificons_models> items;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Map<String, String> params = remoteMessage.getData();
        JSONObject object = new JSONObject(params);

        if (remoteMessage.getData().size() > 0) {
            //---------------------
            SharedPreferences sharedPreferences = getSharedPreferences("SaveFireBase", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            SharedPreferences shared_bdges = getSharedPreferences("Save_badges", MODE_PRIVATE);
            SharedPreferences.Editor editor_badges = shared_bdges.edit();
            getArrayList();
            int count_of_badges = shared_bdges.getInt("count2", 0);


            String Title = object.optString("title");
            String Body = object.optString("message");
            items.add(new Notificons_models(Title, Body));
            saveArrayList();

            count_of_badges++;
            editor_badges.putInt("count2", count_of_badges);
            editor.commit();
            editor_badges.apply();
            //-----------------------

        }


        String NOTIFICATION_CHANNEL_ID = "channel";

        long[] pattern = {0, 1000, 500, 1000};

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Your Notifications",
                    NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.setDescription("");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.setVibrationPattern(pattern);
            notificationChannel.enableVibration(true);
            Objects.requireNonNull(mNotificationManager).createNotificationChannel(notificationChannel);
        }

        // to diaplay notification in DND Mode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = mNotificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID);
            channel.canBypassDnd();
        }
//        Intent notificationIntent = new Intent(getApplicationContext(), Main.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(getApplication(),
//                0, notificationIntent,
//                PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);


        try {
            if (remoteMessage.getNotification() != null) {
                notificationBuilder
                        .setContentTitle(remoteMessage.getNotification().getTitle())
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setStyle(new NotificationCompat.BigTextStyle().
                                bigText(remoteMessage.getNotification().getBody()))
                        .setAutoCancel(true);
                assert mNotificationManager != null;
                mNotificationManager.notify(1756, notificationBuilder.build());
            }
        } catch (Exception ignored) {
        }


    }

    private void saveArrayList() {
        SharedPreferences prefs = getSharedPreferences("save", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(items);
        editor.putString("task", json);
        editor.commit();
        editor.apply();     // This line is IMPORTANT !!!
    }

    private void getArrayList() {
        SharedPreferences prefs = getSharedPreferences("save", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("task", null);
        Type type = new TypeToken<ArrayList<Notificons_models>>() {
        }.getType();
        items = gson.fromJson(json, type);
        if (items == null) {
            items = new ArrayList<>();
        }
    }


}