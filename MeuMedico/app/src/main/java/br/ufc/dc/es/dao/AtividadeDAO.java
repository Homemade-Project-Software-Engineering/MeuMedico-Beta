package br.ufc.dc.es.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.ufc.dc.es.model.Atividade;

/**
 * Created by César on 29/05/2016.
 */
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

    public void insert(Atividade atividade) {

        ContentValues cv = new ContentValues();
        cv.put("id_usuario", atividade.getId());
        cv.put("nome", atividade.getNome());
        cv.put("descricao", atividade.getDescricao());
        cv.put("data", atividade.getData());
        cv.put("hora", atividade.getHora());

        getWritableDatabase().insert(TABELA, null, cv);
    }

    public List<Atividade> getListaAtividades() {

        final List<Atividade> atividades = new ArrayList<>();
        String sql = "SELECT * FROM " + TABELA + ";";
        final Cursor c = getReadableDatabase().rawQuery(sql, null);

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

        return atividades;
    }
}