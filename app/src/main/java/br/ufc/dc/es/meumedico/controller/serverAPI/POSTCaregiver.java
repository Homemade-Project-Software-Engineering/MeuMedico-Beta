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

public class POSTCaregiver {

    String base_url = ConfigAPI.BASE_URL;
    String post_relationship = ConfigAPI.POST_RELATIONSHIP;
    String post = ConfigAPI.POST;

    public void POST(Map<String,String> dados) throws IOException, JSONException {

        String url = base_url+post_relationship;

        URL object=new URL(url);

        HttpURLConnection con = (HttpURLConnection) object.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestMethod(post);

        JSONObject data   = new JSONObject();
        JSONObject rel = new JSONObject();

        data.put("patient_id", dados.get("patient_id"));
        data.put("caregiver_id", dados.get("caregiver_id"));
        rel.put("relationship", data);

        OutputStream os = con.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        osw.write(rel.toString());
        osw.flush();
        osw.close();

        Log.i("json", rel.toString());
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
