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
import br.com.maurosantos.android.posologia.dominio.RepPessoa;
import br.com.maurosantos.android.posologia.dominio.entidades.Pessoa;

public class PessoaNewActivity extends AppCompatActivity {

    private EditText edtNome;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private RepPessoa repPessoa;
    private Pessoa pessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa_new);

        edtNome = (EditText) findViewById(R.id.edtNome);

        Bundle bundle = getIntent().getExtras();

        // Se recebeu parametros da lista, modo edição.
        if ((bundle != null) && (bundle.containsKey(PessoaActivity.PARAM_PESSOA))) {
            pessoa = ((Pessoa) bundle.getSerializable(PessoaActivity.PARAM_PESSOA));
            preencheDados();
        } else {
            pessoa = new Pessoa();
        }

        try {
            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();

            // Deixa o objeto de consulta pronto.
            repPessoa = new RepPessoa(conn);
        } catch (SQLException e) {
            MessageBox.showAlert(this, "Erro", "Erro ao conectar ao banco de dados" + e.getMessage());
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
        inflater.inflate(R.menu.menu_pessoa_new, menu);

        // Se estiver editando apresenta opção Excluir.
        if (pessoa.getId() != 0) {
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
        edtNome.setText(pessoa.getNome());
    }

    private boolean salvar() {
        try {
            pessoa.setNome(edtNome.getText().toString());

            if (pessoa.getNome().isEmpty()) {
                MessageBox.showInfo(this, "Atenção", "Informe o nome");
                return false;
            } else {
                if (pessoa.getId() == 0) {
                    repPessoa.inserirPessoa(pessoa);
                } else {
                    repPessoa.alterarPessoa(pessoa);
                }

                return true;
            }
        } catch (Exception e) {
            MessageBox.showAlert(this, "Erro", "Erro ao salvar os dados" + e.getMessage());
            return false;
        }
    }

    private boolean excluir() {
        try {
            repPessoa.excluirPessoa(pessoa.getId());
            return true;
        } catch (Exception e) {
            MessageBox.showAlert(this, "Erro", "Erro ao excluir os dados" + e.getMessage());
            return false;
        }
    }
}
