package com.bwidlarz.todolist.Acitivities;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bwidlarz.todolist.Database.Task;
import com.bwidlarz.todolist.Database.TaskDao;
import com.bwidlarz.todolist.Database.TaskTable;
import com.bwidlarz.todolist.Dialogs.DeletingAlert_Fragment;
import com.bwidlarz.todolist.JSON.JSON;
import com.bwidlarz.todolist.R;
import com.bwidlarz.todolist.Database.TaskDatabaseHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity
        implements DeletingAlert_Fragment.NoticeDialogListener{

    static String fileName = "myBlog.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        setFABClickListener(fab);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menumain, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.JSONExport_item:

                    new ExportJson().execute();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setFABClickListener(FloatingActionButton fab) {
        try{
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startEditingAcitivity();
                }
            });
        }
        catch (NullPointerException e){
            showToast("FAB nie działa");
        }
    }

    private void showToast(String textToShow) {
        Toast toast = Toast.makeText(this, textToShow, Toast.LENGTH_LONG);
        toast.show();
    }


    private void startEditingAcitivity() {
        Intent intent = new Intent(MainActivity.this, EditingActivity.class);
        intent.putExtra(EditingActivity.EXTRA_INFO, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        SQLiteOpenHelper taskDatabaseHelper = new TaskDatabaseHelper(getApplicationContext());
        try{

            Task taskEntity = new Task();
            SQLiteDatabase db = taskDatabaseHelper.getWritableDatabase();
            TaskDao taskDao =new TaskDao(db);

            taskEntity.setProviderId(TaskList.currentID);

            taskDao.delete(taskEntity);
            db.close();
        }
        catch (SQLiteException e){
            Toast toast = Toast.makeText(getApplicationContext(), "delete Title nie działa", Toast.LENGTH_LONG);
            toast.show();
        }

       refreshTheActivity();


    }

    private void refreshTheActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Toast toast = Toast.makeText(getApplicationContext(), "Delete when the Task is done! :)", Toast.LENGTH_LONG);
        toast.show();
    }





        class ExportJson extends AsyncTask<Context,Void, String>{

            @Override
            protected String doInBackground(Context... context) {
                try {
                    startExport();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                return null;
            }
            private void startExport() throws FileNotFoundException {
                SQLiteOpenHelper taskDatabaseHelper = new TaskDatabaseHelper(getApplicationContext());
                SQLiteDatabase db = taskDatabaseHelper.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT  * FROM "+ TaskTable.TABLE_NAME, null);

                final   GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(Task.class, new JSON());
                    gsonBuilder.setPrettyPrinting();
                final   Gson gson = gsonBuilder.create();
                StringBuilder stringBuilder = new StringBuilder();
                    if (cursor .moveToFirst()) {

                        while (cursor.isAfterLast() == false) {




                    JSONObject root = new JSONObject();
                    JSONArray taskArray = new JSONArray();

                    // int i = 0;
                    //while (!cursor.isAfterLast()) {


                        JSONObject JSONtask = new JSONObject();
                        //int id = cursor.getColumnIndexOrThrow("_id");
                            final  TaskDao taskdao =new TaskDao(db);
                           // final     Task currentTask = taskdao.get(id);

                            Task testTask = new Task();
                            testTask.setProviderId(cursor.getInt(0));
                            testTask.setTitle(cursor.getString(1));
                            testTask.setDescription(cursor.getString(2));testTask.setCreated(cursor.getLong(3));
                            testTask.setEndTime(cursor.getLong(4));
                            testTask.setImageUrl(cursor.getString(5));
                            Log.d("Saving....", testTask.getTitle());



                        JSON jsonSerializer = new JSON();


                        String json = gson.toJson(testTask);
                            //TODO zamist używać string buildera, który źle tworzy strukturę JSON, trzeba
                            //stringBuilder.append(json);

                            cursor.moveToNext();
                        //}

                }}

                saveImageToExternalStorage(stringBuilder.toString());
            }






            private void saveImageToExternalStorage(String json) {
                String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
                Log.d("JSON to export", json);
                File myDir = new File(root + "/savedjsons");
                myDir.mkdirs();


                String fname = "JsonTest.json";
                File file = new File(myDir, fname);
                if (file.exists())
                    file.delete();

                try {
                    FileOutputStream out = new FileOutputStream(file);
                    out.write(json.getBytes());
                    out.flush();
                    out.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }


                // Tell the media scanner about the new file so that it is
                // immediately available to the user.
                MediaScannerConnection.scanFile(getApplicationContext(), new String[] { file.toString() }, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("ExternalStorage", "Scanned " + path + ":");
                                Log.i("ExternalStorage", "-> uri=" + uri);
                            }
                        });

            }





            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast toast = Toast.makeText(getApplicationContext(), "asf", Toast.LENGTH_LONG);
                toast.show();
            }
        }
}
