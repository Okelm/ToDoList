package com.bwidlarz.todolist.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;


public class AlarmReceiver extends BroadcastReceiver {
    private static PowerManager.WakeLock wakeLock =null;
    private static final String LOCK_TAG = "com.bwidlarz.todolist";
    public static synchronized void aquireLock(Context context){
        if (wakeLock ==null){
            PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,LOCK_TAG);
            wakeLock.setReferenceCounted(true);
        }
        wakeLock.acquire();
    }
    public static synchronized void releaseLock(){
        if(wakeLock!=null){
            wakeLock.release();
        }
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notifIntent = new Intent(context, NotificationHandler.class);
        context.startService(notifIntent);
    }
}
