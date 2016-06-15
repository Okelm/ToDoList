package com.bwidlarz.todolist.JSON;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

/**
 * Created by Dell on 2016-06-09.
 */
public class JSONExport {

/*
    private void startExport() throws FileNotFoundException {
        Cursor cursor = setTheCursor();
        if (cursor.moveToFirst()) {
            JSONObject root = new JSONObject();
            JSONArray taskArray = new JSONArray();

           // int i = 0;
            //while (!cursor.isAfterLast()) {
            if (cursor.moveToFirst()) {
                JSONObject JSONtask = new JSONObject();
                int id = cursor.getColumnIndex("_id");
                TaskDao taskdao = setTastDao();
                Task currentTask = taskdao.get(id);
                Gson gson = new Gson();
                String json = gson.toJson(currentTask);
                //TODO serializacja miała pomóc, tak,żeby móżna było całego taska od razu wsadzić do gsona, ale JSONObjectu. Możliwe że w ogóle w tym przypadku nie potrzebuję JSONOb, ani Ar, tylko i wyłącznie kilka razy wywołać gson.tojson kolejno

                //}
            }
        }
    }

    private TaskDao setTastDao() {
        SQLiteOpenHelper taskDatabaseHelper = new TaskDatabaseHelper(this);
        SQLiteDatabase db = taskDatabaseHelper.getReadableDatabase();
        TaskDao taskDao =new TaskDao(db);
        return taskDao;
    }

    private Cursor setTheCursor() {
        SQLiteOpenHelper taskDatabaseHelper = new TaskDatabaseHelper(this);
        SQLiteDatabase db = taskDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.query("TASK",
                new String[]{"TITLE", "DESCRIPTION", "CREATED", "TIME_END", "URL"},
                null,
                null,
                null, null, null);

        return cursor;
    }
    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

*/

}
