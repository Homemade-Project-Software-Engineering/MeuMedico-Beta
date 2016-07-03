package br.ufc.dc.es.meumedico.view;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.ufc.dc.es.meumedico.controller.helper.ContaHelper;
import br.ufc.dc.es.meumedico.R;
import br.ufc.dc.es.meumedico.controller.helper.ValidacaoHelper;
import br.ufc.dc.es.meumedico.model.LoginDAO;
import br.ufc.dc.es.meumedico.controller.domain.Login;

public class ContaActivity extends AppCompatActivity {

    Button createAccount;
    ContaHelper helper;
    private TextInputLayout emailTIL, passwordTIL;
    private EditText nameET, emailET, passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta);

        nameET = (EditText) findViewById(R.id.CadastroNome);

        emailTIL = (TextInputLayout) findViewById(R.id.contaInputUserEmail);
        emailET = (EditText) findViewById(R.id.CadastroEmail);

        passwordTIL = (TextInputLayout) findViewById(R.id.contaInputUserPassword);
        passwordET = (EditText) findViewById(R.id.CadastroSenha);

        createAccount = (Button) findViewById(R.id.btcriarConta);

        callCreateAccount();

        Login contaParaSerAlterada = (Login) getIntent().getSerializableExtra("contaParaSerAlterada");

        if(contaParaSerAlterada!=null){
            getSupportActionBar().show();
            getSupportActionBar().setTitle("Editar Conta");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            createAccount.setText("Editar Conta");

            helper = new ContaHelper(this);

            helper.contaParaSerAlterada(contaParaSerAlterada);
        }else{
            getSupportActionBar().show();
            getSupportActionBar().setTitle("Cadastrar Conta");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void callCreateAccount(){
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValidacaoHelper vh = new ValidacaoHelper(ContaActivity.this);
                if (vh.verificaCamposVaziosConta()) {
                    Toast.makeText(ContaActivity.this, "Todos os campos são obrigatórios", Toast.LENGTH_LONG).show();
                }else if(validateEmail() && validatePassword()){
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validateEmail() {

        String email = emailET.getText().toString();

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailTIL.requestFocus();
            emailTIL.setError("Email inválido");
            return false;
        }else{
            emailTIL.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        if(passwordET.getText().toString().length()<8){
            passwordTIL.setError("Sua senha deve ter no mínimo 8 caracteres");
            return false;
        }else{
            passwordTIL.setErrorEnabled(false);
            return true;
        }
    }
}
