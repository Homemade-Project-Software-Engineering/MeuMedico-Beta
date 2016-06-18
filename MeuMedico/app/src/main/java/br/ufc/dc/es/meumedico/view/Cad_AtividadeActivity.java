package br.ufc.dc.es.meumedico.view;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import br.ufc.dc.es.meumedico.model.helper.AtividadeHelper;
import br.ufc.dc.es.meumedico.model.fragments.DatePickerFragment;
import br.ufc.dc.es.meumedico.R;
import br.ufc.dc.es.meumedico.model.fragments.TimePickerFragment;
import br.ufc.dc.es.meumedico.model.helper.ValidacaoHelper;
import br.ufc.dc.es.meumedico.controller.AtividadeDAO;
import br.ufc.dc.es.meumedico.model.domain.Atividade;

public class Cad_AtividadeActivity extends FragmentActivity
        implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {

    Button btsalvarAlterar;
    AtividadeHelper helper;
    int id_usuario;
    Atividade atividadeParaSerAlterada;
    int ano, mes, dia, hora, minuto;
    EditText editTextHora, editTextData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_atividade);
        callCriarAtividade();

        atividadeParaSerAlterada = (Atividade) getIntent().getSerializableExtra("atividadeSelecionada");

        if(atividadeParaSerAlterada!=null){
            btsalvarAlterar.setText("Alterar");
            helper.atividadeParaSerAlterada(atividadeParaSerAlterada);
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
                    SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    GregorianCalendar dataUsuario = new GregorianCalendar();
                    dataUsuario.add(Calendar.YEAR, ano);
                    dataUsuario.add(Calendar.MONTH, mes);
                    dataUsuario.add(Calendar.DAY_OF_MONTH, dia);
                    dataUsuario.add(Calendar.HOUR_OF_DAY, hora);
                    dataUsuario.add(Calendar.MINUTE, minuto);
                    //String data = ano+"/"+mes+"/"+dia+" "+hora+":"+minuto;
                    try {
                        Date dataAtual = sf.parse(sf.format(Calendar.getInstance().getTime()));
                        Date dataFormatada = sf.parse(dataUsuario.toString());
                        Log.i("data setada",dataUsuario.getTime().toString());
                        Log.i("data atual",dataAtual.toString());
                        Log.i("data usuario",dataFormatada.toString());
                        if(dataFormatada.compareTo(dataAtual)<0){
                            Log.i("Result", "Data anterior");
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(Cad_AtividadeActivity.this, "Todos os campos são obrigatórios, preencha e tente novamente", Toast.LENGTH_LONG).show();
                }else {
                    AtividadeDAO dao = new AtividadeDAO(Cad_AtividadeActivity.this);
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
        editTextData.setText(String.format("%02d", dayOfMonth)+"/"+String.format("%02d", monthOfYear)+"/"+String.format("%02d", year));
        ano = year;
        mes = monthOfYear;
        dia = dayOfMonth;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        editTextHora = (EditText) findViewById(R.id.AtHora);
        editTextHora.setText(String.format("%02d", hourOfDay)+":"+String.format("%02d", minute));
        hora = hourOfDay;
        minuto = minute;
    }
}
