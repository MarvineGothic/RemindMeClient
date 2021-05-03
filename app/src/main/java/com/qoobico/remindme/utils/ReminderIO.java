package com.qoobico.remindme.utils;

import android.content.Context;

import com.qoobico.remindme.dto.RemindDTO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

    public void putReminder(String tabName, String title, Date date) {
        try {
            List<RemindDTO> remindDTOS = this.getRemindDTOs();

            Long newId = lastId + 1;
            remindDTOS.add(new RemindDTO(newId, tabName, title, date));

            String reminderJson = composeReminderJSON(remindDTOS);

            this.createAndSaveFile(this.fileName, reminderJson);

            tabsFragmentAdapter.setData(remindDTOS);
        } catch (JSONException e) {
            e.printStackTrace();
            Utils.debugLog("Error when put reminder: " + e.getMessage());
        }
    }

    public void patchReminder(String id, String tabName, String title, Date date) {
        try {
            long reminderId = Long.parseLong(id);
            List<RemindDTO> remindDTOS = this.getRemindDTOs();

            for (RemindDTO remindDTO : remindDTOS) {
                if (remindDTO.getId() == reminderId) {
                    remindDTO.setTabName(tabName);
                    remindDTO.setTitle(title);
                    remindDTO.setRemindDate(date);
                }
            }

            String reminderJson = composeReminderJSON(remindDTOS);

            this.createAndSaveFile(this.fileName, reminderJson);

            tabsFragmentAdapter.setData(remindDTOS);
        } catch (JSONException e) {
            e.printStackTrace();
            Utils.debugLog("Error when put reminder: " + e.getMessage());
        }
    }

    public void deleteReminder(long id) {
        try {
            List<RemindDTO> remindDTOS = this.getRemindDTOs();

            this.deleteRemindDTO(id, remindDTOS);

            String reminderJson = composeReminderJSON(remindDTOS);

            Utils.debugLog("Saving Data to File " + reminderJson);
            this.createAndSaveFile(this.fileName, reminderJson);

            tabsFragmentAdapter.setData(remindDTOS);
        } catch (JSONException e) {
            e.printStackTrace();
            Utils.debugLog("Error when deleting reminder: " + e.getMessage());
        }
    }

    private void deleteRemindDTO(Long reminderId, List<RemindDTO> remindDTOs) {
        Iterator<RemindDTO> i = remindDTOs.iterator();

        while (i.hasNext()) {
            RemindDTO remindDTO = i.next();

            if (remindDTO.getId() == reminderId) {
                Utils.debugLog("Deleted Item " + remindDTO);

                i.remove();
            }
        }

    }

    private String composeReminderJSON(List<RemindDTO> remindDTOS) throws JSONException {
        JSONObject reminderJson = new JSONObject();

        reminderJson.put("lastId", lastId);
        reminderJson.put("reminders", remindDTOS.toString());
        Utils.debugLog("Composed JSON: " + reminderJson);
        return reminderJson.toString();
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
