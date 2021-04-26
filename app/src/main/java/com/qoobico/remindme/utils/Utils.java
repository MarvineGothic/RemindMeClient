package com.qoobico.remindme.utils;

import static com.qoobico.remindme.utils.Constants.DEBUG;

public class Utils {
    public static void debugLog(Object logMessage){
        if (DEBUG){
            System.out.println(logMessage);
        }
    }
}
