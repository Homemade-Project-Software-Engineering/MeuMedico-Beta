package br.ufc.dc.es.meumedico.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import br.ufc.dc.es.meumedico.R;
import br.ufc.dc.es.meumedico.controller.helper.isConnected;
import br.ufc.dc.es.meumedico.controller.serverAPI.POSTCaregiver;
import br.ufc.dc.es.meumedico.controller.serverAPI.POSTUser;
import br.ufc.dc.es.meumedico.model.LoginDAO;
import br.ufc.dc.es.meumedico.model.MeuMedicoDAO;

public class CuidadorActivity extends AppCompatActivity {

    private static final String PREF_NAME = "LoginActivityPreferences";

    int id_usuario, id_recebido;
    MeuMedicoDAO dao_cuidador = new MeuMedicoDAO(this);
    ListView lista_cuidador;
    private static CuidadorActivity toastCuidadorActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuidador);

        getSupportActionBar().setTitle(R.string.cuidador_action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toastCuidadorActivity = CuidadorActivity.this;

        SharedPreferences sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        this.id_usuario = sp.getInt("id_usuario", 0);

        lista_cuidador = (ListView) findViewById(R.id.lista_cuidador);
        //registerForContextMenu(lista_cuidador);

        String teste[] = new String[]{"teste", "teste1", "teste1", "teste1"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, teste);

        lista_cuidador.setAdapter(adapter);
    }

    public void inserirCuidador(View view){

        final AlertDialog diag = new AlertDialog.Builder(this)
                .setTitle("Cadastrar Cuidador")
                .setView(R.layout.dialog_insert_cuidador)
                .create();

        diag.show();

        final Button confirmar = (Button) diag.findViewById(R.id.btn_Confirmar);
        final Button cancelar = (Button) diag.findViewById(R.id.btn_Cancelar);
        final EditText id_cuidador = (EditText) diag.findViewById(R.id.etValor);

        confirmar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(id_cuidador.getText().toString().equals("")) {
                    Toast.makeText(CuidadorActivity.this, "Campo ID obrigatório para cadastro de Cuidador",
                            Toast.LENGTH_SHORT).show();
                    diag.dismiss();
                }else{
                    try{
                        final int id_recebido = Integer.parseInt(id_cuidador.getText().toString());

                        if(id_usuario==id_recebido){
                            Toast.makeText(CuidadorActivity.this, "Você não pode se cadastrar como Cuidador" +
                                            ", por favor, tente novamente",
                                    Toast.LENGTH_LONG).show();
                            diag.dismiss();
                        }else{

                            final LoginDAO dao_login = new LoginDAO(CuidadorActivity.this);
                            final MeuMedicoDAO dao_cuidador = new MeuMedicoDAO(CuidadorActivity.this);

                            if(dao_login.verificarUsuarioTabelaLogin(id_recebido)==false){
                                Toast.makeText(CuidadorActivity.this, "ID não encontrado" +
                                                ", por favor, tente novamente",
                                        Toast.LENGTH_LONG).show();
                                diag.dismiss();
                            }else if(dao_cuidador.verificaUsuarioCadastradoComoCuidador(id_usuario,id_recebido)){
                                Toast.makeText(CuidadorActivity.this, "Você já cadastrou esse usuário de ID "
                                                + id_recebido + " como seu cuidador",
                                        Toast.LENGTH_LONG).show();
                                diag.dismiss();
                            }else{

                                final ProgressDialog dialog = new ProgressDialog(CuidadorActivity.this);
                                dialog.setMessage("Cadastrando Cuidador...");
                                dialog.show();

                                Thread mThread = new Thread() {
                                    @Override
                                    public void run() {
                                        dao_cuidador.insertCuidador(id_usuario, id_recebido);

                                        Map<String, String> dados = new HashMap<>();
                                        dados.put("patient_id", String.valueOf(id_usuario));
                                        dados.put("caregiver_id", String.valueOf(id_recebido));

                                        POSTCaregiver post = new POSTCaregiver();
                                        try {
                                            if(new isConnected().isConnected(toastCuidadorActivity)) {
                                                post.POST(dados);
                                            }else{
                                                toastCuidadorActivity.runOnUiThread(new Runnable() {
                                                    public void run() {
                                                        Toast.makeText(toastCuidadorActivity.getBaseContext(), "Sem conexão com a internet, " +
                                                                "salvando apenas localmente", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                            post.POST(dados);
                                        } catch (IOException|JSONException e) {
                                            e.printStackTrace();
                                        }

                                        toastCuidadorActivity.runOnUiThread(new Runnable() {
                                            public void run() {
                                                Toast.makeText(toastCuidadorActivity.getBaseContext(), "Cuidador cadastrado com sucesso",
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        });
                                        diag.dismiss();
                                        dialog.dismiss();
                                    }
                                };
                                mThread.start();

                            }
                            dao_cuidador.close();
                            dao_login.close();
                        }

                    }catch (NumberFormatException e){
                        Toast.makeText(CuidadorActivity.this, "ID do Cuidador para cadastro é um inteiro" +
                                        ", por favor, tente novamente",
                                Toast.LENGTH_LONG).show();
                        diag.dismiss();
                    }
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //finaliza o dialog
                diag.dismiss();
            }
        });
    }

    public void deletarCuidador(View view){

        final AlertDialog diag = new AlertDialog.Builder(this)
                .setTitle("Excluir Cuidador")
                .setView(R.layout.dialog_insert_cuidador)
                .create();

        diag.show();

        final Button confirmar = (Button) diag.findViewById(R.id.btn_Confirmar);
        final Button cancelar = (Button) diag.findViewById(R.id.btn_Cancelar);
        final EditText id_cuidador = (EditText) diag.findViewById(R.id.etValor);

        confirmar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(id_cuidador.getText().toString().equals("")) {
                    Toast.makeText(CuidadorActivity.this, "Campo ID obrigatório para cadastro de Cuidador",
                            Toast.LENGTH_SHORT).show();
                    diag.dismiss();
                }else{
                    try{
                        id_recebido = Integer.parseInt(id_cuidador.getText().toString());

                        if(id_usuario==id_recebido){
                            Toast.makeText(CuidadorActivity.this, "Você não pode se deletar como Cuidador" +
                                            ", por favor, tente novamente",
                                    Toast.LENGTH_LONG).show();
                            diag.dismiss();
                        }else{

                            LoginDAO dao_login = new LoginDAO(CuidadorActivity.this);
                            dao_cuidador = new MeuMedicoDAO(CuidadorActivity.this);

                            if(dao_login.verificarUsuarioTabelaLogin(id_recebido)==false){
                                Toast.makeText(CuidadorActivity.this, "ID não encontrado" +
                                                ", por favor, tente novamente",
                                        Toast.LENGTH_LONG).show();
                                diag.dismiss();
                            }else if(dao_cuidador.verificaUsuarioCadastradoComoCuidador(id_usuario,id_recebido)==false){
                                Toast.makeText(CuidadorActivity.this, "Você não cadastrou esse usuário de ID "
                                                + id_recebido + " como seu cuidador, nada a fazer",
                                        Toast.LENGTH_LONG).show();
                                diag.dismiss();
                            }else{

                                diag.dismiss();
                                new AlertDialog.Builder(CuidadorActivity.this)
                                        .setMessage("Você realmente deseja excluir esse cuidador?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                dao_cuidador.deleteCuidador(id_usuario,id_recebido);
                                                Toast.makeText(CuidadorActivity.this, "Cuidador excluido com sucesso",
                                                        Toast.LENGTH_LONG).show();
                                            }})
                                        .setNegativeButton("Cancelar", null).show();
                            }

                            dao_cuidador.close();
                            dao_login.close();
                        }
                    }catch (NumberFormatException e){
                        Toast.makeText(CuidadorActivity.this, "ID do Cuidador para cadastro é um inteiro" +
                                        ", por favor, tente novamente",
                                Toast.LENGTH_LONG).show();
                        diag.dismiss();
                    }
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //finaliza o dialog
                diag.dismiss();
            }
        });
    }
}
