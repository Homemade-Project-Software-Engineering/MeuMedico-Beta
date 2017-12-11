package br.ufc.dc.es.meumedico.controller.serverAPI;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class PUTActivity {

    String base_url = ConfigAPI.BASE_URL;
    String put_user = ConfigAPI.PUT_ALARM_USERS;
    String put_alarm = ConfigAPI.PUT_ALARM_ALARMS;
    String put = ConfigAPI.PUT;

    public void PUT(Map<String,String> dados, int id_usuario, int id) throws IOException, JSONException {

        String url = base_url+put_user+id_usuario+put_alarm+id;

        URL object=new URL(url);

        HttpURLConnection con = (HttpURLConnection) object.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestMethod(put);

        JSONObject data   = new JSONObject();
        JSONObject alarm   = new JSONObject();

        data.put("name", dados.get("name"));
        data.put("checked", dados.get("checked"));
        data.put("user_id", dados.get("user_id"));
        data.put("horario", dados.get("horario"));
        data.put("description", dados.get("description"));

        alarm.put("alarm", data);

        Log.i("json", alarm.toString());

        OutputStream os = con.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        osw.write(alarm.toString());
        osw.flush();
        osw.close();

        //display what returns the POST request

        StringBuilder sb = new StringBuilder();
        int HttpResult = con.getResponseCode();
        if (HttpResult == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            System.out.println("" + sb.toString());
        } else {
            System.out.println(con.getResponseMessage());
        }
    }
}
