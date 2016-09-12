package com.bwidlarz.todolist.Acitivities;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.bwidlarz.todolist.Database.Results;
import com.bwidlarz.todolist.Database.Task;
import com.bwidlarz.todolist.Database.TaskDao;
import com.bwidlarz.todolist.Database.TaskDatabaseHelper;
import com.bwidlarz.todolist.R;
import com.bwidlarz.todolist.RecycleView.RecycleViewFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements DeletingAlert_Fragment.NoticeDialogListener{

    int currentPosition = 0;
    private String[] titles;
    private ListView drawerList;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    static String fileName = "myBlog.json";
    public static String sortingListViewOption = "Default";


    private class DrawerItemClickListener implements ListView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    private void selectItem(int position) {
        currentPosition = position;
        switch (position){
            case 0:
                refreshTheList("SortByName");
                break;
            case 1:
                refreshTheList("SortByDate");
                break;
            default:
                refreshTheList("Default");
        }
        setActionBarTitle(position);
        drawerLayout.closeDrawer(drawerList);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("position", currentPosition);
    }
    //TODO backstackchangelistiner add

    private void setActionBarTitle(int position) {
        String title;
        if (position == 0){
            title = getResources().getString(R.string.app_name);
        }else {
            title = titles[position];
        }
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFABClickListener();
        setNavigationDrawer();
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("position");
            setActionBarTitle(currentPosition);
        }else {
            refreshTheList("Default");
        }
/*
        Intent intent = new Intent(this, NotificationHandler.class);
        intent.putExtra(NotificationHandler.EXTRA_MESSAGE, "hahahahahahh działa!!!!1");
        startService(intent);
        */

    }

    private void setNavigationDrawer() {
        titles = getResources().getStringArray(R.array.titles);
        drawerList = (ListView) findViewById(R.id.drawer);
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_activated_1,titles));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,
                R.string.open_drawer,R.string.close_drawer){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.JSONImport_item).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
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
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        switch (item.getItemId()) {
            case R.id.JSONExport_item:
                new ExportJson().execute();
                return true;
            case R.id.JSONImport_item:
                new ImportJson().execute();
                return true;
//            case R.id.SortByName:
//                refreshTheList("SortByName");
//                return true;
//            case R.id.SortByDate:
//                refreshTheList("SortByDate");
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void refreshTheList(String sortBy) {
        sortingListViewOption =  sortBy;
//        TaskList taskList = new TaskList();
        RecycleViewFragment recycleViewFragment = new RecycleViewFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container2, recycleViewFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_NONE);
        ft.commit();
    }

    private void setFABClickListener() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        try{
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startEditingActivity();
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


    private void startEditingActivity() {
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
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setPrettyPrinting();
                Gson gson = gsonBuilder.create();
                SQLiteOpenHelper taskDatabaseHelper = new TaskDatabaseHelper(getApplicationContext());
                SQLiteDatabase db = taskDatabaseHelper.getReadableDatabase();
                TaskDao taskDao = new TaskDao(db);

                List<Task> listTemp = taskDao.getAll();
                Results results= new Results(listTemp);

                String json = gson.toJson(results);
                saveJsonToExternalStorage(json);
            }

            private void saveJsonToExternalStorage(String json) {
                String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
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
                Toast toast = Toast.makeText(getApplicationContext(), "zapisana", Toast.LENGTH_LONG);
                toast.show();
            }
        }

    private class ImportJson extends AsyncTask<Context,Void, String>{
        @Override
        protected String doInBackground(Context... params) {
            return startImport();
        }

        private String startImport() {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setPrettyPrinting();
            Gson gson = gsonBuilder.create();

            String json = loadJSONFromAsset();
            //List<Task> list = gson.fromJson(json);
            return json;
        }


        public String loadJSONFromAsset() {
            String json = null;
            try {
                String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
                //InputStream is = getApplicationContext().getAssets().open(root + "/savedjsons/JsonTest.json");
                InputStream is = new FileInputStream(root + "/savedjsons/JsonTest.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //Toast toast = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG);
            //toast.show();

            Gson gson = new GsonBuilder().create();
            Results results = gson.fromJson(s, Results.class);
            Toast toast = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG);
            toast.show();
        }

    }
}