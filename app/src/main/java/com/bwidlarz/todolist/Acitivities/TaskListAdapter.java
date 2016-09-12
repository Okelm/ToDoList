package com.bwidlarz.todolist.Acitivities;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwidlarz.todolist.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dell on 2016-06-05.
 */
public class TaskListAdapter extends CursorAdapter {

    public TaskListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.adapter_item_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView titleView = (TextView) view.findViewById(R.id.titleViewAdapter);
        TextView timeEndView = (TextView) view.findViewById(R.id.timeEndViewAdapter);

        String title = cursor.getString(cursor.getColumnIndexOrThrow("TITLE"));
        Long timeEnd = cursor.getLong(cursor.getColumnIndexOrThrow("TIME_END"));

        titleView.setText(title);
        timeEndView.setText(translateTheDate(timeEnd));
    }

    private String translateTheDate(Long timeEnd) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeEnd);
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(d).toString();
    }
}
