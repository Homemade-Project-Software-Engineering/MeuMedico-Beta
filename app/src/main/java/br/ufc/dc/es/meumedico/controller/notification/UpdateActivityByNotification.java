package br.ufc.dc.es.meumedico.controller.notification;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import br.ufc.dc.es.meumedico.model.MeuMedicoDAO;

public class UpdateActivityByNotification extends BroadcastReceiver{

    public static String ID = "id";
    public static String BOOLEAN_CONCLUIDA = "trueOrFalse";
    public static String NOTIFICATION_ID = "notification-id";

    @Override
    public void onReceive(Context context, Intent intent) {

        int id = intent.getIntExtra(ID, 0);
        int trueOrFalse = intent.getIntExtra(BOOLEAN_CONCLUIDA, 0);
        Log.i("id", String.valueOf(id));
        Log.i("trueOrFalse", String.valueOf(trueOrFalse));
        MeuMedicoDAO dao = new MeuMedicoDAO(context);
        dao.changeColumnConcluidabyNotification(id,trueOrFalse);
        dao.close();

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(intent.getIntExtra(NOTIFICATION_ID, 0));
    }
}
