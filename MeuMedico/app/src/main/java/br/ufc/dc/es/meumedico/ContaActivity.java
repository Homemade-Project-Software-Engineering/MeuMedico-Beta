package br.ufc.dc.es.meumedico;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.ufc.dc.es.dao.LoginDAO;
import br.ufc.dc.es.model.Login;

public class ContaActivity extends Activity {

    Button createAccount;
    ContaHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta);
        callCreateAccount();
    }

    public void callCreateAccount(){
        createAccount = (Button) findViewById(R.id.btcriarConta);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValidacaoHelper vh = new ValidacaoHelper(ContaActivity.this);
                if(vh.verificaCamposVaziosConta()){
                    Toast.makeText(ContaActivity.this, "Todos os campos são obrigatórios", Toast.LENGTH_LONG).show();
                }else {
                    helper = new ContaHelper(ContaActivity.this);
                    final Login login = helper.pegaCadastroLogin();

                    LoginDAO dao = new LoginDAO(ContaActivity.this);
                    dao.insert(login);
                    dao.close();
                    Toast.makeText(ContaActivity.this, "Conta criada com sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
