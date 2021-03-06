package com.qoobico.remindme.utils;

import com.qoobico.remindme.dto.RemindDTO;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static com.qoobico.remindme.utils.Constants.DEBUG;

public class Utils {
    public static void debugLog(Object logMessage) {
        if (DEBUG) {
            System.out.println(logMessage);
        }
    }

    public static List<RemindDTO> getReminders(Object jsonData) {
        JSONArray jsonArray;

        List<RemindDTO> remindDTOS = new ArrayList<>();
        try {
            if (jsonData instanceof JSONArray) {
                jsonArray = (JSONArray) jsonData;
            } else if (jsonData instanceof String) {
                jsonArray = new JSONArray((String) jsonData);
            } else {
                jsonArray = new JSONArray(jsonData);
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                remindDTOS.add(new RemindDTO(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return remindDTOS;
    }
}
