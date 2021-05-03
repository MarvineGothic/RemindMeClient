package com.qoobico.remindme.manager;

import com.qoobico.remindme.rest_api.RestAsync;
import com.qoobico.remindme.utils.Utils;

import java.util.Date;
import java.util.Locale;

import static com.qoobico.remindme.activity.MainActivity.reminderIO;
import static com.qoobico.remindme.utils.Constants.MOCK;
import static com.qoobico.remindme.utils.Constants.URL.DELETE_REMIND_ITEM;

public class ReminderManager {

    public static void getAllReminders() {
        Utils.debugLog("ReminderManager: Get all reminders");
        if (MOCK) {
            reminderIO.getAllReminders();
        } else {
            new RestAsync().getData();
        }
    }

    public static void saveReminder(String reminderId, String tabName, String title, Date date) {
        Utils.debugLog("ReminderManager: Save item");

        if (MOCK) {
            if (reminderId != null) {
                reminderIO.patchReminder(reminderId, tabName, title, date);
            } else {
                reminderIO.putReminder(tabName, title, date);
            }
        } else {
            String item = String.format(Locale.ENGLISH,
                    "{\"id\":\"0\",\"tab_name\":%s,\"title\":%s,\"remindDate\":%s}",
                    tabName, title, date.getTime());
            new RestAsync().sendData(item);
        }
    }

    public static void deleteReminder(String reminderId) {
        Utils.debugLog("ReminderManager: Delete item id " + reminderId);

        new RestAsync().deleteData(DELETE_REMIND_ITEM + reminderId);
        reminderIO.deleteReminder(Long.parseLong(reminderId));
    }
}
