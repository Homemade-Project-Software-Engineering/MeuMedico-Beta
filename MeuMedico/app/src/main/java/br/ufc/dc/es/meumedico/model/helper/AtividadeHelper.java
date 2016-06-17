package br.ufc.dc.es.meumedico.model.helper;

import android.widget.EditText;

import br.ufc.dc.es.meumedico.R;
import br.ufc.dc.es.meumedico.model.domain.Atividade;
import br.ufc.dc.es.meumedico.view.Cad_AtividadeActivity;

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

        String dataPadrao[] = data.split("/");

        String hora = campoHora.getText().toString();

        data = dataPadrao[2]+"-"+dataPadrao[1]+"-"+dataPadrao[0];

        Atividade atividade = new Atividade();
        atividade.setNome(nome);
        atividade.setDescricao(descricao);
        atividade.setData(data+" "+hora);

        return atividade;
    }

    public void atividadeParaSerAlterada(Atividade atividadeParaSerAlterada) {

        campoNome.setText(atividadeParaSerAlterada.getNome());
        campoDescricao.setText(atividadeParaSerAlterada.getDescricao());
        campoData.setText(atividadeParaSerAlterada.getData());
        campoHora.setText(atividadeParaSerAlterada.getHora());
    }
}
