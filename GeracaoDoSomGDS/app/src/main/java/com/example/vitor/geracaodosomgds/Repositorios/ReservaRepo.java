package com.example.vitor.geracaodosomgds.Repositorios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.vitor.geracaodosomgds.BancoDeDados.setupBanco;
import com.example.vitor.geracaodosomgds.Modelos.ReservaLista_Model;
import com.example.vitor.geracaodosomgds.Modelos.ReservaModel;

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

    public void deletaReserva(String CPF)
    {
        String[] argumentos = new String[]{CPF} ;
        sb.getConexaoDB().delete("dbo_gds_reservas", "cpf", argumentos);
    }

    public List<ReservaLista_Model> selectReservas_Lista ()
    {
        List<ReservaLista_Model> reservas = new ArrayList<>();
        try {
            StringBuilder query = new StringBuilder();
            query.append(   "SELECT R.nome, R.cpf, B.nome nomeBanda, E.dtevento\n" +
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
}
