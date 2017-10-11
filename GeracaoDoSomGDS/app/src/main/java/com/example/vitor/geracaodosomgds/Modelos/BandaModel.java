package com.example.vitor.geracaodosomgds.Modelos;

/**
 * Created by Vitor on 13/05/2017.
 */

public class BandaModel
{
    private String login;
    private String senha;
    private String nome;
    private String descricao;
    private String email;
    private String telefone;
    private byte[] foto;
    private String site;
    private Integer numacesso;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Integer getNumacesso() {
        return numacesso;
    }

    public void setNumacesso(Integer numacesso) {
        this.numacesso = numacesso;
    }
}
