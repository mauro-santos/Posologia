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
import br.com.maurosantos.android.posologia.dominio.RepMedicamento;
import br.com.maurosantos.android.posologia.dominio.RepPessoa;
import br.com.maurosantos.android.posologia.dominio.entidades.Medicamento;
import br.com.maurosantos.android.posologia.dominio.entidades.Pessoa;

public class MedicamentoActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private EditText edtFiltro;
    private ImageButton btnAdicionar;
    private ListView lstMedicamentos;

    private ArrayAdapter<Medicamento> adpMedicamentos;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private RepMedicamento repMedicamento;
    private FiltraDados filtraDados;

    public static final String PARAM_MEDICAMENTO = "MEDICAMENTO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento);

        edtFiltro = (EditText) findViewById(R.id.edtFiltro);
        btnAdicionar = (ImageButton) findViewById(R.id.btnAdicionar);
        lstMedicamentos = (ListView) findViewById(R.id.lstMedicamentos);

        btnAdicionar.setOnClickListener(this);
        lstMedicamentos.setOnItemClickListener(this);

        // Conexão com a base de dados.
        try {
            dataBase = new DataBase(this);
            conn = dataBase.getReadableDatabase();

            repMedicamento = new RepMedicamento(conn);

            adpMedicamentos = repMedicamento.listarMedicamentos(this);

            lstMedicamentos.setAdapter(adpMedicamentos);

            filtraDados = new FiltraDados(adpMedicamentos);
            edtFiltro.addTextChangedListener(filtraDados);

        } catch (SQLException e) {
            MessageBox.showAlert(this, getResources().getString(R.string.lbl_erro), getResources().getString(R.string.lbl_erro_conexao) + ": " + e.getMessage());
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
        Intent it = new Intent(this, MedicamentoNewActivity.class);
        startActivityForResult(it, 0);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Medicamento medicamento = adpMedicamentos.getItem(position);

        Intent it = new Intent(this, MedicamentoNewActivity.class);
        it.putExtra(PARAM_MEDICAMENTO, medicamento);

        startActivityForResult(it, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        adpMedicamentos = repMedicamento.listarMedicamentos(this);
        filtraDados.setArrayAdapter(adpMedicamentos);
        lstMedicamentos.setAdapter(adpMedicamentos);
    }

    private class FiltraDados implements TextWatcher {
        private ArrayAdapter<Medicamento> arrayAdapter;

        private FiltraDados(ArrayAdapter<Medicamento> arrayAdapter) {
            this.arrayAdapter = arrayAdapter;
        }

        public void setArrayAdapter(ArrayAdapter<Medicamento> arrayAdapter) {
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
