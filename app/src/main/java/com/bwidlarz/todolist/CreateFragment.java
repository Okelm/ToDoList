package com.bwidlarz.todolist;


import android.app.FragmentManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import  android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class CreateFragment extends Fragment{

    private static final int REQUEST_CODE = 0 ;
    public static int timePicked = 10;
    EditText titleView;
    EditText descView;
    Button dataPickerButton;
    Button timePickerButton;
    TextView dateView;
    TextView timeView;

    int year = 0;
    int month = 0;
    int day = 0;
    int hours = 0;
    int minutes = 0;

    public CreateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        settingPickerForTimeAndDate(view);
        setFloatingActionButton(view);
    }

    private void setFloatingActionButton(View view) {

        FloatingActionButton floatingActionButton =  (FloatingActionButton) view.findViewById(R.id.fab2);
        titleView = (EditText) view.findViewById(R.id.editTitleViewCreate);
        descView = (EditText) view.findViewById(R.id.editDescViewCreate);
        dateView = (TextView) view.findViewById(R.id.textViewForDate);
        timeView = (TextView) view.findViewById(R.id.textViewForTime);

        try{
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (titleView.length()==0){
                        titleView.setError( "Title is required :)" );
                    }
                    else{
                        Task taskEntity = new Task();
                        taskEntity.setTitle(titleView.getText().toString());
                        taskEntity.setDescription(descView.getText().toString());
                        taskEntity.setEndTime(buildTheDate());


                        SQLiteOpenHelper taskDatabaseHelper = new TaskDatabaseHelper(getActivity());
                        SQLiteDatabase db = taskDatabaseHelper.getWritableDatabase();
                        TaskDao taskDao =new TaskDao(db);
                        taskDao.save(taskEntity);

                        getActivity().finish();
                        }
                }
            });
        }
        catch (NullPointerException e){
            Toast toast = Toast.makeText(getActivity(), "FAB nie dziala", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private Long buildTheDate() {
        Calendar calendar = new GregorianCalendar(year, month, day, hours ,minutes);

        return calendar.getTimeInMillis();
    }

    private void settingPickerForTimeAndDate(View view) {

        dataPickerButton =(Button) view.findViewById(R.id.dataPickerButton);
        dataPickerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                DataPickerFragment dataPicker = new DataPickerFragment();

                dataPicker.setTargetFragment(CreateFragment.this,1);
                FragmentManager fm =getActivity().getFragmentManager();
                dataPicker.show(fm, "Time Dialog Fragment");
            }
        });

        timePickerButton = (Button) view.findViewById(R.id.timePickerButton) ;
        timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timePicker = new TimePickerFragment();

                timePicker.setTargetFragment(CreateFragment.this,0);
                FragmentManager fm = getActivity().getFragmentManager();
                timePicker.show(fm, "Time Dialog Fragment");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        View v = getView();

        if (requestCode == TimePickerFragment.REQUEST_CODE) {
            TextView timeshow = (TextView) v.findViewById(R.id.textViewForTime);

            hours = data.getIntExtra("hourOfDay",hours);
            minutes = data.getIntExtra("minute", minutes);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(hours);
            stringBuilder.append(":");
            stringBuilder.append(minutes);

            timeshow.setText(stringBuilder.toString());
        }

        if (requestCode == DataPickerFragment.REQUEST_CODE) {

            TextView timeshow = (TextView) v.findViewById(R.id.textViewForDate);

            year = data.getIntExtra("year",year);
            month = data.getIntExtra("month", month);
            day = data.getIntExtra("day", day);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(day);
            stringBuilder.append("/");
            stringBuilder.append(month);
            stringBuilder.append("/");
            stringBuilder.append(year);

            timeshow.setText(stringBuilder.toString());
        }
    }
}
