package com.example.vitor.geracaodosomgds.Repositorios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.vitor.geracaodosomgds.BancoDeDados.setupBanco;
import com.example.vitor.geracaodosomgds.Modelos.ApresentacaoModel;
import com.example.vitor.geracaodosomgds.Modelos.EventoModel;
import com.example.vitor.geracaodosomgds.Modelos.ProximoEventoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vitor on 16/05/2017.
 */

public class ApresentacaoRepo {
    setupBanco sb;

    public ApresentacaoRepo(Context c){
        sb = new setupBanco(c);
    }

    public long insertApresentacao(ApresentacaoModel a)
    {
        long ik;
        try {
            ContentValues values = new ContentValues();

            values.put("cdevento", a.getCd_evento());
            values.put("login", a.getLogin());
            values.put("status", a.getStatus());
            ik = sb.getConexaoDB().insert("dbo_gds_apresentacoes", null, values);
        }
        finally {
            sb.close();
        }
        return ik;
    }

    public List<EventoModel> listaConvitesPorLogin(String login)
    {
        List<EventoModel> eventos = new ArrayList<EventoModel>();
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM dbo_gds_eventos JOIN dbo_gds_apresentacoes ON dbo_gds_eventos.cdevento = dbo_gds_apresentacoes.cdevento WHERE login = ? AND status = ?");
            String[] argumentos = {login, "AG"};
            Cursor cursor = sb.getConexaoDB().rawQuery(query.toString(), argumentos);


            EventoModel eventoModel;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                eventoModel = new EventoModel();
                eventoModel.setDtEvento(cursor.getString(cursor.getColumnIndex("dbo_gds_eventos.dtevento")));
                eventoModel.setQtdeIngressos(cursor.getString(cursor.getColumnIndex("qtdeingressos")));
                eventoModel.setCdEvento(cursor.getInt(cursor.getColumnIndex("cdevento")));
                eventos.add(eventoModel);
                cursor.moveToNext();
            }
        }
        finally {
            sb.close();
        }
        return eventos;
    }

    public List<ApresentacaoModel> listaApresentacaoPorLogin(String login, Integer cdevento)
    {
        List<ApresentacaoModel> apresentacoes = new ArrayList<ApresentacaoModel>();
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM dbo_gds_apresentacoes WHERE login = ? AND cdevento = ?");
            String[] argumentos = {login, cdevento.toString()};
            Cursor cursor = sb.getConexaoDB().rawQuery(query.toString(), argumentos);


            ApresentacaoModel apresentacaoModel;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                apresentacaoModel = new ApresentacaoModel();
                apresentacaoModel.setCd_evento(cursor.getInt(cursor.getColumnIndex("cdevento")));
                apresentacaoModel.setLogin(cursor.getString(cursor.getColumnIndex("login")));
                apresentacaoModel.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                apresentacoes.add(apresentacaoModel);
                cursor.moveToNext();
            }
        }
        finally {
            sb.close();
        }
        return apresentacoes;
    }

    public Integer countConvitesAceitosADM()
    {
        Integer res = 0;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT COUNT(*) count FROM dbo_gds_apresentacoes WHERE status = ? ");
            String[] argumentos = {"S"};
            Cursor cursor = sb.getConexaoDB().rawQuery(sql.toString(), argumentos);
            cursor.moveToFirst();

            if (!cursor.isAfterLast())
                res = cursor.getInt(cursor.getColumnIndex("count"));

        }
        finally
        {
            sb.close();
        }
        return res;
    }

    public List<String> retornaApresentacao(String cdevento)
    {
        List<String> diaevento = new ArrayList<String>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT dbo_gds_bandas.nome, dbo_gds_bandas.descricao, dbo_gds_apresentacoes.login, dbo_gds_apresentacoes.status " +
                        " FROM dbo_gds_apresentacoes " +
                        " JOIN dbo_gds_bandas ON dbo_gds_bandas.login = dbo_gds_apresentacoes.login " +
                        " WHERE dbo_gds_apresentacoes.cdevento = ?");
            String[] argumentos = {cdevento};
            Cursor cursor = sb.getConexaoDB().rawQuery(sql.toString(), argumentos);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                diaevento.add(cursor.getString(cursor.getColumnIndex("nome")));
                diaevento.add(cursor.getString(cursor.getColumnIndex("descricao")));
                diaevento.add(cursor.getString(cursor.getColumnIndex("status")));
                cursor.moveToNext();
            }
        }
        finally
        {
            sb.close();
        }
        return diaevento;
    }


    public List<ApresentacaoModel> selectAllApresentacoes()
    {
        List<ApresentacaoModel> apresentacoes = new ArrayList<ApresentacaoModel>();

        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM dbo_gds_apresentacoes");

            Cursor cursor = sb.getConexaoDB().rawQuery(query.toString(), null);
            cursor.moveToFirst();

            ApresentacaoModel apresentacao;

            while (!cursor.isAfterLast()) {
                apresentacao = new ApresentacaoModel();
                apresentacao.setLogin(cursor.getString(cursor.getColumnIndex("login")));
                apresentacao.setCd_evento(cursor.getInt(cursor.getColumnIndex("cdevento")));
                apresentacao.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                apresentacoes.add(apresentacao);
                cursor.moveToNext();
            }
        }
        finally {
            sb.close();
        }
        return apresentacoes;
    }

    public void deletaTudo()
    {
        sb.getConexaoDB().delete("dbo_gds_apresentacoes", null, null);
    }

    public void responderConvite(ApresentacaoModel a)
    {
        ContentValues values = new ContentValues();
        values.put("status", a.getStatus());
        String[] argumentos = {a.getLogin()};
        sb.getConexaoDB().update("dbo_gds_apresentacoes", values, "login=?",argumentos);
    }

    public ProximoEventoModel getProxApresentacao()
    {
        ProximoEventoModel pe = new ProximoEventoModel();
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT E.cdevento, E.dtevento, B.nome, (SELECT COUNT (*) FROM dbo_gds_reservas R WHERE R.cdevento = E.cdevento) qtdreservas \n" +
                    " FROM dbo_gds_eventos E\n" +
                    " JOIN dbo_gds_apresentacoes A\n" +
                    " ON a.cdevento = E.cdevento " +
                    " JOIN dbo_gds_bandas B\n" +
                    " ON B.login = A.login\n" +
                    " WHERE A.status = ? "+
                    " ORDER BY E.dtevento ASC ");
            String[] argumentos = {"S"};
            Cursor cursor = sb.getConexaoDB().rawQuery(query.toString(), argumentos);

            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                pe.setNomeBanda(cursor.getString(cursor.getColumnIndex("nome")));
                pe.setDtEvento(cursor.getString(cursor.getColumnIndex("dtevento")));
                pe.setQtdReserva(cursor.getString(cursor.getColumnIndex("qtdreservas")));
                pe.setCdevento(cursor.getString(cursor.getColumnIndex("cdevento")));
            }
        }
        finally {
            sb.close();
        }

        return pe;
    }

}
