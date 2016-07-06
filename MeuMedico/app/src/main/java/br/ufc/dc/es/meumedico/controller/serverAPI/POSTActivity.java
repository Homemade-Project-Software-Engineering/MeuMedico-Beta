package br.ufc.dc.es.meumedico.controller.serverAPI;

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

public class POSTActivity {

    String base_url = ConfigAPI.BASE_URL;
    String post_alarm = ConfigAPI.POST_ALARM;
    String post = ConfigAPI.POST;

    public void POST(Map<String,String> dados) throws IOException, JSONException {

        String url = base_url+post_alarm;

        URL object=new URL(url);

        HttpURLConnection con = (HttpURLConnection) object.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestMethod(post);

        JSONObject data   = new JSONObject();

        data.put("name", dados.get("name"));
        data.put("checked", dados.get("checked"));
        data.put("user_id", dados.get("user_id"));
        data.put("horario", dados.get("horario"));
        data.put("description", dados.get("description"));

        OutputStream os = con.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        osw.write(data.toString());
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
