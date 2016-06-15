package com.bwidlarz.todolist.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bwidlarz.todolist.Database.TaskTable;

/**
 * Created by Dell on 2016-05-25.
 */
public class TaskDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_name = "tasks";
    private static final int DB_version = 3;

    public TaskDatabaseHelper(Context context) {
        super(context, DB_name, null, DB_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        TaskTable.onCreate(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        TaskTable.onUpgrade(db, oldVersion, newVersion);

    }
}
