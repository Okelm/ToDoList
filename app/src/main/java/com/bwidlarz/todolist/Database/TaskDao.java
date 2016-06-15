package com.bwidlarz.todolist.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Dell on 2016-06-05.
 */
public class TaskDao implements Dao<Task> {

    private SQLiteDatabase db;

    public TaskDao(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public long save(Task entity) {
        ContentValues taskValue = new ContentValues();
        taskValue.put("TITLE", entity.getTitle());
        taskValue.put("DESCRIPTION", entity.getDescription());
        taskValue.put("CREATED", entity.getCreated());
        taskValue.put("TIME_END", entity.getEndTime());
        taskValue.put("URL", entity.getImageUrl());
        db.insert(TaskTable.TABLE_NAME, null, taskValue);
        return 0;
    }

    @Override
    public void update(Task entity) {

        ContentValues taskValue = new ContentValues();
        taskValue.put("TITLE", entity.getTitle());
        taskValue.put("DESCRIPTION", entity.getDescription());
        taskValue.put("CREATED", entity.getCreated());
        if (entity.getEndTime() > 0) {
            taskValue.put("TIME_END", entity.getEndTime());
        }
        taskValue.put("URL", entity.getImageUrl());

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

        Cursor cursor = db.query("TASK",
                new String[]{"TITLE", "DESCRIPTION", "CREATED","TIME_END", "URL"},
                "_id = ?",
                new String[]{Long.toString(id)},
                null, null, null);

        Task task = new Task();
        if (cursor.moveToFirst()) {
            task.setProviderId(id);
            task.setTitle(cursor.getString(0));
            task.setDescription(cursor.getString(1));
            task.setCreated(cursor.getLong(2));
            task.setEndTime(cursor.getLong(3));
            task.setImageUrl(cursor.getString(4));
        }
        cursor.close();
        return task;
    }
}
