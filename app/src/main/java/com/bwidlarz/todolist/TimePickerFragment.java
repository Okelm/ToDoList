package com.bwidlarz.todolist;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Dell on 2016-06-03.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public static final int REQUEST_CODE = 0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        Calendar calendar = new GregorianCalendar(0, 0, 0,hourOfDay,minute, 0);
        Intent intent = new Intent();
        intent.putExtra("hourOfDay",hourOfDay);
        intent.putExtra("minute",minute);

        getTargetFragment().onActivityResult(
                getTargetRequestCode(), 0, intent);
    }
}