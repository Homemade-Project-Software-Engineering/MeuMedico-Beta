package br.ufc.dc.es.meumedico.model;

import android.util.Log;

/**
 * Created by jonas on 06/06/16.
 */
public class MyAlternateLogVersion {

    public static final void Log(String message){
        Log("MyTests",message);
    }

    public static final void Log(String tag, String messgae){
        Log.d(tag,messgae);
    }
}
