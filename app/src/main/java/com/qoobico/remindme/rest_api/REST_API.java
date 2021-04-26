//package com.qoobico.remindme.rest_api;
//
//import com.qoobico.remindme.dto.RemindDTO;
//import com.qoobico.remindme.utils.Constants;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.qoobico.remindme.utils.Constants.URL.POST_REMIND_ITEM;
//import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
//
//public class REST_API {
//    public static void POST(String jsonString){
//        try {
//                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(POST_REMIND_ITEM).openConnection();
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
//                httpURLConnection.setRequestProperty("Accept", APPLICATION_JSON_VALUE);
//                httpURLConnection.setDoInput(true);
//
//                try (OutputStreamWriter osw = new OutputStreamWriter(httpURLConnection.getOutputStream())) {
//                    osw.write(jsonString);
//                } catch (Exception e) {
//                    System.out.println(e.toString());
//                }
//
//                try (BufferedReader br = new BufferedReader(
//                        new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8))) {
//                    StringBuilder response = new StringBuilder();
//                    String responseLine = null;
//                    while ((responseLine = br.readLine()) != null) {
//                        response.append(responseLine.trim());
//                    }
//                    System.out.println(response.toString());
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//    }
//    public static List<RemindDTO> GET(){
//        System.out.println(" get item ");
//        List<RemindDTO> remindDTOS = new ArrayList<>();
//        HttpJsonParser parser = new HttpJsonParser();
//        JSONArray response = parser.makeHttpRequest(Constants.URL.GET_REMIND_ITEM, "GET", null);
//
//        try {
//            for (int i = 0; i < response.length(); i++) {
//                remindDTOS.add(new RemindDTO(response.getJSONObject(i)));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return remindDTOS;
//    }
//}
