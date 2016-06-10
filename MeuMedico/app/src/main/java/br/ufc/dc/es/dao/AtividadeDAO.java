package br.ufc.dc.es.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

import br.ufc.dc.es.model.Atividade;

public class AtividadeDAO extends SQLiteOpenHelper {

    private static final String DATABASE = "bd_atividade";
    private static final int VERSAO = 1;
    private static final String TABELA = "Atividade";

    public AtividadeDAO(Context context) {

        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " + TABELA + "(" +
                "id INTEGER PRIMARY KEY, " +
                "id_usuario INTEGER," +
                "nome TEXT, " +
                "descricao TEXT, " +
                "data TEXT, " +
                "hora TEXT" +
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS " + TABELA;
        db.execSQL(sql);
        onCreate(db);
    }

    public long insert(Atividade atividade) {

        ContentValues cv = new ContentValues();
        cv.put("id_usuario", atividade.getId_usuario());
        cv.put("nome", atividade.getNome());
        cv.put("descricao", atividade.getDescricao());
        cv.put("data", atividade.getData());
        cv.put("hora", atividade.getHora());

        return getWritableDatabase().insert(TABELA, null, cv);
    }

    public List<Atividade> getListaAtividades(int id_usuario) {

        final List<Atividade> atividades = new ArrayList<>();
        String sql = "SELECT * FROM " + TABELA + " where id_usuario = ?";
        String args[] = new String[]{String.valueOf(id_usuario)};
        final Cursor c = getReadableDatabase().rawQuery(sql, args);

        while(c.moveToNext()){
            Atividade atividade = new Atividade();
            atividade.setId(c.getInt(c.getColumnIndex("id")));
            atividade.setId_usuario(c.getInt(c.getColumnIndex("id_usuario")));
            atividade.setNome(c.getString(c.getColumnIndex("nome")));
            atividade.setDescricao(c.getString(c.getColumnIndex("descricao")));
            atividade.setData(c.getString(c.getColumnIndex("data")));
            atividade.setHora(c.getString(c.getColumnIndex("hora")));

            atividades.add(atividade);
        }
        c.close();
        return atividades;
    }

    public void update(Atividade atividade) {

        ContentValues cv = new ContentValues();
        cv.put("nome", atividade.getNome());
        cv.put("descricao", atividade.getDescricao());
        cv.put("data", atividade.getData());
        cv.put("hora", atividade.getHora());

        String args[] = {String.valueOf(atividade.getId())};
        getWritableDatabase().update(TABELA, cv, "id=?", args);
    }

    public long delete(Atividade atividadeSelecionadaItem) {

        String args[] = {String.valueOf(atividadeSelecionadaItem.getId())};
        return getWritableDatabase().delete(TABELA, "id=?", args);
    }
}