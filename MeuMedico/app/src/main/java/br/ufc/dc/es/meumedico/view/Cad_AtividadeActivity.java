package br.ufc.dc.es.meumedico.view;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.ufc.dc.es.meumedico.controller.helper.AtividadeHelper;
import br.ufc.dc.es.meumedico.controller.fragments.DatePickerFragment;
import br.ufc.dc.es.meumedico.R;
import br.ufc.dc.es.meumedico.controller.fragments.TimePickerFragment;
import br.ufc.dc.es.meumedico.controller.helper.ValidacaoHelper;
import br.ufc.dc.es.meumedico.model.MeuMedicoDAO;
import br.ufc.dc.es.meumedico.controller.domain.Atividade;
import br.ufc.dc.es.meumedico.controller.others.NotificationPublisher;
import br.ufc.dc.es.meumedico.controller.others.UpdateActivityByNotification;

public class Cad_AtividadeActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {

    Button btsalvarAlterar;
    AtividadeHelper helper;
    int id_usuario;
    Atividade atividadeParaSerAlterada;
    int ano, mes, dia, hora, minuto, notification_id = 1;
    EditText editTextHora, editTextData;
    Bundle data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_atividade);
        callCriarAtividade();

        atividadeParaSerAlterada = (Atividade) getIntent().getSerializableExtra("atividadeSelecionada");

        if(atividadeParaSerAlterada!=null){
            getSupportActionBar().show();
            getSupportActionBar().setTitle("Editar Atividade");
            btsalvarAlterar.setText(R.string.botao_alterar_atividade);
            helper.atividadeParaSerAlterada(atividadeParaSerAlterada);
        }else{
            getSupportActionBar().show();
            getSupportActionBar().setTitle("Cadastrar Atividade");
        }
    }

    public void callCriarAtividade(){

        helper = new AtividadeHelper(Cad_AtividadeActivity.this);

        btsalvarAlterar = (Button) findViewById(R.id.AtSalvar);
        btsalvarAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValidacaoHelper vh = new ValidacaoHelper(Cad_AtividadeActivity.this);

                if(vh.verificaCamposVaziosAtividade()){
                    Toast.makeText(Cad_AtividadeActivity.this, "Todos os campos são obrigatórios, preencha e tente novamente", Toast.LENGTH_LONG).show();
                }else {
                    MeuMedicoDAO dao = new MeuMedicoDAO(Cad_AtividadeActivity.this);
                    Atividade atividade = helper.pegaCamposAtividade();
                    if (atividadeParaSerAlterada != null) {
                        atividade.setId(atividadeParaSerAlterada.getId());
                        atividade.setId_usuario(atividadeParaSerAlterada.getId_usuario());
                        dao.update(atividade);
                        Toast.makeText(Cad_AtividadeActivity.this, "Atividade atualizada com sucesso", Toast.LENGTH_SHORT).show();
                    } else {
                        id_usuario = getIntent().getIntExtra("id_usuario", 0);
                        atividade.setId_usuario(id_usuario);
                        dao.insert(atividade);

                        data = new Bundle();

                        data.putString("descricao", atividade.getDescricao());

                        Log.i("id", String.valueOf(dao.getLastIDInserted()));
                        data.putInt("id", dao.getLastIDInserted());

                        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
                        String dataUser = String.format(Locale.US,"%02d", dia)+"/"+String.format(Locale.US,"%02d", mes+1)+"/"
                                +String.format(Locale.US,"%02d", ano) + " " + String.format(Locale.US,"%02d", hora)+":"+String.format(Locale.US,"%02d", minuto);
                        try {
                            Date dataFormatada = sf.parse(dataUser);
                            Log.i("data setada",dataUser);
                            Log.i("data usuario",dataFormatada.toString());
                            Log.i("data em millis", String.valueOf(dataFormatada.getTime()));
                            data.putLong("dataHora", dataFormatada.getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        scheduleNotification(getNotification());
                        Toast.makeText(Cad_AtividadeActivity.this, "Atividade inserida com sucesso", Toast.LENGTH_SHORT).show();
                    }
                    dao.close();
                    finish();
                }
            }
        });
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");


    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }



    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        editTextData = (EditText) findViewById(R.id.AtData);
        editTextData.setText(String.format(Locale.US,"%02d", dayOfMonth)+"/"+String.format(Locale.US,"%02d", monthOfYear+1)+"/"
                +String.format(Locale.US,"%02d", year));
        ano = year;
        mes = monthOfYear;
        dia = dayOfMonth;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        editTextHora = (EditText) findViewById(R.id.AtHora);
        editTextHora.setText(String.format(Locale.US,"%02d", hourOfDay)+":"+String.format(Locale.US,"%02d", minute));
        hora = hourOfDay;
        minuto = minute;
    }

    private void scheduleNotification(Notification notification) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, data.getLong("dataHora"), pendingIntent);
    }

    private Notification getNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setCategory("CATEGORY_EVENT")
                .setPriority(Notification.PRIORITY_MAX)
                .setTicker(data.getString("descricao"))
                .setSmallIcon(R.drawable.ic_notification_activity)
                .setContentTitle("Realizar Atividade")
                .setContentText(data.getString("descricao"))
                .setAutoCancel(true);

        Intent intentDone = new Intent("br.ufc.dc.es.meumedico.intentDone");
        intentDone.putExtra(UpdateActivityByNotification.ID, data.getInt("id"));
        intentDone.putExtra(UpdateActivityByNotification.BOOLEAN_CONCLUIDA, 1);
        intentDone.putExtra(NotificationPublisher.NOTIFICATION_ID, notification_id);
        PendingIntent pendingIntentDone = PendingIntent.getBroadcast(this, 0, intentDone, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentDontDone = new Intent("br.ufc.dc.es.meumedico.intentDontDone");
        intentDontDone.putExtra(UpdateActivityByNotification.ID, data.getInt("id"));
        intentDontDone.putExtra(UpdateActivityByNotification.BOOLEAN_CONCLUIDA, 0);
        intentDontDone.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        PendingIntent pendingIntentDontDone = PendingIntent.getBroadcast(this, 0, intentDontDone, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});

        Uri uri = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(uri);
        builder.setWhen(data.getLong("dataHora"));
        builder.addAction(R.drawable.ic_notification_done, "Feito", pendingIntentDone);
        builder.addAction(R.drawable.ic_notification_dont_done, "Não Feito", pendingIntentDontDone);

        notification_id++;

        return builder.build();
    }
}
