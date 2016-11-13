package br.com.maurosantos.android.posologia.dominio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

import br.com.maurosantos.android.posologia.ArrayAdapterPessoa;
import br.com.maurosantos.android.posologia.R;
import br.com.maurosantos.android.posologia.dominio.entidades.Pessoa;

/**
 * Created by Mauro on 12/11/2016.
 */

public class RepPessoa {

    private SQLiteDatabase conn;

    public RepPessoa(SQLiteDatabase conn) {
        this.conn = conn;
    }

    private ContentValues preencheContentValues(Pessoa pessoa) {
        ContentValues values = new ContentValues();

        values.put(Pessoa.NOME, pessoa.getNome());
        values.put(Pessoa.ATIVO, pessoa.isAtivo());
        values.put(Pessoa.DATAREGISTRO, pessoa.getDataRegistro().getTime());

        return values;
    }

    public void inserirPessoa(Pessoa pessoa) {
        ContentValues values = preencheContentValues(pessoa);
        conn.insertOrThrow(Pessoa.TABELA, null, values);
    }

    public void alterarPessoa(Pessoa pessoa) {
        ContentValues values = preencheContentValues(pessoa);
        conn.update(Pessoa.TABELA, values, "_id = ?", new String[]{String.valueOf(pessoa.getId())});
    }

    public void excluirPessoa(long id) {
        conn.delete(Pessoa.TABELA, "_id = ?", new String[]{String.valueOf(id)});
    }

    public ArrayAdapterPessoa listarPessoas(Context context) {
        ArrayAdapterPessoa adpPessoas = new ArrayAdapterPessoa(context, R.layout.item_pessoa);

        Cursor cursor = conn.query(Pessoa.TABELA, null, null, null, null, null, null);

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();

            do {
                Pessoa pessoa = new Pessoa();

                pessoa.setId(cursor.getLong(cursor.getColumnIndex(Pessoa.ID)));
                pessoa.setNome(cursor.getString(cursor.getColumnIndex(Pessoa.NOME)));
                pessoa.setAtivo(cursor.getInt(cursor.getColumnIndex(Pessoa.ATIVO))==1?true:false);
                pessoa.setDataRegistro(new Date(cursor.getLong(cursor.getColumnIndex(Pessoa.DATAREGISTRO))));

                adpPessoas.add(pessoa);
            } while (cursor.moveToNext());
        }

        return adpPessoas;
    }
}
