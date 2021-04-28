package com.qoobico.remindme.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

class FileIO {

    void createAndSaveFile(String filePath, String mJsonResponse) {

        try {
            File file = new File(filePath);
            boolean fileExists = file.exists();
            if (!fileExists) {
                File parentFile = file.getParentFile();
                if (parentFile != null) {
                    boolean dirs = parentFile.mkdirs();
                }
                fileExists = file.createNewFile();
            }

            if (fileExists) {
                FileWriter fw = new FileWriter(filePath);
                fw.write(mJsonResponse);
                fw.flush();
                fw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String readJsonData(String filePath) {
        try {
            File f = new File(filePath);
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

    public void deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            boolean deleted = file.delete();
            if (deleted)
                Utils.debugLog("File is deleted: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
