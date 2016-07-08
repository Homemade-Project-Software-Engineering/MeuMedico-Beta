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

public class PUTUser {

    String base_url = ConfigAPI.BASE_URL;
    String put_user = ConfigAPI.PUT_USER;
    String put = ConfigAPI.PUT;

    public void PUT(Map<String,String> dados,int id_usuario) throws IOException, JSONException {

        String url = base_url+ put_user +id_usuario;

        URL object=new URL(url);

        HttpURLConnection con = (HttpURLConnection) object.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestMethod(put);


        JSONObject user = new JSONObject();

        JSONObject data = new JSONObject();

        data.put("name", dados.get("name"));
        data.put("email", dados.get("email"));

        user.put("user", data);

        OutputStream os = con.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
        osw.write(user.toString());
        Log.i("json", data.toString());
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
