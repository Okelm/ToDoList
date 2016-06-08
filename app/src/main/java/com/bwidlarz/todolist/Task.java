package com.bwidlarz.todolist;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Dell on 2016-06-05.
 */
public class Task   {

public Task(){
    this.created = setCreated();
}

    private long providerId;
    private String title;
    private String description;
    private long  created;
    private long endTime;
    private String imageUrl;

    public long getProviderId() {
        return providerId;
    }

    public void setProviderId(long providerId) {
        this.providerId = providerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public long getCreated() {
        return created;
    }

    public static String getTimeCreated(long created) {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(created);
            Date d = c.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(d).toString();
    }

    public Long setCreated() {
        return   System.currentTimeMillis();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }



}
