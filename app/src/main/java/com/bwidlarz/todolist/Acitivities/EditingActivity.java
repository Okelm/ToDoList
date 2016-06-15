package com.bwidlarz.todolist.Acitivities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.Toast;

import com.bwidlarz.todolist.R;

public class EditingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static final String EXTRA_INFO ="itemNo" ;
    private int itemNo;

    public EditingActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing);
        choseFragmentToStart();
    }

    private void choseFragmentToStart() {
        itemNo= (Integer) getIntent().getExtras().get(EXTRA_INFO);
        if (itemNo==0)
            { createNewTask();}
        else
            { editTask();}
    }

    private void editTask() {

        EditingFragment editFragment = new EditingFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        editFragment.setEditingFragment(itemNo);
        ft.replace(R.id.fragment_container, editFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_NONE);
        ft.commit();

    }

    private void createNewTask() {

        CreateFragment createfragment = new CreateFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, createfragment);
        ft.setTransition(FragmentTransaction.TRANSIT_NONE);
        ft.commit();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        Toast toast = Toast.makeText(this, "Baza nie działa", Toast.LENGTH_LONG);
        toast.show();
    }


}
