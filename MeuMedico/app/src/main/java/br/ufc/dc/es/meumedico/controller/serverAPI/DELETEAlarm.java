package br.ufc.dc.es.meumedico.controller.serverAPI;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DELETEAlarm {

    String base_url = ConfigAPI.BASE_URL;
    String delete_alarm_users = ConfigAPI.DELETE_ALARM_USERS;
    String delete_alarm_alarms = ConfigAPI.DELETE_ALARM_ALARMS;
    String delete = ConfigAPI.DELETE;

    public void Delete(int id_usuario, int id) throws IOException, JSONException {

        String url = base_url+delete_alarm_users+String.valueOf(id_usuario)+
                delete_alarm_alarms+String.valueOf(id);

        URL object=new URL(url);

        HttpURLConnection httpCon = (HttpURLConnection) object.openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestProperty(
                "Content-Type", "application/x-www-form-urlencoded" );
        httpCon.setRequestMethod(delete);
        httpCon.connect();

        //display what returns the POST request

        StringBuilder sb = new StringBuilder();
        int HttpResult = httpCon.getResponseCode();
        if (HttpResult == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(httpCon.getInputStream(), "utf-8"));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            System.out.println("" + sb.toString());
        } else {
            System.out.println(httpCon.getResponseMessage());
        }
    }
}
