package br.ufc.dc.es.meumedico.controller.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.ufc.dc.es.meumedico.R;
import br.ufc.dc.es.meumedico.controller.domain.Atividade;
import br.ufc.dc.es.meumedico.controller.domain.Cuidador;
import br.ufc.dc.es.meumedico.controller.interfaces.RecyclerViewOnClickListenerHack;
import br.ufc.dc.es.meumedico.model.LoginDAO;


public class CuidadorAdapter extends RecyclerView.Adapter<CuidadorAdapter.MyViewHolder> {
    private List<Cuidador> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    ContextMenu.ContextMenuInfo info;
    Context c;

    public CuidadorAdapter(Context c, List<Cuidador> l){
        this.c = c;
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public CuidadorAdapter(){}


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Log.i("LOG", "onCreateViewHolder()");
        View v = mLayoutInflater.inflate(R.layout.item_cuidador, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        Log.i("LOG", "onBindViewHolder()");
        myViewHolder.cuidador_nome.setText(mList.get(position).getNome());
        myViewHolder.cuidador_id.setText(String.valueOf(mList.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{
        public TextView cuidador_nome;
        public TextView cuidador_id;

        public MyViewHolder(View itemView) {
            super(itemView);
            cuidador_nome = (TextView) itemView.findViewById(R.id.cuidador_nome);
            cuidador_id = (TextView) itemView.findViewById(R.id.cuidador_id);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mRecyclerViewOnClickListenerHack != null){
                mRecyclerViewOnClickListenerHack.onClickListener(v, getAdapterPosition());
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            new AtividadeAdapter().info = menuInfo;
            menu.setHeaderTitle("Selecione a ação desejada");
            menu.add(0, R.id.itemContextMenuDeletar, 0, "Deletar");
        }
    }
}
