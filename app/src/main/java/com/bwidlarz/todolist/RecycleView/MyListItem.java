package com.bwidlarz.todolist.RecycleView;

import android.database.Cursor;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class MyListItem{
    private String title;
    private String desc;
    private String created;

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {

        DateTimeFormatter dateStringFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        DateTime time = dateStringFormat.parseDateTime(created);
        int month = time.getMonthOfYear();
        int day =  time.getDayOfMonth();

        this.created = month + "/" +day;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public String getTitle(){
        return title;
    }

    public static MyListItem fromCursor(Cursor cursor) {
        MyListItem myListItem = new MyListItem();

        myListItem.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("TITLE")));
        myListItem.setDesc(cursor.getString(cursor.getColumnIndexOrThrow("DESCRIPTION")));
        myListItem.setCreated(cursor.getString(cursor.getColumnIndexOrThrow("CREATED")));

        return myListItem;
    }
}