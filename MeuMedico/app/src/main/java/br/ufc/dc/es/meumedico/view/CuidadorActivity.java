package br.ufc.dc.es.meumedico.view;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import br.ufc.dc.es.meumedico.R;
import br.ufc.dc.es.meumedico.model.LoginDAO;
import br.ufc.dc.es.meumedico.model.MeuMedicoDAO;

public class CuidadorActivity extends AppCompatActivity {

    private static final String PREF_NAME = "LoginActivityPreferences";

    int id_usuario, id_recebido;
    MeuMedicoDAO dao_cuidador = new MeuMedicoDAO(this);
    ListView lista_cuidador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuidador);

        getSupportActionBar().setTitle(R.string.cuidador_action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                        int id_recebido = Integer.parseInt(id_cuidador.getText().toString());

                        if(id_usuario==id_recebido){
                            Toast.makeText(CuidadorActivity.this, "Você não pode se cadastrar como Cuidador" +
                                            ", por favor, tente novamente",
                                    Toast.LENGTH_LONG).show();
                            diag.dismiss();
                        }else{

                            LoginDAO dao_login = new LoginDAO(CuidadorActivity.this);
                            MeuMedicoDAO dao_cuidador = new MeuMedicoDAO(CuidadorActivity.this);

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

                                dao_cuidador.insertCuidador(id_usuario, id_recebido);

                                Toast.makeText(CuidadorActivity.this, "Cuidador cadastrado com sucesso",
                                        Toast.LENGTH_LONG).show();
                                diag.dismiss();
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
