package com.bwidlarz.todolist.Notifications;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bwidlarz.todolist.Acitivities.EditingActivity;
import com.bwidlarz.todolist.Acitivities.MainActivity;
import com.bwidlarz.todolist.Database.Task;
import com.bwidlarz.todolist.Database.TaskDao;
import com.bwidlarz.todolist.Database.TaskDatabaseHelper;
import com.bwidlarz.todolist.R;

import java.util.List;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class NotificationHandler extends IntentService {

    public static final String EXTRA_MESSAGE = "message";
    public static final int NOTIF_ID = 3737;


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public NotificationHandler(String name) {
        super(name);
    }
    public NotificationHandler(){
        super("NotificationHandler");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("onHandleIntent", "start");
        synchronized (this) {
            SQLiteOpenHelper taskDatabaseHelper = new TaskDatabaseHelper(getApplicationContext());
            SQLiteDatabase db = taskDatabaseHelper.getReadableDatabase();
            Log.d("onHandleIntent", "db start");
            TaskDao taskDao = new TaskDao(db);
            List<Task> tasks = taskDao.getAll();
            Log.d("onHandleIntent", "list of task");
            //checkForLateDuedates(tasks);
            checkForTodayTask(tasks);
        }
        String text = intent.getStringExtra(EXTRA_MESSAGE);
        //showText(text);
    }

    private void checkForTodayTask(List<Task> tasks) {
        Log.d("checkForTodayTask", " start");
        try {
            int numberOfTasks = 0;
            String exampleOfTitle = null;
            StringBuilder stringBuilder = new StringBuilder();

            for (Task task : tasks) {
                long currentTime = System.currentTimeMillis();
                long endTime = task.getEndTime();
                if ((endTime-currentTime)< 86400000 && (endTime-currentTime)>0) {
                    Log.d("checkForTodayTask", " its Today!");
                    numberOfTasks++;
                    stringBuilder.append(task.getTitle());
                    stringBuilder.append(", ");
                }
            }
            String desc = stringBuilder.toString();
            createDuedateNotification("Tasks for today: " + numberOfTasks, desc);

        }finally {
            AlarmReceiver.releaseLock();
            stopSelf();
        }
    }



    private void checkForLateDuedates(List<Task> tasks) {
        Log.d("checkForLateDuedates", " start");
        try {
            int numberOfTasks = 0;
            long time = System.currentTimeMillis();
            for (Task task : tasks) {
                if (task.getEndTime() < time) {
                    Log.d("checkForLateDuedates", " time end!");
                    //createDuedateNotification(task.getTitle(), task);

                }
            }
        }finally {
            AlarmReceiver.releaseLock();
            stopSelf();
        }
    }

    private void createDuedateNotification(String title, String desc) {
        Log.d("createDuedateNotif", " start");
        Intent intent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            Log.d("createDuedateNotif", " version checked");
            stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(EditingActivity.class);
            stackBuilder.addNextIntent(intent);
            PendingIntent pendingIntent =
                    stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            Log.d("createDuedateNotif", " notif building ....");



            Notification notification = new Notification
                    .Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setAutoCancel(false)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setContentIntent(pendingIntent)
                    .setContentText(desc)
                    .build();
            Log.d("createDuedateNotif", " notif built");
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIF_ID,notification);
        }
    }
}
