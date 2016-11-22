package br.com.maurosantos.android.posologia;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import br.com.maurosantos.android.posologia.app.MessageBox;
import br.com.maurosantos.android.posologia.database.DataBase;
import br.com.maurosantos.android.posologia.dominio.RepMedicamento;
import br.com.maurosantos.android.posologia.dominio.RepPessoa;
import br.com.maurosantos.android.posologia.dominio.entidades.Medicamento;
import br.com.maurosantos.android.posologia.dominio.entidades.Pessoa;

public class MedicamentoNewActivity extends AppCompatActivity {

    private EditText edtNome;
    private EditText edtDescricao;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private RepMedicamento repMedicamento;
    private Medicamento medicamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento_new);

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtDescricao = (EditText) findViewById(R.id.edtDescricao);

        Bundle bundle = getIntent().getExtras();

        // Se recebeu parametros da lista, modo edição.
        if ((bundle != null) && (bundle.containsKey(MedicamentoActivity.PARAM_MEDICAMENTO))) {
            medicamento = ((Medicamento) bundle.getSerializable(MedicamentoActivity.PARAM_MEDICAMENTO));
            preencheDados();
        } else {
            medicamento = new Medicamento();
        }

        try {
            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();

            // Deixa o objeto de consulta pronto.
            repMedicamento = new RepMedicamento(conn);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_medicamento_new, menu);

        // Se estiver editando apresenta opção Excluir.
        if (medicamento.getId() != 0) {
            menu.getItem(1).setVisible(true);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mn_salvar:
                if (salvar()) {
                    finish();
                }
                break;
            case R.id.mn_excluir:
                if (excluir()) {
                    finish();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void preencheDados() {
        edtNome.setText(medicamento.getNome());
        edtDescricao.setText(medicamento.getDescricao());
    }

    private boolean salvar() {
        try {
            medicamento.setNome(edtNome.getText().toString());
            medicamento.setDescricao(edtDescricao.getText().toString());

            if (medicamento.getNome().isEmpty()) {
                MessageBox.showInfo(this, getResources().getString(R.string.lbl_atencao), getResources().getString(R.string.lbl_nome_requerido));
                return false;
            } else {
                if (medicamento.getId() == 0) {
                    repMedicamento.inserirMedicamento(medicamento);
                } else {
                    repMedicamento.alterarMedicamento(medicamento);
                }

                return true;
            }
        } catch (Exception e) {
            MessageBox.showAlert(this, getResources().getString(R.string.lbl_erro), getResources().getString(R.string.lbl_erro_salvar) + ": " + e.getMessage());
            return false;
        }
    }

    private boolean excluir() {
        try {
            repMedicamento.excluirMedicamento(medicamento.getId());
            return true;
        } catch (Exception e) {
            MessageBox.showAlert(this, getResources().getString(R.string.lbl_erro), getResources().getString(R.string.lbl_erro_excluir) + ": " + e.getMessage());
            return false;
        }
    }
}
