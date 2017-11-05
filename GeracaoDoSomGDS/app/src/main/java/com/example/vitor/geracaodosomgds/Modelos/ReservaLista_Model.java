package com.example.vitor.geracaodosomgds.Modelos;

/**
 * Created by Vitor on 08/10/2017.
 */

public class ReservaLista_Model {
    private String CPF;
    private String nome;
    private String dtEvento;
    private String nomeBanda;
    private String idReserva;

    private Boolean checkBoxReserva; //pra usar so no proxevento


    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDtEvento() {
        return dtEvento;
    }

    public void setDtEvento(String dtEvento) {
        this.dtEvento = dtEvento;
    }

    public String getNomeBanda() {
        return nomeBanda;
    }

    public void setNomeBanda(String nomeBanda) {
        this.nomeBanda = nomeBanda;
    }

    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public Boolean getCheckBoxReserva() {
        return checkBoxReserva;
    }

    public void setCheckBoxReserva(Boolean checkBoxReserva) {
        this.checkBoxReserva = checkBoxReserva;
    }
}
