package br.com.maurosantos.android.posologia.dominio.entidades;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mauro on 12/11/2016.
 */

public class PessoaMedicamento implements Serializable {

    public static String TABELA = "PessoaMedicamento";

    public static String ID = "_id";
    public static String PESSOAID = "PessoaId";
    public static String MEDICAMENTOID = "MedicamentoId";
    public static String HORARIO = "Horario";
    public static String OBSERVACAO = "Observacao";
    public static String ATIVO = "Ativo";
    public static String DATAREGISTRO = "DataRegistro";

    private long Id;
    private long PessoaId;
    private long MedicamentoId;
    private Date Horario;
    private String Observacao;
    private boolean Ativo;
    private Date DataRegistro;

    public PessoaMedicamento() {
        this.Ativo = true;
        this.DataRegistro = new Date();
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public long getPessoaId() {
        return PessoaId;
    }

    public void setPessoaId(long pessoaId) {
        PessoaId = pessoaId;
    }

    public long getMedicamentoId() {
        return MedicamentoId;
    }

    public void setMedicamentoId(long medicamentoId) {
        MedicamentoId = medicamentoId;
    }

    public Date getHorario() {
        return Horario;
    }

    public void setHorario(Date horario) {
        Horario = horario;
    }

    public String getObservacao() {
        return Observacao;
    }

    public void setObservacao(String observacao) {
        Observacao = observacao;
    }

    public boolean isAtivo() {
        return Ativo;
    }

    public void setAtivo(boolean ativo) {
        Ativo = ativo;
    }

    public Date getDataRegistro() {
        return DataRegistro;
    }

    public void setDataRegistro(Date dataRegistro) {
        DataRegistro = dataRegistro;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
