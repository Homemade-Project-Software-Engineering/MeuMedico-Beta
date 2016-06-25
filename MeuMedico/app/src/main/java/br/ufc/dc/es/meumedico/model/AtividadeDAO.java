package br.ufc.dc.es.meumedico.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

import br.ufc.dc.es.meumedico.controller.domain.Atividade;

public class AtividadeDAO extends SQLiteOpenHelper {

    private static final String DATABASE = "bd_atividade";
    private static final int VERSAO = 5;
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
                "data TEXT," +
                "concluida INTEGER" +
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
        cv.put("concluida", -1);

        return getWritableDatabase().insert(TABELA, null, cv);
    }

    public List<Atividade> getListaAtividades(int id_usuario, String dataAtual) {

        final List<Atividade> atividades = new ArrayList<>();
        String sql = "SELECT * FROM " + TABELA + " where id_usuario = ? and data like ?";
        String args[] = new String[]{String.valueOf(id_usuario),dataAtual+"%"};
        final Cursor c = getReadableDatabase().rawQuery(sql, args);

        while(c.moveToNext()){
            Atividade atividade = new Atividade();
            atividade.setId(c.getInt(c.getColumnIndex("id")));
            atividade.setId_usuario(c.getInt(c.getColumnIndex("id_usuario")));
            atividade.setNome(c.getString(c.getColumnIndex("nome")));
            atividade.setDescricao(c.getString(c.getColumnIndex("descricao")));
            atividade.setConcluida(c.getInt(c.getColumnIndex("concluida")));

            String dataHora = c.getString(c.getColumnIndex("data"));

            String split[] = dataHora.split(" ");

            String dataPadrao[] = split[0].split("-");

            String data = dataPadrao[2]+"/"+dataPadrao[1]+"/"+dataPadrao[0];

            atividade.setData(data);
            atividade.setHora(split[1]);

            atividades.add(atividade);
        }
        c.close();
        return atividades;
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
            atividade.setConcluida(c.getInt(c.getColumnIndex("concluida")));

            String dataHora = c.getString(c.getColumnIndex("data"));

            String split[] = dataHora.split(" ");

            String dataPadrao[] = split[0].split("-");

            String data = dataPadrao[2]+"/"+dataPadrao[1]+"/"+dataPadrao[0];

            atividade.setData(data);
            atividade.setHora(split[1]);

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

        String args[] = {String.valueOf(atividade.getId())};
        getWritableDatabase().update(TABELA, cv, "id=?", args);
    }

    public long delete(Atividade atividadeSelecionadaItem) {

        String args[] = {String.valueOf(atividadeSelecionadaItem.getId())};
        return getWritableDatabase().delete(TABELA, "id=?", args);
    }

    public int getLastIDInserted(){

        String sql = "SELECT last_insert_rowid();";
        Cursor c = getReadableDatabase().rawQuery(sql, null);
        c.moveToFirst();
        int id = c.getInt(0);
        c.close();
        return id;
    }

    public void changeColumnConcluidabyNotification(int id, int trueOrFalse){

        ContentValues contentValues = new ContentValues();
        contentValues.put("concluida", trueOrFalse);

        String args[] = {String.valueOf(id)};
        getWritableDatabase().update(TABELA, contentValues,"id=?", args);
    }
}