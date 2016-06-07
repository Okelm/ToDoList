package com.bwidlarz.todolist;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Dell on 2016-06-05.
 */
public class TaskDao implements Dao<Task> {

    private SQLiteDatabase db;

    public TaskDao(SQLiteDatabase db){
        this.db=db;
    }

    @Override
    public long save(Task entity) {
        ContentValues taskValue = new ContentValues();
        taskValue.put("TITLE",entity.getTitle());
        taskValue.put("DESCRIPTION",entity.getDescription());
        taskValue.put("CREATED",entity.getCreated());
        taskValue.put("TIME_END",entity.getEndTime());
        taskValue.put("URL",entity.getImageUrl());
        db.insert(TaskTable.TABLE_NAME,null, taskValue);
        return 0;
    }

    @Override
    public void update(Task entity) {

        ContentValues taskValue = new ContentValues();
        taskValue.put("TITLE",entity.getTitle());
        taskValue.put("DESCRIPTION",entity.getDescription());
        taskValue.put("CREATED",entity.getCreated());
        taskValue.put("TIME_END",entity.getEndTime());
        taskValue.put("URL",entity.getImageUrl());

        db.update(TaskTable.TABLE_NAME,
                taskValue,
                "_id = ?",
                new String[]{Long.toString(entity.getProviderId())});

    }

    @Override
    public void delete(Task entity) {
        db.delete(TaskTable.TABLE_NAME,
                "_id = ?",
                new String[]{Long.toString(entity.getProviderId())});
    }

    @Override
    public Task get(long id) {
        return null;
    }
}
