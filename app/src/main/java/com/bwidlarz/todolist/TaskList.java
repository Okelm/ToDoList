package com.bwidlarz.todolist;


import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskList extends Fragment {

    private SQLiteDatabase db;
    private Cursor cursor;
    CursorAdapter listAdapter;
    ListView listView;
    static long  currentID;
    static TaskListAdapter todoAdapter;


    public TaskList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
              return  inflater.inflate(R.layout.fragment_task_list, container, false);

        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
      super.onResume();
      populateTheListView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    private void populateTheListView() {
        View view = getView();
        listView = (ListView) view.findViewById(R.id.taskListView);
        downloadFromDataBasetest();
        onListViewClick();
    }
    private void downloadFromDataBasetest() {
        View view =getView();
        TaskDatabaseHelper handler = new TaskDatabaseHelper(getActivity());
        SQLiteDatabase db = handler.getWritableDatabase();

        Cursor todoCursor = db.rawQuery("SELECT  * FROM "+ TaskTable.TABLE_NAME, null);
        listView = (ListView) view.findViewById(R.id.taskListView);
        TaskListAdapter todoAdapter = new TaskListAdapter(getContext(), todoCursor, 0);
        listView.setAdapter(todoAdapter);
    }


    private void onListViewClick() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), EditingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra(EditingActivity.EXTRA_INFO, (int)id);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                currentID =  id;
                showDeleteWarning();
                return true;
            }
        });
    }

    private void showDeleteWarning() {
        DeletingAlert_Fragment deletingAlert = new DeletingAlert_Fragment();
        // Show Alert DialogFragment
        FragmentManager fm =getActivity().getFragmentManager();
        deletingAlert.show(fm, "Delete Dialog Fragment");

    }
}
