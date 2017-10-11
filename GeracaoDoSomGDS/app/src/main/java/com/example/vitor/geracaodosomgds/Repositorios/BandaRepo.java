package com.example.vitor.geracaodosomgds.Repositorios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.vitor.geracaodosomgds.BancoDeDados.setupBanco;
import com.example.vitor.geracaodosomgds.Modelos.BandaModel;
import com.example.vitor.geracaodosomgds.Modelos.EventoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vitor on 13/05/2017.
 */

public class BandaRepo
{
    setupBanco sb;

    public BandaRepo(Context c){
        sb = new setupBanco(c);
    }

    public void insert(BandaModel b){
        try {
            ContentValues values = new ContentValues();
            values.put("login", b.getEmail());
            values.put("senha", b.getSenha());
            values.put("nome", b.getNome());
            values.put("descricao", b.getDescricao());
            values.put("email", b.getEmail());
            values.put("telefone", b.getTelefone());
            values.put("foto", b.getFoto());
            values.put("site", b.getSite());
            values.put("numacesso", b.getNumacesso());

            sb.getConexaoDB().insert("dbo_gds_bandas", null, values);
        }
        finally {
            sb.close();
        }
    }

    //overload
    public BandaModel selectUmaBanda(String login, String senhaHash){
        BandaModel bandaRetornada = new BandaModel();
        try {
            String[] argumentos = new String[]{login, senhaHash};
            Cursor cursor = sb.getConexaoDB().rawQuery("SELECT * FROM dbo_gds_bandas WHERE login = ? AND senha = ? ", argumentos);


            if (cursor.isAfterLast())
                bandaRetornada = null;
            else {
                cursor.moveToFirst();
                bandaRetornada.setLogin(cursor.getString(cursor.getColumnIndex("login")));
                bandaRetornada.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
                bandaRetornada.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                bandaRetornada.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                bandaRetornada.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                bandaRetornada.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
                bandaRetornada.setFoto(cursor.getBlob(cursor.getColumnIndex("foto")));
                bandaRetornada.setSite(cursor.getString(cursor.getColumnIndex("site")));
                bandaRetornada.setNumacesso(cursor.getInt(cursor.getColumnIndex("numacesso")));
            }
        }
        finally {
            sb.close();
        }

        return bandaRetornada;
    }

    //overload
    public BandaModel selectUmaBanda(String login){
        BandaModel bandaRetornada = new BandaModel();
        try {
            String[] argumentos = new String[]{login};
            Cursor cursor = sb.getConexaoDB().rawQuery("SELECT * FROM dbo_gds_bandas WHERE login = ?", argumentos);


            if (cursor.isAfterLast())
                bandaRetornada = null;
            else {
                cursor.moveToFirst();
                bandaRetornada.setLogin(cursor.getString(cursor.getColumnIndex("login")));
                bandaRetornada.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
                bandaRetornada.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                bandaRetornada.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                bandaRetornada.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                bandaRetornada.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
                bandaRetornada.setFoto(cursor.getBlob(cursor.getColumnIndex("foto")));
                bandaRetornada.setSite(cursor.getString(cursor.getColumnIndex("site")));
                bandaRetornada.setNumacesso(cursor.getInt(cursor.getColumnIndex("numacesso")));
            }
        }
        finally {
            sb.close();
        }
        return bandaRetornada;
    }

    public BandaModel selectEdita(String email){
        BandaModel bandaRetornada = new BandaModel();
        try {
            String[] argumentos = new String[]{email};
            Cursor cursor = sb.getConexaoDB().rawQuery("SELECT * FROM dbo_gds_bandas WHERE login = ?", argumentos);


            if (cursor.isAfterLast())
                bandaRetornada = null;
            else {
                cursor.moveToFirst();
                bandaRetornada.setLogin(cursor.getString(cursor.getColumnIndex("login")));
                bandaRetornada.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
                bandaRetornada.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                bandaRetornada.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                bandaRetornada.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                bandaRetornada.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
                bandaRetornada.setFoto(cursor.getBlob(cursor.getColumnIndex("foto")));
                bandaRetornada.setSite(cursor.getString(cursor.getColumnIndex("site")));
            }
        }
        finally {
            sb.close();
        }

        return bandaRetornada;
    }

    public void updateBanda(BandaModel b)
    {
        try {
            ContentValues values = new ContentValues();

//        values.put("login", b.getEmail());
//        values.put("senha", b.getSenha());
            values.put("nome", b.getNome());
            values.put("descricao", b.getDescricao());
            values.put("email", b.getEmail());
            values.put("telefone", b.getTelefone());
            values.put("foto", b.getFoto());
            values.put("site", b.getSite());

            sb.getConexaoDB().update("dbo_gds_bandas", values, "login = ?", new String[]{b.getLogin()});
        }
        finally {
            sb.close();
        }
    }

    public void updateBandaSenhaFIRSTACCESS(String login, String senha, int numacesso)
    {
        try {
            ContentValues values = new ContentValues();

            values.put("senha", senha);
            values.put("numacesso", numacesso);

            sb.getConexaoDB().update("dbo_gds_bandas", values, "login = ?", new String[]{login});
        }
        finally {
            sb.close();
        }
    }

    public void reset() {
        sb.getConexaoDB().execSQL("DELETE * FROM dbo_gds_bandas");
        sb.getConexaoDB().execSQL("DELETE * FROM dbo_gds_apresentacoes");
        sb.getConexaoDB().execSQL("DELETE * FROM dbo_gds_eventos");
        sb.getConexaoDB().execSQL("DELETE * FROM dbo_gds_reservas");
    }

    public List<BandaModel> listaBandas()
    {
        List<BandaModel> bandas = new ArrayList<BandaModel>();
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM dbo_gds_bandas ORDER BY nome");

            Cursor cursor = sb.getConexaoDB().rawQuery(query.toString(), null);
            cursor.moveToFirst();

            BandaModel bandaModel;

            while (!cursor.isAfterLast()) {
                bandaModel = new BandaModel();
                bandaModel.setLogin(cursor.getString(cursor.getColumnIndex("login")));
                bandaModel.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                bandaModel.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                bandaModel.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                //fazer os outros campos dps

                bandas.add(bandaModel);
                cursor.moveToNext();
            }
        }
        finally {
            sb.close();
        }
        return bandas;

    }
    //para retornar todas, fazer com while !cursor.IsAfterLast
}
