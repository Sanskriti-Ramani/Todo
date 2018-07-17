package com.example.sanskriti.todoapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
       // throw new UnsupportedOperationException("Not yet implemented");
        long  id = intent.getLongExtra("intent_id", 0);
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("myChannelId", "Expenses Channel", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "myChannelId");
        builder.setContentTitle("Todo");
        builder.setContentText("Get Details");
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        Intent intent1 = new Intent(context, ViewTodoActivity.class);
        intent1.putExtra("id", id);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 2,intent1, 0);

        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        manager.notify(1, notification);

    }
}
