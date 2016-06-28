package br.ufc.dc.es.meumedico.controller.helper;

import android.widget.EditText;
import java.text.DateFormat;
import java.util.Date;

import br.ufc.dc.es.meumedico.R;
import br.ufc.dc.es.meumedico.controller.domain.Login;
import br.ufc.dc.es.meumedico.view.ContaActivity;
import br.ufc.dc.es.meumedico.view.MainActivity;

public class ContaHelper {

    private EditText campoNome,campoEmail,campoSenha;

    public ContaHelper(ContaActivity activity){

        campoNome = (EditText) activity.findViewById(R.id.CadastroNome);
        campoEmail = (EditText) activity.findViewById(R.id.CadastroEmail);
        campoSenha = (EditText) activity.findViewById(R.id.CadastroSenha);
    }

    public ContaHelper(MainActivity activity){

        campoEmail = (EditText) activity.findViewById(R.id.etGuestName);
        campoSenha = (EditText) activity.findViewById(R.id.passwordTextEdit);
    }

    public Login pegaCadastroLogin(){

        String nome = campoNome.getText().toString();
        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();

        Login login = new Login();

        login.setName(nome);
        login.setEmail(email);
        login.setCrypted_password(senha);
        login.setSalt(senha);
        login.setCreated_at(DateFormat.getInstance().format(new Date()));
        login.setUpdated_at(null);

        return login;
    }

    public Login pegaDadosLogin(){

        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();

        Login login = new Login();
        login.setEmail(email);
        login.setCrypted_password(senha);

        return login;
    }

    public void contaParaSerAlterada(Login contaParaSerAlterada) {

        campoNome.setText(contaParaSerAlterada.getName());
        campoEmail.setText(contaParaSerAlterada.getEmail());
    }
}
