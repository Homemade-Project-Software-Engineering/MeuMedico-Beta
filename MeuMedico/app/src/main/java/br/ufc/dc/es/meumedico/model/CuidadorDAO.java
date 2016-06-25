package br.ufc.dc.es.meumedico.model;

import android.content.Context;
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
}
