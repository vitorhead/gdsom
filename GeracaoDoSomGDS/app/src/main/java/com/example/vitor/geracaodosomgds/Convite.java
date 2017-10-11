package com.example.vitor.geracaodosomgds;

/**
 * Created by Vitor on 21/04/2017.
 */

public class Convite
{
    private String evento;
    private String hora;
    private String data;

    public Convite (String evento, String hora, String data)
    {
        this.evento = evento;
        this.data = data;
        this.hora = hora;
    }

    public String getEvento() {  return evento; }
    public void setEvento(String evento) { this.evento = evento; }
    public String getHora() {  return hora; }
    public void setHora(String hora) {this.hora = hora;}
    public String getData() { return data;}
    public void setData(String data) {  this.data = data; }
}
