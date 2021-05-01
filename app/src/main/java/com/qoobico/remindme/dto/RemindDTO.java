package com.qoobico.remindme.dto;

import androidx.annotation.NonNull;

import com.qoobico.remindme.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class RemindDTO {
    private long id;
    private String tabName;
    private String title;
    private Date remindDate;


    public RemindDTO() {

    }

    public RemindDTO(JSONObject jsonObject) {
        Utils.debugLog("new DTO");
        initFields(jsonObject);
    }

    public RemindDTO(Long id, String tabName, String title, Date remindDate) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("tab_name", tabName);
            jsonObject.put("title", title);
            jsonObject.put("remindDate", remindDate.getTime());
            initFields(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initFields(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getInt("id");
            this.tabName = jsonObject.getString("tab_name");
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

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
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
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", this.getId());
            jsonObject.put("tab_name", this.getTabName());
            jsonObject.put("title", this.getTitle());
            jsonObject.put("remindDate", this.getRemindDate().getTime());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
