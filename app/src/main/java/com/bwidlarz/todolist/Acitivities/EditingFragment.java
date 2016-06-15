package com.bwidlarz.todolist.Acitivities;


import android.app.FragmentManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bwidlarz.todolist.Database.Task;
import com.bwidlarz.todolist.Database.TaskDao;
import com.bwidlarz.todolist.Database.TaskDatabaseHelper;
import com.bwidlarz.todolist.Dialogs.DataPickerFragment;
import com.bwidlarz.todolist.Dialogs.TimePickerFragment;
import com.bwidlarz.todolist.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditingFragment extends Fragment {

    private int item_id;
    private TextView titleView ;
    TextView timeView ;
    private TextView descView;
    TextView dateView;
    Button dataPickerButton;
    Button timePickerButton;
    String timeAndDate;
    TextView timeAndDateView;
    EditText urlView;

    int year = 0;
    int month = 0;
    int day = 0;
    int hours = 0;
    int minutes = 0;

    public EditingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

                return  inflater.inflate(R.layout.fragment_editing, container, false);
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();
        View view =getView();
        populateTheViews();
        settingPickerForTimeAndDate(view);
        setFloatingActionButton(view);
    }

    private void populateTheViews() {

        View view = getView();
        titleView = (TextView) view.findViewById(R.id.editTitleView) ;
        descView = (EditText) view.findViewById(R.id.editDescView) ;
        timeAndDateView = (TextView) view.findViewById(R.id.currentDueTimeData);
        urlView = (EditText) view.findViewById(R.id.imageEditText);

        try{
            SQLiteOpenHelper taskDatabaseHelper = new TaskDatabaseHelper(getActivity());
            SQLiteDatabase db = taskDatabaseHelper.getWritableDatabase();
            TaskDao taskDao =new TaskDao(db);
            Task task= taskDao.get(item_id);
            db.close();

            titleView.setText(task.getTitle());
            descView.setText(task.getDescription());
            long timeText = task.getEndTime();
            if (timeText>0){
                    timeAndDateView.setText(unbindTheDate(timeText));
            }
            urlView.setText(task.getImageUrl());
        }
        catch (SQLiteException e){
            Toast toast = Toast.makeText(getActivity(), "Baza nie działa", Toast.LENGTH_LONG);
            toast.show();
        }

    }

    private String unbindTheDate(Long timeText) {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(timeText);
            Date d = c.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            return sdf.format(d).toString();
    }



    public void setEditingFragment(int item_id) {
        this.item_id = item_id;
    }


    private void setFloatingActionButton(View view) {

        FloatingActionButton floatingActionButton =  (FloatingActionButton) view.findViewById(R.id.fab3);
        titleView = (EditText) view.findViewById(R.id.editTitleView);
        descView = (EditText) view.findViewById(R.id.editDescView);
        dateView = (TextView) view.findViewById(R.id.textViewForDateEditing);
        timeView = (TextView) view.findViewById(R.id.textViewForTimeEditing);
        urlView = (EditText) view.findViewById(R.id.imageEditText);

        try{

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String newTitle = titleView.getText().toString();
                    String newDesc = descView.getText().toString();
                    if( newTitle.length() == 0 )
                        titleView.setError( "First name is required!" );
                    else {
                        Task taskEntity = new Task();
                        taskEntity.setTitle(titleView.getText().toString());
                        taskEntity.setDescription(descView.getText().toString());
                        taskEntity.setProviderId(item_id);

                        if(dateView.getText().toString().length()>1 && timeView.getText().toString().length() >1){
                            taskEntity.setEndTime(buildTheDate());
                        }

                        if (!(urlView.length()==0)){
                            taskEntity.setImageUrl(urlView.getText().toString());
                        }
                        SQLiteOpenHelper taskDatabaseHelper = new TaskDatabaseHelper(getActivity());
                        SQLiteDatabase db = taskDatabaseHelper.getWritableDatabase();
                        TaskDao taskDao =new TaskDao(db);
                        taskDao.update(taskEntity);
                        db.close();
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

    private void settingPickerForTimeAndDate(View view) {

        dataPickerButton =(Button) view.findViewById(R.id.dataPickerButtonEditing);
        dataPickerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                DataPickerFragment dataPicker = new DataPickerFragment();
                // Show Alert DialogFragment
                dataPicker.setTargetFragment(EditingFragment.this,1);
                FragmentManager fm =getActivity().getFragmentManager();
                dataPicker.show(fm, "Time Dialog Fragment");
            }
        });

        timePickerButton = (Button) view.findViewById(R.id.timePickerButtonEditing) ;
        timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timePicker = new TimePickerFragment();
                // Show Alert DialogFragment
                timePicker.setTargetFragment(EditingFragment.this,0);
                FragmentManager fm =getActivity().getFragmentManager();
                timePicker.show(fm, "Time Dialog Fragment");
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        View v = getView();

        if (requestCode == TimePickerFragment.REQUEST_CODE) {
            TextView timeShow = (TextView) v.findViewById(R.id.textViewForTimeEditing);

            hours = data.getIntExtra("hourOfDay",hours);
            minutes = data.getIntExtra("minute", minutes);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(hours);
            stringBuilder.append(":");
            if (minutes<10){
                stringBuilder.append("0"); }
            stringBuilder.append(minutes);

            timeShow.setText(stringBuilder.toString());
        }

        if (requestCode == DataPickerFragment.REQUEST_CODE) {
            TextView dateShow = (TextView) v.findViewById(R.id.textViewForDateEditing);

            year = data.getIntExtra("year",year);
            month = data.getIntExtra("month", month);
            day = data.getIntExtra("day", day);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(day);
            stringBuilder.append("/");
            stringBuilder.append(month);
            stringBuilder.append("/");
            stringBuilder.append(year);

            dateShow.setText(stringBuilder.toString());
        }
    }

    private Long buildTheDate() {
        Calendar calendar = new GregorianCalendar(year, month, day, hours ,minutes);
        Long timeZnowu = (calendar.getTimeInMillis());
        return timeZnowu;
    }

}
