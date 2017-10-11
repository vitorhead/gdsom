package com.example.vitor.geracaodosomgds.Repositorios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.example.vitor.geracaodosomgds.BancoDeDados.setupBanco;
import com.example.vitor.geracaodosomgds.Modelos.EventoModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vitor on 15/05/2017.
 */

public class EventoRepo
{
    setupBanco sb;

    public EventoRepo(Context c){
        sb = new setupBanco(c);
    }

    public long insertEvento(EventoModel e){
        try {
            ContentValues values = new ContentValues();

            values.put("dtevento", e.getDtEvento());
            values.put("qtdeingressos", e.getQtdeIngressos());
            values.put("loginadmin", e.getLoginAdmin());
            return sb.getConexaoDB().insert("dbo_gds_eventos", null, values);
        }
        finally {
            sb.close();
        }
    }

    public List<EventoModel> selectAllEventos()
    {
        List<EventoModel> eventos = new ArrayList<EventoModel>();

        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM dbo_gds_eventos ORDER BY cdevento");

            Cursor cursor = sb.getConexaoDB().rawQuery(query.toString(), null);
            cursor.moveToFirst();

            EventoModel eventoModel;

            while (!cursor.isAfterLast()) {
                eventoModel = new EventoModel();
                eventoModel.setQtdeIngressos(cursor.getString(cursor.getColumnIndex("qtdeingressos")));
                eventoModel.setCdEvento(cursor.getInt(cursor.getColumnIndex("cdevento")));
                eventoModel.setDtEvento(cursor.getString(cursor.getColumnIndex("dtevento")));
                eventos.add(eventoModel);
                cursor.moveToNext();
            }
        }
        finally {
            sb.close();
        }
        return eventos;
    }

    public void deletaTudo()
    {
        sb.getConexaoDB().delete("dbo_gds_eventos", null, null);
    }

}
