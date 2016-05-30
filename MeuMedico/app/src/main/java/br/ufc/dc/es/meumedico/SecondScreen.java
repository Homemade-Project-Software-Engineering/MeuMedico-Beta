package br.ufc.dc.es.meumedico;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.List;

import br.ufc.dc.es.dao.AtividadeDAO;
import br.ufc.dc.es.model.Atividade;
import br.ufc.dc.es.model.Login;

public class SecondScreen extends Activity{

    Intent cadAtividade;
    String user;
    Button home;
    Button datePicker;
    TextView nameUser;
    Button atividade;
    String nome, email;
    int id_usuario;
    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        fillSpinner();
        btHome();
        btDate();

        callCadAtividade();

        Login informacoes = (Login) getIntent().getSerializableExtra("informacoes");
        nome = informacoes.getName();
        email = informacoes.getEmail();
        id_usuario = informacoes.getId();
        //setUser("@"+getDataMainScreen());
        setUser(id_usuario + " - " + nome);
        setNameView(getUser());

        lista = (ListView) findViewById(R.id.listViewAtividades);
        registerForContextMenu(lista);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
     public boolean onCreateOptionsMenu(Menu menu){
         getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
     }
     public void btDate(){
         datePicker = (Button) findViewById(R.id.btDatePiker);
         datePicker.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 DialogFragment newFragment = new DatePickerFragment();
                 newFragment.show(getFragmentManager(),"datePicker");
             }
         });
     }

     public void btHome(){
         home = (Button) findViewById(R.id.home);
         home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondScreen.this, MainActivity.class));
                finish(); // dispose do java
            }
        });
    }
    public void setNameView(String name){
        nameUser = (TextView) findViewById(R.id.nameUser);
        nameUser.setText(name);
    }

    public void setUser(String name){
        this.user = name;
    }
    public String getUser(){
        return user;
    }

    public void fillSpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.spinnerOptions);
        String[] options = getResources().getStringArray(R.array.options);
        ArrayAdapter<String> adapterOptions =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapterOptions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterOptions);
        spinner.setPrompt("Options");
    }

    public void callCadAtividade(){

        atividade = (Button) findViewById(R.id.btCad_Atividade);
        atividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadAtividade = new Intent(SecondScreen.this, Cad_AtividadeActivity.class);
                cadAtividade.putExtra("id_usuario",id_usuario);
                startActivity(cadAtividade);
            }
        });
    }

    public void carregaLista(){

        AtividadeDAO dao = new AtividadeDAO(this);

        List<Atividade> atividades = dao.getListaAtividades(id_usuario);

        ArrayAdapter<Atividade> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, atividades);

        lista.setAdapter(adapter);
    }
}
