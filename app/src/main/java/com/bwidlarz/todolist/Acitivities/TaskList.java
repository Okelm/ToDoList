package com.bwidlarz.todolist.Acitivities;


import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.bwidlarz.todolist.Database.TaskTable;
import com.bwidlarz.todolist.Dialogs.DeletingAlert_Fragment;
import com.bwidlarz.todolist.R;
import com.bwidlarz.todolist.Database.TaskDatabaseHelper;


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
        if (cursor!=null) {
             cursor.close();
        }
        if (db!=null) {
            db.close();
        }
    }

    private void populateTheListView() {
        View view = getView();
        listView = (ListView) view.findViewById(R.id.taskListView);
        downloadFromDataBasetest();
        onListViewClick();
    }
    private void downloadFromDataBasetest() {
        View view =getView();

        listView = (ListView) view.findViewById(R.id.taskListView);
        TaskDatabaseHelper handler = new TaskDatabaseHelper(getActivity());
        SQLiteDatabase db = handler.getWritableDatabase();

        Cursor todoCursor = db.rawQuery("SELECT  * FROM "+ TaskTable.TABLE_NAME, null);
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
