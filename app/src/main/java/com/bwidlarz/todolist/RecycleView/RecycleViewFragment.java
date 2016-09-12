package com.bwidlarz.todolist.RecycleView;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwidlarz.todolist.Database.TaskDatabaseHelper;
import com.bwidlarz.todolist.Database.TaskTable;
import com.bwidlarz.todolist.R;

/**
 * Created by Dell on 2016-07-10.
 */
public class RecycleViewFragment extends Fragment {
//private SQLiteDatabase db;
//private Cursor cursor;
//    CursorAdapter listAdapter;
//    ListView listView;
//    static long  currentID;
//    static TaskListAdapter todoAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerview = (RecyclerView) inflater.inflate(
                R.layout.recycleview_material_layout, container, false);

        TaskDatabaseHelper handler = new TaskDatabaseHelper(getActivity());
        SQLiteDatabase db = handler.getWritableDatabase();
        Cursor todoCursor = db.rawQuery("SELECT  * FROM "+ TaskTable.TABLE_NAME, null);
        MyListCursorAdapter adapter = new MyListCursorAdapter(getActivity(), todoCursor);
        recyclerview.setAdapter(adapter);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
//        recyclerview.setLayoutManager(gridLayoutManager);
        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        //TaskListAdapter todoAdapter = new TaskListAdapter(getActivity(), todoCursor, 0);
       return recyclerview;

    }


//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (cursor!=null) {
//            cursor.close();
//        }
//        if (db!=null) {
//            db.close();
//        }
//    }


}
