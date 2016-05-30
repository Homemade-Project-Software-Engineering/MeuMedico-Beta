package br.ufc.dc.es.meumedico;

import android.widget.EditText;

import br.ufc.dc.es.model.Atividade;

public class AtividadeHelper {

    EditText campoNome, campoDescricao, campoData, campoHora;

    public AtividadeHelper(Cad_AtividadeActivity activity){

        campoNome = (EditText) activity.findViewById(R.id.AtNome);
        campoDescricao = (EditText) activity.findViewById(R.id.AtDescricao);
        campoData = (EditText) activity.findViewById(R.id.AtData);
        campoHora = (EditText) activity.findViewById(R.id.AtHora);
    }

    public Atividade pegaCamposAtividade(){

        String nome = campoNome.getText().toString();
        String descricao = campoDescricao.getText().toString();
        String data = campoData.getText().toString();
        String hora = campoHora.getText().toString();

        Atividade atividade = new Atividade();
        atividade.setNome(nome);
        atividade.setDescricao(descricao);
        atividade.setData(data);
        atividade.setHora(hora);

        return atividade;
    }
}
