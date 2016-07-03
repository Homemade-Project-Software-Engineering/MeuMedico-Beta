package br.ufc.dc.es.meumedico.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import br.ufc.dc.es.meumedico.controller.fragments.AtividadeFragment;
import br.ufc.dc.es.meumedico.R;
import br.ufc.dc.es.meumedico.model.MeuMedicoDAO;
import br.ufc.dc.es.meumedico.model.LoginDAO;
import br.ufc.dc.es.meumedico.controller.domain.Atividade;
import br.ufc.dc.es.meumedico.controller.domain.Login;

public class MainActivity extends AppCompatActivity {

    Intent cadAtividade;
    String user;
    Button datePicker;
    TextView nameUser;
    Button atividade;
    String nome, email;
    int id_usuario;
    Atividade atividadeSelecionadaItem;
    Login informacoes;
    Bundle infosFacebook;
    AtividadeFragment frag;
    private static final String PREF_NAME = "LoginActivityPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.second_activity);

        btDate();

        callCadAtividade();

        informacoes = (Login) getIntent().getSerializableExtra("informacoes");
        if(informacoes!=null) {
            nome = informacoes.getName();
            email = informacoes.getEmail();
            id_usuario = informacoes.getId();
            userIsLogged(id_usuario, nome, email);
        }

        infosFacebook = getIntent().getBundleExtra("infosFacebook");
        if(infosFacebook!=null) {
            LoginDAO dao = new LoginDAO(MainActivity.this);
            nome = infosFacebook.get("first_name").toString();
            email = infosFacebook.get("email").toString();
            id_usuario = dao.getIdUserByFacebookEmail(email);
            dao.close();
            userIsLogged(id_usuario, nome, email);
        }

        SharedPreferences sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        id_usuario = sp.getInt("id_usuario", 0);
        nome = sp.getString("nome", "");
        email = sp.getString("email", "");

        if(email.equals("")){
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }

        setUser(nome);
        setNameView(getUser());

        frag = (AtividadeFragment) getSupportFragmentManager().findFragmentByTag("mainFrag");
        if(frag == null) {
            frag = new AtividadeFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container, frag, "mainFrag");
            ft.commit();
        }
    }

    public boolean onContextItemSelected(MenuItem item) {

        try {
            atividadeSelecionadaItem = frag.mList.get(frag.positionOnLongClick);
        }catch(IndexOutOfBoundsException e){
            Log.i("Status", "Array sem nada");
        }

        switch (item.getItemId()) {
            case R.id.itemContextMenuEditar:

                Intent atividadeSelecionada = new Intent(MainActivity.this, Cad_AtividadeActivity.class);
                atividadeSelecionada.putExtra("atividadeSelecionada", atividadeSelecionadaItem);
                startActivity(atividadeSelecionada);
                break;
            case R.id.itemContextMenuDeletar:

                MeuMedicoDAO dao = new MeuMedicoDAO(MainActivity.this);
                dao.delete(atividadeSelecionadaItem);
                dao.close();
                carregaLista();
                Toast.makeText(MainActivity.this, "Atividade deletada com sucesso", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
        LoginDAO dao = new LoginDAO(this);
        if(dao.getIdUserByFacebookEmail(email)==0){
            LoginManager.getInstance().logOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        dao.close();
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
                 Intent irParaCalendarView = new Intent(MainActivity.this, CalendarActivity.class);
                 irParaCalendarView.putExtra("id_usuario",id_usuario);
                 startActivity(irParaCalendarView);
             }
         });
     }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.itemOptionsCuidador:
                startActivity(new Intent(this, CuidadorActivity.class));
                break;
            case R.id.itemOptionsConta:
                Intent irParaATelaContaUsuario = new Intent(MainActivity.this, ContaUsuarioActivity.class);
                if(informacoes!=null) {
                    irParaATelaContaUsuario.putExtra("informacoes", informacoes);
                }else if(infosFacebook!=null){
                    irParaATelaContaUsuario.putExtra("infosFacebook", infosFacebook);
                }
                startActivity(irParaATelaContaUsuario);
                break;
            case R.id.itemOptionsLogout:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setMessage(R.string.MensagemLogout)
                        .setPositiveButton(R.string.SimDeletarConta, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                                progressDialog.setMessage("Saindo...");
                                progressDialog.show();
                                Thread mThread = new Thread(){
                                    @Override
                                    public void run() {
                                        LoginManager.getInstance().logOut();
                                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                        progressDialog.dismiss();
                                        SharedPreferences sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.clear();
                                        editor.apply();
                                        finish(); // dispose do java
                                    }
                                };
                                mThread.start();
                            }
                        })
                        .setNegativeButton(R.string.CancelarDeletarConta, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                builder.show();
                break;
            case R.id.itemOptionsCadastrar:
                startActivity(new Intent(this, Cad_AtividadeActivity.class));
                break;
            case R.id.itemOptionsAtualizar:
                //chamar método pra atualizar recyclerview da API
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
        return id_usuario + " - " + user;
    }

    public void callCadAtividade(){

        atividade = (Button) findViewById(R.id.btCad_Atividade);
        atividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadAtividade = new Intent(MainActivity.this, Cad_AtividadeActivity.class);
                cadAtividade.putExtra("id_usuario",id_usuario);
                startActivity(cadAtividade);
            }
        });
    }

    public void carregaLista(){

        frag.mList.clear();
        frag.mList.addAll(getSetAtividadeList());
        frag.adapter.notifyDataSetChanged();
    }

    public List<Atividade> getSetAtividadeList(){

        MeuMedicoDAO dao = new MeuMedicoDAO(this);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String dataAtual = simpleDateFormat.format(Calendar.getInstance().getTime());

        List<Atividade> atividades = dao.getListaAtividades(id_usuario,dataAtual);

        dao.close();

        return atividades;
    }

    public void userIsLogged(int id_usuario, String nome, String email){

        SharedPreferences sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putInt("id_usuario", id_usuario);
        editor.putString("nome", nome);
        editor.putString("email", email);
        editor.apply();
    }
}
