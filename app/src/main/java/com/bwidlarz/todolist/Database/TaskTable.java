package com.bwidlarz.todolist.Database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by Dell on 2016-06-05.
 */
public class TaskTable {

    public static final String TABLE_NAME = "TASK";




    public static class TaskColumns implements BaseColumns{
        public static final  String TITLE = "title";
        public static final  String DESCRIPTION = "description";
        public static final  String CREATED ="created";
        public static final  String TIME_END = "endTime";
        public static final  String URL = "imageUrl";
    }

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE TASK (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "TITLE TEXT, "
                + "DESCRIPTION TEXT, "
                + "CREATED DATETIME DEFAULT CURRENT_TIMESTAMP, "
                + "TIME_END TEXT, "
                + "URL TEXT);");
        insertTask(db, "Run for a while", "Need to run a bit, a bit longer than yesterday",
                System.currentTimeMillis(), "1420917972", "https://j7w7h8q2.ssl.hwcdn.net/achievements/ach_ipad/11.10.png");
        insertTask(db, "Refactor the code", "Looks messy, do something with it",
                System.currentTimeMillis(), "1520917972", "https://j7w7h8q2.ssl.hwcdn.net/achievements/ach_ipad/11.10.png");
        insertTask(db, "Have a walk", "Stop staring at the monitor and have some walk",
                System.currentTimeMillis(), "33620917972", "https://j7w7h8q2.ssl.hwcdn.net/achievements/ach_ipad/11.10.png");


    }

    private static void insertTask(SQLiteDatabase db, String title, String description, long created, String endTime, String url) {

        ContentValues taskValue = new ContentValues();
        taskValue.put("TITLE",title);
        taskValue.put("DESCRIPTION",description);
       // taskValue.put("CREATED",created);
        taskValue.put("TIME_END",endTime);
        taskValue.put("URL",url);
        db.insert("TASK",null, taskValue);

    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "
                + TaskTable.TABLE_NAME);
        TaskTable.onCreate(db);
    }

}
