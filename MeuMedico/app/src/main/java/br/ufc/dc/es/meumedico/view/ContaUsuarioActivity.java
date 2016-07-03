package br.ufc.dc.es.meumedico.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.ufc.dc.es.meumedico.R;
import br.ufc.dc.es.meumedico.controller.domain.Login;
import br.ufc.dc.es.meumedico.model.MeuMedicoDAO;
import br.ufc.dc.es.meumedico.model.LoginDAO;

public class ContaUsuarioActivity extends AppCompatActivity {

    private static final String PREF_NAME = "LoginActivityPreferences";
    String email,nome;
    int id_usuario, id_recebido;
    MeuMedicoDAO dao_cuidador = new MeuMedicoDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta_usuario);

        SharedPreferences sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        TextView logado = (TextView) findViewById(R.id.textViewLogadoContaUsuario);
        this.nome = sp.getString("nome", "");
        logado.setText(nome);
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

        Login conta = new Login();
        conta.setId(id_usuario);
        conta.setName(nome);
        conta.setEmail(email);

        Intent contaParaSerAlterada = new Intent(ContaUsuarioActivity.this, ContaActivity.class);
        contaParaSerAlterada.putExtra("contaParaSerAlterada", conta);
        startActivity(contaParaSerAlterada);
    }
}
