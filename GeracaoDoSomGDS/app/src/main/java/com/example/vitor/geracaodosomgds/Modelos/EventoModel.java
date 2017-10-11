package com.example.vitor.geracaodosomgds.Modelos;

import java.util.Date;

/**
 * Created by Vitor on 15/05/2017.
 */

public class EventoModel {
    private Integer cdEvento;
    private Integer dia;
    private String mes;
    private String ano;
    private String qtdeIngressos;
    private String loginadmin;
    private String dtEvento;

    public Integer getCdEvento() { return cdEvento; }
    public void setCdEvento(Integer cdEvento) { this.cdEvento = cdEvento; }
    public Integer getDia()
    {
        //gambiarra do caraleous
        dia = Integer.valueOf(dtEvento.substring(0, 2));
        return dia;
    }
    public void setDia(Integer dia) { this.dia = dia; }
    public String getMes() { return mes; }
    public void setMes(String mes) { this.mes = mes; }
    public String getAno() { return ano; }
    public void setAno(String ano) { this.ano = ano;}
    public String getQtdeIngressos() { return qtdeIngressos;}
    public void setQtdeIngressos(String qtdeIngressos) { this.qtdeIngressos = qtdeIngressos; }
    public String getLoginAdmin() {return loginadmin; }
    public void setLoginAdmin(String loginadmin) {this.loginadmin = loginadmin; }
    public String getDtEvento() {return dtEvento;}
    public void setDtEvento(String dtEvento) {this.dtEvento = dtEvento;}

}
