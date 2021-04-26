package com.qoobico.remindme.rest_api;

import android.os.AsyncTask;

import com.qoobico.remindme.dto.RemindDTO;
import com.qoobico.remindme.utils.Constants;
import com.qoobico.remindme.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.qoobico.remindme.activity.MainActivity.tabsFragmentAdapter;

public class RestAsync extends AsyncTask<Void, Void, List<RemindDTO>> {

    private static RestTemplate restTemplate;
    private static HttpHeaders headers;
    private String jsonString = null;
    private String deleteURL = null;
    private String updateURL = null;

    public RestAsync() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    public void getData() {
        this.execute();
    }

    public void sendData(String jsonString) {
        this.jsonString = jsonString;
        this.execute();
    }

    public void deleteData(String deleteURL) {
        this.deleteURL = deleteURL;
        this.execute();
    }

    public void updateData(String updateURL, String jsonString) {
        this.updateURL = updateURL;
        this.jsonString = jsonString;
        this.execute();
    }

    @Override
    protected List<RemindDTO> doInBackground(Void... voids) {
        if (jsonString != null)
            try {
                HttpEntity<RemindDTO> request = new HttpEntity<>(new RemindDTO(new JSONObject(jsonString)), headers);

                ResponseEntity<RemindDTO> personResultAsJsonStr = restTemplate.postForEntity(Constants.URL.POST_REMIND_ITEM, request, RemindDTO.class);
                if (personResultAsJsonStr != null) Utils.debugLog("POST: " + jsonString);
            } catch (Exception e) {
                Utils.debugLog(e.toString() + " POST request failed!");
            }

        if (updateURL != null && jsonString != null) {
            try {
                RemindDTO updatedInstance = new RemindDTO(new JSONObject(jsonString));
                HttpEntity<RemindDTO> requestUpdate = new HttpEntity<>(updatedInstance, headers);
                restTemplate.exchange(updateURL, HttpMethod.PUT, requestUpdate, Void.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (deleteURL != null)
            restTemplate.delete(deleteURL);

        List<RemindDTO> remindDTOS = new ArrayList<>();
        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(Constants.URL.GET_REMIND_ITEM, Object[].class);
        Object[] objects = responseEntity.getBody();

        try {
            JSONArray jsonArray = new JSONArray(objects);
            for (int i = 0; i < jsonArray.length(); i++) {
                remindDTOS.add(new RemindDTO(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return remindDTOS;
    }

    @Override
    protected void onPostExecute(List<RemindDTO> remindDTOS) {
        tabsFragmentAdapter.setData(remindDTOS);
    }
}