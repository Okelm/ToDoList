package com.bwidlarz.todolist;

import android.app.DialogFragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements DeletingAlert_Fragment.NoticeDialogListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        setFABClickListener(fab);
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
}
