package com.bwidlarz.todolist.Notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class NotificationStartupReceiver extends BroadcastReceiver {
    int intervalNotification = 1*60*60*1000;
    @Override

    public void onReceive(Context context, Intent intent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent newIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(
                context,
                0,
                newIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, 1);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, now.getTimeInMillis(),intervalNotification, sender);
        Log.d("receiver", "działa");

    }
}
