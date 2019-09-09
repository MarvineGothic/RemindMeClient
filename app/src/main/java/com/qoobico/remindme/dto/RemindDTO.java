package com.qoobico.remindme.dto;

import android.text.format.DateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RemindDTO {
    private long id;
    private String title;
    private Date remindDate;

    public RemindDTO() {
    }

    public RemindDTO(String title) {
        this.title = title;
    }

    public RemindDTO(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getInt("id");
            this.title = jsonObject.getString("title");
            this.remindDate = new Date(jsonObject.getLong("remindDate"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getRemindDate() {
        return remindDate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRemindDate(Date remindDate) {
        this.remindDate = remindDate;
    }
}
