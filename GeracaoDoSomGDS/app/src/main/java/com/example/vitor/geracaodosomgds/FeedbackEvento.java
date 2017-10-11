package com.example.vitor.geracaodosomgds;

/**
 * Created by Vitor on 01/10/2017.
 */

public class FeedbackEvento {
    private String idFeedback;
    private String nomeBanda;
    private String dataEvento;
    private byte[] foto;
    private Integer reserva_cd_evento;
    private String dsfeedback;
    private String dtfeedback;
    private Integer reserva_cpf_cliente;

    public FeedbackEvento(String nomeBanda, String dataEvento, byte[] foto)
    {
        this.setNomeBanda(nomeBanda);
        this.setDataEvento(dataEvento);
        this.setFoto(foto);
    }


    public String getNomeBanda() {
        return nomeBanda;
    }

    public void setNomeBanda(String nomeBanda) {
        this.nomeBanda = nomeBanda;
    }

    public String getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(String dataEvento) {
        this.dataEvento = dataEvento;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Integer getReserva_cd_evento() {
        return reserva_cd_evento;
    }

    public void setReserva_cd_evento(Integer reserva_cd_evento) {
        this.reserva_cd_evento = reserva_cd_evento;
    }

    public String getDsfeedback() {
        return dsfeedback;
    }

    public void setDsfeedback(String dsfeedback) {
        this.dsfeedback = dsfeedback;
    }

    public String getDtfeedback() {
        return dtfeedback;
    }

    public void setDtfeedback(String dtfeedback) {
        this.dtfeedback = dtfeedback;
    }

    public Integer getReserva_cpf_cliente() {
        return reserva_cpf_cliente;
    }

    public void setReserva_cpf_cliente(Integer reserva_cpf_cliente) {
        this.reserva_cpf_cliente = reserva_cpf_cliente;
    }

    public String getIdFeedback() {
        return idFeedback;
    }

    public void setIdFeedback(String idFeedback) {
        this.idFeedback = idFeedback;
    }
}
