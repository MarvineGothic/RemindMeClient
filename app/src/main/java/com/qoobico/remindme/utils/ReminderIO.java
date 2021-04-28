package com.qoobico.remindme.utils;

import android.content.Context;

import com.qoobico.remindme.dto.RemindDTO;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static com.qoobico.remindme.activity.MainActivity.tabsFragmentAdapter;

public class ReminderIO extends FileIO {

    private String fileName;

    public ReminderIO(Context context, String fileName) {
        this.fileName = context.getFilesDir().getPath() + context.getPackageName() + "/" + fileName;
    }

    public void getData() {
        tabsFragmentAdapter.setData(this.getRemindDTOs());
    }

    public List<RemindDTO> getRemindDTOs(){
        String fileContents = this.readJsonData(this.fileName);
        Utils.debugLog("Read file: " + fileContents);
        return Utils.getReminders(fileContents);
    }

    public void putReminder(String tab_name, String title, Date date){
        List<RemindDTO> remindDTOS = this.getRemindDTOs();
        long maxId = 0;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            RemindDTO maxIdDto = remindDTOS.stream().max(Comparator.comparing(RemindDTO::getId)).orElseThrow(NoSuchElementException::new);
            maxId = maxIdDto.getId();
        } else {
            for (RemindDTO remindDTO : remindDTOS) {
                maxId = Math.max(maxId, remindDTO.getId());
            }
        }

        remindDTOS.add(new RemindDTO(maxId + 1, tab_name, title, date));

        this.createAndSaveFile(this.fileName, remindDTOS.toString());

        tabsFragmentAdapter.setData(remindDTOS);
    }

    public void deleteReminder(long id){
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
