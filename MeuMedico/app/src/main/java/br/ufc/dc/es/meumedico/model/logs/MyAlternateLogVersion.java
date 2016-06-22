package br.ufc.dc.es.meumedico.model.logs;

import android.util.Log;

public class MyAlternateLogVersion {

    public static final void Log(String message){
        Log("MyTests",message);
    }

    public static final void Log(String tag, String messgae){
        Log.d(tag,messgae);
    }
}
