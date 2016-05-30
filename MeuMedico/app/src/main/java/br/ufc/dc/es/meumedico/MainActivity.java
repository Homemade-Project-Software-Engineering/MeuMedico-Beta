package br.ufc.dc.es.meumedico;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.ufc.dc.es.dao.LoginDAO;
import br.ufc.dc.es.model.Login;


public class MainActivity extends Activity {

    Button next;
    Button conta;
    private Intent contaActivity;
    private ContaHelper helper;
    Login login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callSecondScreen();
        callContaActivity();
    }

    public void callSecondScreen(){
        next  = (Button) findViewById(R.id.btSecondScreen);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                helper = new ContaHelper(MainActivity.this);

                login = helper.pegaDadosLogin();

                LoginDAO dao = new LoginDAO(MainActivity.this);
                if(dao.fazerLogin(login)) {

                    Login informacoes = dao.getInformacoes(login);
                    Intent irParaATelaPrincipal = new Intent(MainActivity.this, SecondScreen.class);
                    irParaATelaPrincipal.putExtra("informacoes", informacoes);
                    startActivity(irParaATelaPrincipal);
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "Usuário ou login inválido", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void callContaActivity(){

        conta = (Button) findViewById(R.id.btCreateAccount);
        conta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contaActivity = new Intent(MainActivity.this, ContaActivity.class);
                startActivity(contaActivity);
            }
        });
    }
}
