package br.ufc.dc.es.meumedico.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.ufc.dc.es.meumedico.R;
import br.ufc.dc.es.meumedico.controller.domain.Atividade;
import br.ufc.dc.es.meumedico.model.AtividadeDAO;

public class CalendarActivity extends AppCompatActivity implements CalendarPickerController{

    List<Atividade> listAtividade = new ArrayList<>();
    int id_usuario, color;
    BaseCalendarEvent event;
    List<CalendarEvent> eventList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Calendário de Atividades");

        AgendaCalendarView mAgendaCalendarView = (AgendaCalendarView) findViewById(R.id.agenda_calendar_view);

        AtividadeDAO dao = new AtividadeDAO(this);

        id_usuario = getIntent().getIntExtra("id_usuario", 0);
        listAtividade = dao.getListaAtividades(id_usuario);
        dao.close();
        // minimum and maximum date of our calendar
        // 2 month behind, one year ahead, example: March 2015 <-> May 2015 <-> May 2016
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        try {
            mockList(eventList);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        minDate.add(Calendar.MONTH, -2);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 1);

        mAgendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mockList(eventList);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void mockList(List<CalendarEvent> eventList) throws ParseException {

        for(Atividade atividade : listAtividade) {

            if(atividade.getConcluida()==-1){
                color = R.color.colorGrayActivityPending;
            }else if(atividade.getConcluida()==1){
                color = R.color.colorGreenActivityDone;
            }else{
                color = R.color.colorRedActivityDontDone;
            }

            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy",Locale.US);
            Date data = formato.parse(atividade.getData());

            Log.i("Data", data.toString());
            Log.i("Data da Atividade", atividade.getData());

            Calendar startTime = Calendar.getInstance();
            Calendar endTime = Calendar.getInstance();
            startTime.setTime(data);
            endTime.setTime(data);

            event = new BaseCalendarEvent(atividade.getDescricao(), "", atividade.getNome(),
                    ContextCompat.getColor(this, color), startTime, endTime, true);
            eventList.add(event);
        }
    }

    @Override
    public void onDaySelected(DayItem dayItem) {
        Log.i("Status", "Dia selecionado");
    }

    @Override
    public void onEventSelected(CalendarEvent event) {
        Log.i("Status", "Evento selecionado");
    }

    @Override
    public void onScrollToDate(Calendar calendar) {
        Log.i("Status", "Scroll");
    }
}
