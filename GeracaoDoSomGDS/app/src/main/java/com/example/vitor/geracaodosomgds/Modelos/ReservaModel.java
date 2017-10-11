package com.example.vitor.geracaodosomgds.Modelos;

/**
 * Created by Vitor on 14/05/2017.
 */

public class ReservaModel {
    private String nome;
    private String cpf;
    private Integer cdEvento;
    private String dtEvento;


    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String reserva) { this.cpf = reserva; }

    public Integer getCdEvento() {
        return cdEvento;
    }

    public void setCdEvento(Integer cdEvento) {
        this.cdEvento = cdEvento;
    }

    public String getDtEvento() {
        return dtEvento;
    }

    public void setDtEvento(String dtEvento) {
        this.dtEvento = dtEvento;
    }
}
