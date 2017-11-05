package com.example.vitor.geracaodosomgds.Repositorios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.vitor.geracaodosomgds.BancoDeDados.setupBanco;
import com.example.vitor.geracaodosomgds.Modelos.ReservaLista_Model;
import com.example.vitor.geracaodosomgds.Modelos.ReservaModel;
import com.example.vitor.geracaodosomgds.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vitor on 14/05/2017.
 */

public class ReservaRepo {

    setupBanco sb;

    public ReservaRepo(Context c){
        sb = new setupBanco(c);
    }

    public void insertReserva(ReservaModel r){
        try {
            ContentValues values = new ContentValues();

            values.put("nome", r.getNome());
            values.put("cpf", r.getCpf());
            values.put("cdevento", r.getCdEvento());
            values.put("dtevento", r.getDtEvento());
            values.put("compareceu", "N");

            sb.getConexaoDB().insert("dbo_gds_reservas", null, values);
        }
        finally {
            sb.close();
        }
    }

    public List<ReservaModel> selectAllReservas(Integer cdevento){
        List<ReservaModel> reservas = new ArrayList<ReservaModel>();
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM dbo_gds_reservas WHERE cdevento = ?");

            String[] argumentos = new String[]{String.valueOf(cdevento)};
            Cursor cursor = sb.getConexaoDB().rawQuery(query.toString(), argumentos);
            cursor.moveToFirst();

            ReservaModel reservaModel;

            while (!cursor.isAfterLast()) {
                reservaModel = new ReservaModel();
                reservaModel.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                reservaModel.setCpf(cursor.getString(cursor.getColumnIndex("cpf")));
                reservaModel.setCdEvento(cursor.getInt(cursor.getColumnIndex("cdevento")));
                reservaModel.setDtEvento(cursor.getString(cursor.getColumnIndex("dtevento")));
                reservas.add(reservaModel);
                cursor.moveToNext();
            }
        }
        finally {
            sb.close();
        }
        return reservas;
    }

    public Boolean reservaExiste(String CPF)
    {
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM dbo_gds_reservas WHERE cpf = ?");

            String[] argumentos = new String[]{String.valueOf(CPF)};
            Cursor cursor = sb.getConexaoDB().rawQuery(query.toString(), argumentos);
            cursor.moveToFirst();
            if (cursor.getCount() <= 0)
                return false;
            else
                return true;
        }
        finally {
            sb.close();
        }
    }

    public void deletaReserva(String idReserva)
    {
        String[] argumentos = new String[]{idReserva} ;
        sb.getConexaoDB().delete("dbo_gds_reservas", "idreserva = ?", argumentos);
    }

    public List<ReservaLista_Model> selectReservas_Lista ()
    {
        List<ReservaLista_Model> reservas = new ArrayList<>();
        try {
            StringBuilder query = new StringBuilder();
            query.append(   "SELECT R.nome, R.cpf, B.nome nomeBanda, E.dtevento, R.idreserva\n" +
                            "FROM dbo_gds_reservas R\n" +
                            "JOIN dbo_gds_apresentacoes A\n" +
                            "ON A.cdevento = R.cdevento\n" +
                            "JOIN dbo_gds_eventos E\n" +
                            "ON E.cdevento = A.cdevento\n" +
                            "JOIN dbo_gds_bandas B\n" +
                            "ON a.login = B.login"  );
            Cursor cursor = sb.getConexaoDB().rawQuery(query.toString(), null);
            cursor.moveToFirst();

            ReservaLista_Model reservaModel;

            while (!cursor.isAfterLast()) {
                reservaModel = new ReservaLista_Model();
                reservaModel.setIdReserva(cursor.getString(cursor.getColumnIndex("idreserva")));
                reservaModel.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                reservaModel.setCPF(cursor.getString(cursor.getColumnIndex("cpf")));
                reservaModel.setDtEvento(cursor.getString(cursor.getColumnIndex("dtevento")));
                reservaModel.setNomeBanda(cursor.getString(cursor.getColumnIndex("nomeBanda")));
                reservas.add(reservaModel);
                cursor.moveToNext();
            }
        }
        finally {
            sb.close();
        }
        return reservas;
    }

    public void updateReserva(String idReserva, String nome, String CPF)
    {
        try {
            ContentValues values = new ContentValues();

            values.put("nome", nome);
            values.put("cpf", CPF);

            sb.getConexaoDB().update("dbo_gds_reservas", values, "idreserva = ?", new String[]{idReserva});
        }
        finally {
            sb.close();
        }
    }

    public Integer countReservaADM()
    {
        Integer res = 0;
        try {
            StringBuilder query = new StringBuilder();
            query.append(   "SELECT COUNT (*) count FROM dbo_gds_reservas " );
            Cursor cursor = sb.getConexaoDB().rawQuery(query.toString(), null);
            cursor.moveToFirst();

            if (!cursor.isAfterLast())
                res = cursor.getInt(cursor.getColumnIndex("count"));
        }
        finally {
            sb.close();
        }
        return res;
    }

    public List<ReservaLista_Model> reservasProxEvento(String cdevento)
    {
        List<ReservaLista_Model> reservas = new ArrayList<>();
        try {
            StringBuilder query = new StringBuilder();
            query.append(   "SELECT R.idreserva, R.nome, R.cpf, R.compareceu \n" +
                            "FROM dbo_gds_reservas R\n" +
                            "JOIN dbo_gds_apresentacoes A\n" +
                            "ON A.cdevento = R.cdevento\n" +
                            "JOIN dbo_gds_eventos E\n" +
                            "ON E.cdevento = A.cdevento\n" +
                            "WHERE E.cdevento = ? ");
            String[] argumentos = new String[]{cdevento};
            Cursor cursor = sb.getConexaoDB().rawQuery(query.toString(), argumentos);
            cursor.moveToFirst();

            ReservaLista_Model reservaModel;

            while (!cursor.isAfterLast()) {
                reservaModel = new ReservaLista_Model();
                reservaModel.setIdReserva(cursor.getString(cursor.getColumnIndex("idreserva")));
                reservaModel.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                reservaModel.setCPF(cursor.getString(cursor.getColumnIndex("cpf")));
                if (cursor.getString(cursor.getColumnIndex("compareceu")).trim().equals("S"))
                    reservaModel.setCheckBoxReserva(true);
                else
                    reservaModel.setCheckBoxReserva(false);
                reservas.add(reservaModel);
                cursor.moveToNext();
            }
        }
        finally {
            sb.close();
        }
        return reservas;
    }

    public void updateReservaCompareceu(String idReserva, Boolean compareceu)
    {
        try {
            ContentValues values = new ContentValues();

//            values.put("idreserva", idReserva);
            if (compareceu)
                values.put("compareceu", "S");
            else
                values.put("compareceu", "N");

            sb.getConexaoDB().update("dbo_gds_reservas", values, "idreserva = ?", new String[]{idReserva});
        }
        finally {
            sb.close();
        }
    }
}

