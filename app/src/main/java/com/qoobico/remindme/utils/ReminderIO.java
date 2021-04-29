package com.qoobico.remindme.utils;

import android.content.Context;

import com.qoobico.remindme.dto.RemindDTO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static com.qoobico.remindme.activity.MainActivity.tabsFragmentAdapter;

public class ReminderIO extends FileIO {

    private Long lastId;
    private String fileName;

    public ReminderIO(Context context, String remindersDir) {
        this.fileName = context.getFilesDir().getPath() + context.getPackageName() + "/" + remindersDir + ".json";
    }

    public void getAllReminders() {
        try {
            tabsFragmentAdapter.setData(this.getRemindDTOs());
        } catch (JSONException e) {
            e.printStackTrace();
            Utils.debugLog("Error when getting all reminders: " + e.getMessage());
        }
    }

    public void putReminder(String tab_name, String title, Date date) {
        try {
            List<RemindDTO> remindDTOS = this.getRemindDTOs();
            JSONObject reminderJson = new JSONObject();

            Long newId = lastId + 1;
            remindDTOS.add(new RemindDTO(newId, tab_name, title, date));

            reminderJson.put("lastId", newId);
            reminderJson.put("reminders", remindDTOS.toString());

            Utils.debugLog("Composed JSON: " + reminderJson.toString());

            this.createAndSaveFile(this.fileName, reminderJson.toString());

            tabsFragmentAdapter.setData(remindDTOS);
        } catch (JSONException e) {
            e.printStackTrace();
            Utils.debugLog("Error when put reminder: " + e.getMessage());
        }
    }

    public void deleteReminder(long id){
        try {
            List<RemindDTO> remindDTOS = this.getRemindDTOs();

            Iterator<RemindDTO> i = remindDTOS.iterator();
            while (i.hasNext()) {
                RemindDTO remindDTO = i.next();

                if (remindDTO.getId() == id) {
                    Utils.debugLog("Deleted Item " + remindDTO);
                    i.remove();
                }
            }

            Utils.debugLog("Saving Data to File " + remindDTOS);
            this.createAndSaveFile(this.fileName, remindDTOS.toString());

            tabsFragmentAdapter.setData(remindDTOS);
        } catch (JSONException e) {
            e.printStackTrace();
            Utils.debugLog("Error when deleting reminder: " + e.getMessage());
        }
    }

    private List<RemindDTO> getRemindDTOs() throws JSONException {
        String fileContents = this.readJsonData(this.fileName);
        Utils.debugLog("Read file: " + fileContents);
        JSONObject fileJson = new JSONObject(fileContents);
        lastId = fileJson.getLong("lastId");
        return Utils.getReminders(fileJson.getString("reminders"));
    }

    public List<RemindDTO> getMockDTOs() {
        List<RemindDTO> remindDTOS = new ArrayList<>();
        remindDTOS.add(new RemindDTO((long) 1, "IDEAS", "I have an idea", new Date()));
        remindDTOS.add(new RemindDTO((long) 2, "TODO", "I have something todo", new Date()));
        remindDTOS.add(new RemindDTO((long) 3, "BIRTHDAYS", "I have a BD", new Date()));
        remindDTOS.add(new RemindDTO((long) 4, "IDEAS", "I have another idea", new Date()));

        Utils.debugLog("Working Directory = " + System.getProperty("user.dir"));

        return remindDTOS;
    }
}
