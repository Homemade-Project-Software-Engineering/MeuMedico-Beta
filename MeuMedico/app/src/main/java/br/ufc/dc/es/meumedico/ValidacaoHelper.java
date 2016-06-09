package br.ufc.dc.es.meumedico;

import android.widget.EditText;

public class ValidacaoHelper {

    EditText nome;
    EditText email;
    EditText senha;
    EditText descricao;
    EditText data;
    EditText hora;

    public ValidacaoHelper(ContaActivity activity){

        nome = (EditText) activity.findViewById(R.id.CadastroNome);
        email = (EditText) activity.findViewById(R.id.CadastroEmail);
        senha = (EditText) activity.findViewById(R.id.CadastroSenha);
    }

    public ValidacaoHelper(Cad_AtividadeActivity activity){

        nome = (EditText) activity.findViewById(R.id.AtNome);
        descricao = (EditText) activity.findViewById(R.id.AtDescricao);
        data = (EditText) activity.findViewById(R.id.AtData);
        hora = (EditText) activity.findViewById(R.id.AtHora);
    }

    public boolean verificaCamposVaziosConta(){

        return nome.getText().toString().equals("")
                || email.getText().toString().equals("")
                || senha.getText().toString().equals("");
    }

    public boolean verificaCamposVaziosAtividade(){

        return nome.getText().toString().equals("")
                || descricao.getText().toString().equals("")
                || data.getText().toString().equals("")
                || hora.getText().toString().equals("");
    }
}
