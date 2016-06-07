package com.bwidlarz.todolist;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.support.v4.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;
import android.support.v4.app.FragmentActivity;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Dell on 2016-06-03.
 */
public class DataPickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static final int REQUEST_CODE = 1 ;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        Calendar calendar = new GregorianCalendar(year, month, day);
        Intent intent = new Intent();
        intent.putExtra("year",year);
        intent.putExtra("month",month);
        intent.putExtra("day",day);

        getTargetFragment().onActivityResult(
                getTargetRequestCode(), 1, intent);
    }
}
