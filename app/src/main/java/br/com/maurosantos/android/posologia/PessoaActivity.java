package br.com.maurosantos.android.posologia;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import br.com.maurosantos.android.posologia.app.MessageBox;
import br.com.maurosantos.android.posologia.database.DataBase;
import br.com.maurosantos.android.posologia.dominio.RepPessoa;
import br.com.maurosantos.android.posologia.dominio.entidades.Pessoa;

public class PessoaActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private EditText edtFiltro;
    private ImageButton btnAdicionar;
    private ListView lstPessoas;

    private ArrayAdapter<Pessoa> adpPessoas;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private RepPessoa repPessoa;
    private FiltraDados filtraDados;

    public static final String PARAM_PESSOA = "PESSOA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa);

        edtFiltro = (EditText) findViewById(R.id.edtFiltro);
        btnAdicionar = (ImageButton) findViewById(R.id.btnAdicionar);
        lstPessoas = (ListView) findViewById(R.id.lstPessoas);

        btnAdicionar.setOnClickListener(this);
        lstPessoas.setOnItemClickListener(this);

        // Conexão com a base de dados.
        try {
            dataBase = new DataBase(this);
            conn = dataBase.getReadableDatabase();

            repPessoa = new RepPessoa(conn);

            adpPessoas = repPessoa.listarPessoas(this);

            lstPessoas.setAdapter(adpPessoas);

            filtraDados = new FiltraDados(adpPessoas);
            edtFiltro.addTextChangedListener(filtraDados);

        } catch (SQLException e) {
            MessageBox.showAlert(this, "Erro", "Erro ao conectar no banco de dados: " + e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (conn != null) {
            conn.close();
        }
    }

    @Override
    public void onClick(View v) {
        Intent it = new Intent(this, PessoaNewActivity.class);
        startActivityForResult(it, 0);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Pessoa pessoa = adpPessoas.getItem(position);

        Intent it = new Intent(this, PessoaNewActivity.class);
        it.putExtra(PARAM_PESSOA, pessoa);

        startActivityForResult(it, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        adpPessoas = repPessoa.listarPessoas(this);
        filtraDados.setArrayAdapter(adpPessoas);
        lstPessoas.setAdapter(adpPessoas);
    }

    private class FiltraDados implements TextWatcher {
        private ArrayAdapter<Pessoa> arrayAdapter;

        private FiltraDados(ArrayAdapter<Pessoa> arrayAdapter) {
            this.arrayAdapter = arrayAdapter;
        }

        public void setArrayAdapter(ArrayAdapter<Pessoa> arrayAdapter) {
            this.arrayAdapter = arrayAdapter;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            arrayAdapter.getFilter().filter(s);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
