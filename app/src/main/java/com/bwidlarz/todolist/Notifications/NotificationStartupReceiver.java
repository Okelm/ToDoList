package com.bwidlarz.todolist.Notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class NotificationStartupReceiver extends BroadcastReceiver {

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
        now.add(Calendar.MINUTE, 5);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                now.getTimeInMillis(),
                AlarmManager.INTERVAL_HALF_DAY,
                sender);
        Log.d("receiver", "działa");

    }
}
