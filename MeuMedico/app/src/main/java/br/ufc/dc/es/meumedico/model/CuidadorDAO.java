package br.ufc.dc.es.meumedico.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CuidadorDAO extends SQLiteOpenHelper{

    private static final String DATABASE = "bd_cuidador";
    private static final int VERSAO = 1;
    private static final String TABELA = "Cuidador";

    public CuidadorDAO(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " + TABELA + "(" +
                "id INTEGER PRIMARY KEY, "+
                "id_cuidador INTEGER," +
                "id_usuario INTEGER"+
                ");";
        try {
            db.execSQL(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS " + TABELA;
        db.execSQL(sql);
        onCreate(db);
    }

    public long insertCuidador(int id_usuario, int id_cuidador) {

        ContentValues cv = new ContentValues();
        cv.put("id_cuidador", id_cuidador);
        cv.put("id_usuario", id_usuario);

        return getWritableDatabase().insert(TABELA, null, cv);
    }

    public boolean verificaUsuarioCadastradoComoCuidador(int id_usuario, int id_cuidador) {

        String sql = "SELECT * FROM " + TABELA + " WHERE id_usuario = ? and id_cuidador = ?;";
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
        getWritableDatabase().delete(TABELA, "id_usuario=? and id_cuidador=?", args);
    }
}
