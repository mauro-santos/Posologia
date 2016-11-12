package br.com.maurosantos.android.posologia.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mauro on 12/11/2016.
 */

public class DataBase extends SQLiteOpenHelper {

    public DataBase(Context context) {
        super(context, "Posologia", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptSQL.getCreateTablePessoa());
        db.execSQL(ScriptSQL.getCreateTableMedicamento());
        db.execSQL(ScriptSQL.getCreateTablePessoaMedicamento());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
