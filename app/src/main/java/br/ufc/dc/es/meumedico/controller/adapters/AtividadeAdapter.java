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
import br.ufc.dc.es.meumedico.controller.interfaces.RecyclerViewOnClickListenerHack;
import br.ufc.dc.es.meumedico.model.LoginDAO;


public class AtividadeAdapter extends RecyclerView.Adapter<AtividadeAdapter.MyViewHolder> {
    private List<Atividade> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    ContextMenu.ContextMenuInfo info;
    private Context c;

    public AtividadeAdapter(Context c, List<Atividade> l){
        this.c = c;
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    AtividadeAdapter(){}


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Log.i("LOG", "onCreateViewHolder()");
        View v = mLayoutInflater.inflate(R.layout.item_atividade, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        Log.i("LOG", "onBindViewHolder()");
        myViewHolder.tvModel.setText(mList.get(position).getDescricao());
        myViewHolder.tvBrand.setText(mList.get(position).getHora());
        LoginDAO dao = new LoginDAO(c);
        String nome_usuario = dao.getNomeByIdUsuario(mList.get(position).getId_usuario());
        dao.close();
        myViewHolder.tvName.setText(nome_usuario);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{
        TextView tvModel;
        TextView tvBrand;
        TextView tvName;

        MyViewHolder(View itemView) {
            super(itemView);
            tvModel = (TextView) itemView.findViewById(R.id.tv_model);
            tvBrand = (TextView) itemView.findViewById(R.id.tv_brand);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);

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
            menu.add(0, R.id.itemContextMenuEditar, 0, "Editar");//groupId, itemId, order, title
            menu.add(0, R.id.itemContextMenuDeletar, 0, "Deletar");
        }
    }
}
