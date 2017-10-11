package com.example.vitor.geracaodosomgds;

/**
 * Created by Vitor on 21/04/2017.
 */

public class dados_SessaoIniciada
{
    private static String login;
    private static String autenticacao;
    private static String nomebanda;
    private static String desc;
    private static byte[] foto;
    private static String site;
    private static String telefone;

    public static  byte[] getFoto() { return foto; }
    public static void setFoto(byte[] foto) {dados_SessaoIniciada.foto = foto; }
    public static String getSite() { return site;}
    public static void setSite(String site) { dados_SessaoIniciada.site = site; }
    public static String getTelefone() {return telefone;}
    public static void setTelefone(String telefone) {dados_SessaoIniciada.telefone = telefone;}
    public String getLogin() { return login;}
    public void setLogin(String login) { this.login = login; }
    public String getAutenticacao() { return autenticacao;  }
    public void setAutenticacao(String autenticacao) { this.autenticacao = autenticacao; }
    public String getNomebanda() {  return nomebanda;  }
    public void setNomebanda(String nomebanda) { this.nomebanda = nomebanda; }
    public String getDesc() { return desc; }
    public void setDesc(String desc) { this.desc = desc; }
}
