package br.com.maurosantos.android.posologia.database;

/**
 * Created by Mauro on 12/11/2016.
 */

public class ScriptSQL {

    // Table: Pessoa (DropTable)
    public static String getDropTablePessoa() {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("DROP TABLE IF EXISTS Pessoa;");

        return sqlBuilder.toString();
    }

    // Table: Pessoa (CreateTable)
    public static String getCreateTablePessoa() {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS Pessoa ( ");
        sqlBuilder.append("_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("Nome VARCHAR(150) NOT NULL UNIQUE, ");
        sqlBuilder.append("Ativo BOOLEAN DEFAULT(1) NOT NULL, ");
        sqlBuilder.append("DataRegistro DATETIME NOT NULL DEFAULT(getDate()) ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    // Table: Medicamento (DropTable)
    public static String getDropTableMedicamento() {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("DROP TABLE IF EXISTS Medicamento;");

        return sqlBuilder.toString();
    }

    // Table: Medicamento (CreateTable)
    public static String getCreateTableMedicamento() {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE Medicamento ( ");
        sqlBuilder.append("_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("Nome VARCHAR(150) NOT NULL UNIQUE, ");
        sqlBuilder.append("Descricao VARCHAR(255), ");
        sqlBuilder.append("Ativo BOOLEAN DEFAULT(1) NOT NULL, ");
        sqlBuilder.append("DataRegistro DATETIME NOT NULL DEFAULT(getDate()) ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    // Table: PessoaMedicamento (DropTable)
    public static String getDropTablePessoaMedicamento() {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("DROP TABLE IF EXISTS PessoaMedicamento;");

        return sqlBuilder.toString();
    }

    // Table: PessoaMedicamento (CreateTable)
    public static String getCreateTablePessoaMedicamento() {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE PessoaMedicamento ( ");
        sqlBuilder.append("_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("PessoaId INTEGER REFERENCES Pessoa(_id) NOT NULL, ");
        sqlBuilder.append("MedicamentoId INTEGER REFERENCES Medicamento(_id) NOT NULL, ");
        sqlBuilder.append("Horario DATETIME NOT NULL, ");
        sqlBuilder.append("Observacao VARCHAR(255), ");
        sqlBuilder.append("Ativo BOOLEAN DEFAULT(1) NOT NULL, ");
        sqlBuilder.append("DataRegistro DATETIME NOT NULL DEFAULT(getDate()) ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }
}
