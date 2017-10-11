package com.example.vitor.geracaodosomgds.Repositorios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.vitor.geracaodosomgds.BancoDeDados.setupBanco;
import com.example.vitor.geracaodosomgds.FeedbackEvento;
import com.example.vitor.geracaodosomgds.Modelos.ApresentacaoModel;
import com.example.vitor.geracaodosomgds.Modelos.EventoModel;
import com.example.vitor.geracaodosomgds.Modelos.FeedbackLista_Model;
import com.example.vitor.geracaodosomgds.Modelos.FeedbackModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vitor on 15/09/2017.
 */

public class FeedbackRepo {
    setupBanco sb;

    public FeedbackRepo(Context c)
    {
        sb = new setupBanco(c);
    }

    public long insertFeedback(FeedbackModel f)
    {
        long ik;
        try {
            ContentValues values = new ContentValues();

            switch(f.getCd_tipo_feedback())
            {
                case 1:
                    values.put("cd_tipo_feedback", f.getCd_tipo_feedback());
                    values.put("ds_feedback", f.getDs_feedback());
                    values.put("dt_feedback", f.getDt_feedback());
                    values.put("reserva_cd_evento", f.getReserva_cd_evento());
                    values.put("reserva_cpf_cliente", f.getReserva_cpf_cliente());
                    break;

                case 2:
                    values.put("cd_tipo_feedback", f.getCd_tipo_feedback());
                    values.put("ds_feedback", f.getDs_feedback());
                    values.put("dt_feedback", f.getDt_feedback());
                    break;
            }

            ik = sb.getConexaoDB().insert("dbo_gds_feedback", null, values);
        }
        finally {
            sb.close();
        }
        return ik;
    }

    public List<FeedbackEvento> listaFeedbacksCPF(String cpf)
    {
        List<FeedbackEvento> fbs = new ArrayList<>();
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT b.nome, b.foto, r.dtevento, r.cdevento FROM dbo_gds_reservas r JOIN dbo_gds_apresentacoes a ON a.cdevento = r.cdevento JOIN dbo_gds_bandas b ON b.login = a.login WHERE r.cpf = ? ");
            String[] argumentos = {cpf};
            Cursor cursor = sb.getConexaoDB().rawQuery(query.toString(), argumentos);

            FeedbackEvento fbmodelo;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                fbmodelo = new FeedbackEvento(" "," ",null);
                fbmodelo.setNomeBanda(cursor.getString(cursor.getColumnIndex("nome")));
                fbmodelo.setDataEvento(cursor.getString(cursor.getColumnIndex("dtevento")));
                fbmodelo.setFoto(cursor.getBlob(cursor.getColumnIndex("foto")));
                fbmodelo.setReserva_cd_evento(cursor.getInt(cursor.getColumnIndex("cdevento")));
                fbs.add(fbmodelo);
                cursor.moveToNext();
            }
        }
        finally {
            sb.close();
        }
        return fbs;
    }


    public List<FeedbackLista_Model> listaFeedbacks_ADM()
    {
        List<FeedbackLista_Model> fbs = new ArrayList<>();
        try {
            StringBuilder query = new StringBuilder();
            query.append(   "SELECT   F.ds_feedback \n" +
                            "\t\t,F.dt_feedback\n" +
                            "\t\t,F.idFeedback\n" +
                            "\t\t,F.reserva_cpf_cliente\n" +
                            "\t\t,F.cd_tipo_feedback\n" +
                            "\t\t,E.dtevento\n" +
                            "\t\t,B.nome\n" +
                            "\t\t,A.cdevento\n" +
                            "FROM dbo_gds_feedback F\n" +
                            "LEFT JOIN dbo_gds_apresentacoes A\n" +
                            "ON A.cdevento = F.reserva_cd_evento\n" +
                            "LEFT JOIN dbo_gds_bandas B\n" +
                            "ON B.login = A.login\n" +
                            "LEFT JOIN dbo_gds_eventos E\n" +
                            "ON E.cdevento = A.cdevento" );

            Cursor cursor = sb.getConexaoDB().rawQuery(query.toString(), null);

            FeedbackLista_Model fbmodelo;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                fbmodelo = new FeedbackLista_Model();
                fbmodelo.setIdFeedback(cursor.getString(cursor.getColumnIndex("idfeedback")));
                fbmodelo.setNomebanda(cursor.getString(cursor.getColumnIndex("nome")));
                fbmodelo.setDtfeedback(cursor.getString(cursor.getColumnIndex("dt_feedback")));
                fbmodelo.setCdevento(cursor.getString(cursor.getColumnIndex("cdevento")));
                fbmodelo.setDsfeedback(cursor.getString(cursor.getColumnIndex("ds_feedback")));
                fbmodelo.setReserva_cpf_cliente(cursor.getString(cursor.getColumnIndex("reserva_cpf_cliente")));
                fbmodelo.setDtevento(cursor.getString(cursor.getColumnIndex("dtevento")));
                fbmodelo.setTipofeedback(cursor.getString(cursor.getColumnIndex("cd_tipo_feedback")));
                fbs.add(fbmodelo);
                cursor.moveToNext();
            }
        }
        finally {
            sb.close();
        }
        return fbs;
    }
}
