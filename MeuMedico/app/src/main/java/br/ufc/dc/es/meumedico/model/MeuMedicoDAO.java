package br.ufc.dc.es.meumedico.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.ufc.dc.es.meumedico.controller.domain.Atividade;

public class MeuMedicoDAO extends SQLiteOpenHelper {

    private static final String DATABASE = "bd_meu_medico";
    private static final int VERSAO = 13;
    private static final String TABELA_ATIVIDADE = "Atividade";
    private static final String TABELA_CUIDADOR = "Cuidador";

    public MeuMedicoDAO(Context context) {

        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql_atividade = "CREATE TABLE " + TABELA_ATIVIDADE + "(" +
                "id INTEGER PRIMARY KEY, " +
                "id_usuario INTEGER," +
                "nome TEXT, " +
                "descricao TEXT, " +
                "data TEXT," +
                "concluida INTEGER" +
                ");";

        String sql_cuidador = "CREATE TABLE " + TABELA_CUIDADOR + "(" +
                "id INTEGER PRIMARY KEY, "+
                "id_cuidador INTEGER," +
                "id_usuario INTEGER"+
                ");";

        db.execSQL(sql_atividade);
        db.execSQL(sql_cuidador);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS " + TABELA_ATIVIDADE;
        String sql_cuidador = "DROP TABLE IF EXISTS " + TABELA_CUIDADOR;
        db.execSQL(sql);
        db.execSQL(sql_cuidador);
        onCreate(db);
    }

    public long insert(Atividade atividade) {

        ContentValues cv = new ContentValues();
        cv.put("id_usuario", atividade.getId_usuario());
        cv.put("nome", atividade.getNome());
        cv.put("descricao", atividade.getDescricao());
        cv.put("data", atividade.getData());
        cv.put("concluida", -1);

        return getWritableDatabase().insert(TABELA_ATIVIDADE, null, cv);
    }

    public List<Atividade> getListaAtividades(int id_usuario, String dataAtual) {

        //pega as atividades do usuário baseado na data atual
        final List<Atividade> atividades = new ArrayList<>();
        String sql = "SELECT * FROM " + TABELA_ATIVIDADE + " where id_usuario = ? and data like ?";
        String args[] = new String[]{String.valueOf(id_usuario),dataAtual+"%"};
        Cursor c = getReadableDatabase().rawQuery(sql, args);

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

        //pega as atividades dos usuários que ele cuida(se houver)
        sql = "SELECT * FROM " + TABELA_CUIDADOR + " where id_cuidador = ?";
        String args_verifica_cuidador[] = {String.valueOf(id_usuario)};
        Cursor c_cuidador = getReadableDatabase().rawQuery(sql, args_verifica_cuidador);

        //verifica se tem cuidador
        //e aqui propriamente pega todas as atividades do usuário que ele cuida na data de hoje
        while(c_cuidador.moveToNext()){
            Log.i("entrou_while","entrou");
            sql = "SELECT * FROM " + TABELA_ATIVIDADE + " where id_usuario = ? and data like ?";
            String args_pega_atividades_usuario[] = {String.valueOf(c_cuidador.getInt(c_cuidador.getColumnIndex("id_usuario"))),dataAtual+"%"};
            Cursor c_atividade = getReadableDatabase().rawQuery(sql, args_pega_atividades_usuario);

            while(c_atividade.moveToNext()) {

                Atividade atividade = new Atividade();
                atividade.setId(c_atividade.getInt(c_atividade.getColumnIndex("id")));
                atividade.setId_usuario(c_atividade.getInt(c_atividade.getColumnIndex("id_usuario")));
                atividade.setNome(c_atividade.getString(c_atividade.getColumnIndex("nome")));
                atividade.setDescricao(c_atividade.getString(c_atividade.getColumnIndex("descricao")));
                atividade.setConcluida(c_atividade.getInt(c_atividade.getColumnIndex("concluida")));

                String dataHora = c_atividade.getString(c_atividade.getColumnIndex("data"));

                String split[] = dataHora.split(" ");

                String dataPadrao[] = split[0].split("-");

                String data = dataPadrao[2] + "/" + dataPadrao[1] + "/" + dataPadrao[0];

                atividade.setData(data);
                atividade.setHora(split[1]);

                atividades.add(atividade);
            }

            c_atividade.close();
        }

        c_cuidador.close();
        c.close();
        return atividades;
    }

    public List<Atividade> getListaAtividades(int id_usuario) {

        final List<Atividade> atividades = new ArrayList<>();
        String sql = "SELECT * FROM " + TABELA_ATIVIDADE + " where id_usuario = ?";
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
        getWritableDatabase().update(TABELA_ATIVIDADE, cv, "id=?", args);
    }

    public long delete(Atividade atividadeSelecionadaItem) {

        String args[] = {String.valueOf(atividadeSelecionadaItem.getId())};
        return getWritableDatabase().delete(TABELA_ATIVIDADE, "id=?", args);
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
        getWritableDatabase().update(TABELA_ATIVIDADE, contentValues,"id=?", args);
    }

    public long insertCuidador(int id_usuario, int id_cuidador) {

        ContentValues cv = new ContentValues();
        cv.put("id_cuidador", id_cuidador);
        cv.put("id_usuario", id_usuario);

        return getWritableDatabase().insert(TABELA_CUIDADOR, null, cv);
    }

    public boolean verificaUsuarioCadastradoComoCuidador(int id_usuario, int id_cuidador) {

        String sql = "SELECT * FROM " + TABELA_CUIDADOR + " WHERE id_usuario = ? and id_cuidador = ?;";
        String[] args = {String.valueOf(id_usuario),String.valueOf(id_cuidador)};
        final Cursor cursor = getReadableDatabase().rawQuery(sql, args);

        if(cursor.moveToFirst()){
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }
    }

    public void deleteCuidador(int id_usuario, int id_cuidador) {

        String args[] = {String.valueOf(id_usuario), String.valueOf(id_cuidador)};
        getWritableDatabase().delete(TABELA_CUIDADOR, "id_usuario=? and id_cuidador=?", args);
    }
}