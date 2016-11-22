package br.com.maurosantos.android.posologia;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import br.com.maurosantos.android.posologia.app.MessageBox;
import br.com.maurosantos.android.posologia.database.DataBase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DataBase dataBase;

    private Button btnPessoa;
    private Button btnMedicamento;
    private Button btnPosologia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPessoa = (Button) findViewById(R.id.btnPessoa);
        btnMedicamento = (Button) findViewById(R.id.btnMedicamento);
        btnPosologia = (Button) findViewById(R.id.btnPosologia);

        btnPessoa.setOnClickListener(this);
        btnMedicamento.setOnClickListener(this);
        btnPosologia.setOnClickListener(this);

        // Criar a base de dados.
        try {
            dataBase = new DataBase(this);
        } catch (SQLException e) {
            MessageBox.showAlert(this, getResources().getString(R.string.lbl_erro), getResources().getString(R.string.lbl_erro_criar_base) + ": " + e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v == btnPessoa) {
            Intent it = new Intent(this, PessoaActivity.class);
            startActivity(it);
        } else if (v == btnMedicamento) {
            Intent it = new Intent(this, MedicamentoActivity.class);
            startActivity(it);
        } else if (v == btnPosologia) {
            Intent it = new Intent(this, PosologiaActivity.class);
            startActivity(it);
        }
    }
}
