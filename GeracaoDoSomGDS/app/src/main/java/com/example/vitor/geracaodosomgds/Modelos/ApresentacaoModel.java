package com.example.vitor.geracaodosomgds.Modelos;

/**
 * Created by Vitor on 16/05/2017.
 */

public class ApresentacaoModel {
    private Integer cd_evento;
    private String login;
    private String status;

    public Integer getCd_evento() {
        return cd_evento;
    }

    public void setCd_evento(Integer cd_evento) {
        this.cd_evento = cd_evento;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
