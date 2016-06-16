package br.ufc.dc.es.meumedico.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.ufc.dc.es.meumedico.R;
import br.ufc.dc.es.meumedico.controller.LoginDAO;
import br.ufc.dc.es.meumedico.model.Login;

public class ContaUsuarioActivity extends AppCompatActivity {

    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta_usuario);

        Login informacoes = (Login) getIntent().getSerializableExtra("informacoes");
        Bundle infosFacebook = getIntent().getBundleExtra("infosFacebook");

        if (informacoes != null) {
            TextView logado = (TextView) findViewById(R.id.textViewLogado);
            logado.setText(informacoes.getName());
            TextView email = (TextView) findViewById(R.id.textViewEmail);
            email.setText(informacoes.getEmail());
            this.email = informacoes.getEmail();
        } else if (infosFacebook != null) {
            TextView logado = (TextView) findViewById(R.id.textViewLogado);
            logado.setText(infosFacebook.get("first_name").toString());
            TextView email = (TextView) findViewById(R.id.textViewEmail);
            email.setText(infosFacebook.get("email").toString());
            this.email = infosFacebook.get("email").toString();
        }

    }

    public void deletarContaUsuarioByEmail(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(ContaUsuarioActivity.this);

        builder.setMessage(R.string.MensagemDeletarConta)
                .setPositiveButton(R.string.SimDeletarConta, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final ProgressDialog progressDialogdialog = new ProgressDialog(ContaUsuarioActivity.this);
                        progressDialogdialog.setMessage("Deletando conta e voltando para a Tela de Login...");
                        progressDialogdialog.show();
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
                                progressDialogdialog.dismiss();
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

    public void editarContaUsuario(){

    }
}
