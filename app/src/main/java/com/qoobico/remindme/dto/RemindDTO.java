package com.qoobico.remindme.dto;

import androidx.annotation.NonNull;

import com.qoobico.remindme.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class RemindDTO {
    private JSONObject jsonObject;
    private long id;
    private String tab_name;
    private String title;
    private Date remindDate;


    public RemindDTO(JSONObject jsonObject) {
        Utils.debugLog("new DTO");
        initFields(jsonObject);
    }

    public RemindDTO(Long id, String tab_name, String title, Date remindDate) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("tab_name", tab_name);
            jsonObject.put("title", title);
            jsonObject.put("remindDate", remindDate.getTime());
            initFields(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initFields(JSONObject jsonObject){
        this.jsonObject = jsonObject;
        try {
            this.id = jsonObject.getInt("id");
            this.tab_name = jsonObject.getString("tab_name");
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

    public String getTab_name() {
        return tab_name;
    }

    public void setTab_name(String tab_name) {
        this.tab_name = tab_name;
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

    @NonNull
    public String toString(){
        return jsonObject.toString();
    }
}
