package com.example.vitor.geracaodosomgds.Modelos;

/**
 * Created by Vitor on 15/09/2017.
 */

public class FeedbackModel {
    private Integer idFeedback;
    private String dt_feedback;
    private String ds_feedback;
    private Integer reserva_cd_evento;
    private String reserva_cpf_cliente;
    private Integer cd_tipo_feedback;

    public Integer getIdFeedback() {
        return idFeedback;
    }

    public void setIdFeedback(Integer idFeedback) {
        this.idFeedback = idFeedback;
    }

    public String getDt_feedback() {
        return dt_feedback;
    }

    public void setDt_feedback(String dt_feedback) {
        this.dt_feedback = dt_feedback;
    }

    public String getDs_feedback() {
        return ds_feedback;
    }

    public void setDs_feedback(String ds_feedback) {
        this.ds_feedback = ds_feedback;
    }

    public Integer getReserva_cd_evento() {
        return reserva_cd_evento;
    }

    public void setReserva_cd_evento(Integer reserva_cd_evento) {
        this.reserva_cd_evento = reserva_cd_evento;
    }

    public String getReserva_cpf_cliente() {
        return reserva_cpf_cliente;
    }

    public void setReserva_cpf_cliente(String reserva_cpf_cliente) {
        this.reserva_cpf_cliente = reserva_cpf_cliente;
    }

    public Integer getCd_tipo_feedback() {
        return cd_tipo_feedback;
    }

    public void setCd_tipo_feedback(Integer cd_tipo_feedback) {
        this.cd_tipo_feedback = cd_tipo_feedback;
    }
}
