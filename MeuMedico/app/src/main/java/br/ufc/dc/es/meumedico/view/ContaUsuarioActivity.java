package br.ufc.dc.es.meumedico.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.ufc.dc.es.meumedico.R;
import br.ufc.dc.es.meumedico.model.CuidadorDAO;
import br.ufc.dc.es.meumedico.model.LoginDAO;

public class ContaUsuarioActivity extends AppCompatActivity {

    private static final String PREF_NAME = "LoginActivityPreferences";
    String email;
    int id_usuario, id_recebido;
    CuidadorDAO dao_cuidador = new CuidadorDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta_usuario);

        SharedPreferences sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        TextView logado = (TextView) findViewById(R.id.textViewLogadoContaUsuario);
        logado.setText(sp.getString("nome", ""));
        TextView email = (TextView) findViewById(R.id.textViewEmailContaUsuario);
        this.email = sp.getString("email", "");
        email.setText(this.email);
        TextView id = (TextView) findViewById(R.id.textViewIdPerfil);
        this.id_usuario = sp.getInt("id_usuario", 0);
        id.setText(String.valueOf(this.id_usuario));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Minha Conta");
    }

    public void deletarContaUsuarioByEmail(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(ContaUsuarioActivity.this);

        builder.setMessage(R.string.MensagemDeletarConta)
                .setPositiveButton(R.string.SimDeletarConta, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final ProgressDialog progressDialog = new ProgressDialog(ContaUsuarioActivity.this);
                        progressDialog.setMessage("Deletando conta e voltando para a Tela de Login...");
                        progressDialog.show();
                        Thread mThread = new Thread(){
                            @Override
                            public void run() {
                                try {
                                    sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                LoginDAO dao = new LoginDAO(ContaUsuarioActivity.this);
                                dao.delete(email);
                                dao.close();
                                progressDialog.dismiss();
                                finish();
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
    }

    public void editarContaUsuario(View view){
        Log.i("Editar", "Implementar Editar Conta");
    }

    public void inserirCuidador(View view){
        showDialogCadastrarCuidador();
    }

    public void deletarCuidador(View view){
        showDialogExcluirCuidador();
    }

    public void showDialogCadastrarCuidador(){

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
                    Toast.makeText(ContaUsuarioActivity.this, "Campo ID obrigatório para cadastro de Cuidador",
                            Toast.LENGTH_SHORT).show();
                    diag.dismiss();
                }else{
                    try{
                        int id_recebido = Integer.parseInt(id_cuidador.getText().toString());

                        if(id_usuario==id_recebido){
                            Toast.makeText(ContaUsuarioActivity.this, "Você não pode se cadastrar como Cuidador" +
                                    ", por favor, tente novamente",
                                    Toast.LENGTH_LONG).show();
                            diag.dismiss();
                        }else{

                            LoginDAO dao_login = new LoginDAO(ContaUsuarioActivity.this);
                            CuidadorDAO dao_cuidador = new CuidadorDAO(ContaUsuarioActivity.this);

                            if(dao_login.verificarUsuarioTabelaLogin(id_recebido)==false){
                                Toast.makeText(ContaUsuarioActivity.this, "ID não encontrado" +
                                                ", por favor, tente novamente",
                                        Toast.LENGTH_LONG).show();
                                diag.dismiss();
                            }else if(dao_cuidador.verificaUsuarioCadastradoComoCuidador(id_usuario,id_recebido)){
                                Toast.makeText(ContaUsuarioActivity.this, "Você já cadastrou esse usuário de ID "
                                        + id_recebido + " como seu cuidador",
                                        Toast.LENGTH_LONG).show();
                                diag.dismiss();
                            }else{

                                dao_cuidador.insertCuidador(id_usuario, id_recebido);

                                Toast.makeText(ContaUsuarioActivity.this, "Cuidador cadastrado com sucesso",
                                        Toast.LENGTH_LONG).show();
                                diag.dismiss();
                            }
                            dao_cuidador.close();
                            dao_login.close();
                        }

                    }catch (NumberFormatException e){
                        Toast.makeText(ContaUsuarioActivity.this, "ID do Cuidador para cadastro é um inteiro" +
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

    public void showDialogExcluirCuidador(){

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
                    Toast.makeText(ContaUsuarioActivity.this, "Campo ID obrigatório para cadastro de Cuidador",
                            Toast.LENGTH_SHORT).show();
                    diag.dismiss();
                }else{
                    try{
                        id_recebido = Integer.parseInt(id_cuidador.getText().toString());

                        if(id_usuario==id_recebido){
                            Toast.makeText(ContaUsuarioActivity.this, "Você não pode se cadastrar como Cuidador, " +
                                            ", por favor, tente novamente",
                                    Toast.LENGTH_LONG).show();
                            diag.dismiss();
                        }else{

                            LoginDAO dao_login = new LoginDAO(ContaUsuarioActivity.this);
                            dao_cuidador = new CuidadorDAO(ContaUsuarioActivity.this);

                            if(dao_login.verificarUsuarioTabelaLogin(id_recebido)==false){
                                Toast.makeText(ContaUsuarioActivity.this, "ID não encontrado" +
                                                ", por favor, tente novamente",
                                        Toast.LENGTH_LONG).show();
                                diag.dismiss();
                            }else if(dao_cuidador.verificaUsuarioCadastradoComoCuidador(id_usuario,id_recebido)==false){
                                Toast.makeText(ContaUsuarioActivity.this, "Você não cadastrou esse usuário de ID "
                                                + id_recebido + " como seu cuidador, nada a fazer",
                                        Toast.LENGTH_LONG).show();
                                diag.dismiss();
                            }else{

                                diag.dismiss();
                                new AlertDialog.Builder(ContaUsuarioActivity.this)
                                        .setMessage("Você realmente deseja excluir esse cuidador?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                dao_cuidador.deleteCuidador(id_usuario,id_recebido);
                                                Toast.makeText(ContaUsuarioActivity.this, "Cuidador excluido com sucesso",
                                                        Toast.LENGTH_LONG).show();
                                            }})
                                        .setNegativeButton("Cancelar", null).show();
                            }

                            dao_cuidador.close();
                            dao_login.close();
                        }
                    }catch (NumberFormatException e){
                        Toast.makeText(ContaUsuarioActivity.this, "ID do Cuidador para cadastro é um inteiro" +
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
