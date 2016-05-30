package br.ufc.dc.es.meumedico;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.ufc.dc.es.dao.AtividadeDAO;
import br.ufc.dc.es.model.Atividade;

public class Cad_AtividadeActivity extends Activity{

    Button btsalvarAlterar;
    AtividadeHelper helper;
    int id_usuario;
    Atividade atividadeParaSerAlterada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_atividade);
        callCriarAtividade();

        atividadeParaSerAlterada = (Atividade) getIntent().getSerializableExtra("atividadeSelecionada");

        if(atividadeParaSerAlterada!=null){
            btsalvarAlterar.setText("Alterar");
            helper.atividadeParaSerAlterada(atividadeParaSerAlterada);
        }
    }

    public void callCriarAtividade(){

        helper = new AtividadeHelper(Cad_AtividadeActivity.this);

        btsalvarAlterar = (Button) findViewById(R.id.AtSalvar);
        btsalvarAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AtividadeDAO dao = new AtividadeDAO(Cad_AtividadeActivity.this);
                Atividade atividade = helper.pegaCamposAtividade();

                if(atividadeParaSerAlterada!=null){
                    atividade.setId(atividadeParaSerAlterada.getId());
                    atividade.setId_usuario(atividadeParaSerAlterada.getId_usuario());
                    dao.update(atividade);
                    Toast.makeText(Cad_AtividadeActivity.this, "Atividade atualizada com sucesso", Toast.LENGTH_SHORT).show();
                }else {
                    id_usuario = getIntent().getIntExtra("id_usuario", 0);
                    atividade.setId_usuario(id_usuario);
                    dao.insert(atividade);
                    Toast.makeText(Cad_AtividadeActivity.this, "Atividade inserida com sucesso", Toast.LENGTH_SHORT).show();
                }
                dao.close();
                finish();
            }
        });
    }
}
