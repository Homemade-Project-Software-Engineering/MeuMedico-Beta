package br.ufc.dc.es.meumedico;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import java.util.List;

import br.ufc.dc.es.dao.AtividadeDAO;
import br.ufc.dc.es.model.Atividade;
import br.ufc.dc.es.model.Login;

public class SecondScreen extends Activity{

    Intent cadAtividade;
    String user;
    Button datePicker;
    TextView nameUser;
    Button atividade;
    String nome, email;
    int id_usuario;
    ListView lista;
    Atividade atividadeSelecionadaItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        fillSpinner();
        btDate();

        callCadAtividade();

        Login informacoes = (Login) getIntent().getSerializableExtra("informacoes");
        if(informacoes!=null) {
            nome = informacoes.getName();
            email = informacoes.getEmail();
            id_usuario = informacoes.getId();
        }

        Bundle infosFacebook = getIntent().getBundleExtra("infosFacebook");
        if(infosFacebook!=null) {
            nome = infosFacebook.get("first_name").toString();
        }
        //setUser("@"+getDataMainScreen());
        setUser(nome);
        setNameView(getUser());

        lista = (ListView) findViewById(R.id.listViewAtividades);
        registerForContextMenu(lista);

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {

                atividadeSelecionadaItem = (Atividade) adapter.getItemAtPosition(position);
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuItem editar = menu.add("Editar");
        editar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent atividadeSelecionada = new Intent(SecondScreen.this, Cad_AtividadeActivity.class);
                atividadeSelecionada.putExtra("atividadeSelecionada", atividadeSelecionadaItem);
                startActivity(atividadeSelecionada);

                return false;
            }
        });

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                AtividadeDAO dao = new AtividadeDAO(SecondScreen.this);
                dao.delete(atividadeSelecionadaItem);
                dao.close();
                carregaLista();
                Toast.makeText(SecondScreen.this, "Atividade deletada com sucesso", Toast.LENGTH_SHORT).show();

                return false;
            }
        });
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.itemOptionsPerfil:
                break;
            case R.id.itemOptionsConfig:
                break;
            case R.id.itemOptionsConta:
                break;
            case R.id.itemOptionsLogout:
                LoginManager.getInstance().logOut();
                startActivity(new Intent(SecondScreen.this, MainActivity.class));
                finish(); // dispose do java
                break;
        }
        return super.onOptionsItemSelected(item);
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

        dao.close();
    }


}
