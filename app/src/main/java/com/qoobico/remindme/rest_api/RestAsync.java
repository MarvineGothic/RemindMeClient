package com.qoobico.remindme.rest_api;

import android.os.AsyncTask;

import com.qoobico.remindme.Constants;
import com.qoobico.remindme.adapter.TabsFragmentAdapter;
import com.qoobico.remindme.dto.RemindDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.qoobico.remindme.MainActivity.tabsFragmentAdapter;

public class RestAsync extends AsyncTask<Void, Void, List<RemindDTO>> {

    private static RestTemplate restTemplate;
    private String jsonString = null;
    private String deleteURL = null;

    public RestAsync() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    public void getData() {
        this.execute();
    }

    public void sendData(String jsonString) {
        this.jsonString = jsonString;
        this.execute();
    }

    public void deleteData(String deleteItemID) {
        this.deleteURL = deleteItemID;
        this.execute();
    }

    public void updateData() {

        this.execute();
    }

    @Override
    protected List<RemindDTO> doInBackground(Void... voids) {
        if (jsonString != null)
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<RemindDTO> request = new HttpEntity<>(new RemindDTO(new JSONObject(jsonString)), headers);

                ResponseEntity<RemindDTO> personResultAsJsonStr = restTemplate.postForEntity(Constants.URL.POST_REMIND_ITEM, request, RemindDTO.class);
                if (personResultAsJsonStr != null) System.out.println("POST: " + jsonString);
            } catch (Exception e) {
                System.out.println(e.toString() + " POST request failed!");
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