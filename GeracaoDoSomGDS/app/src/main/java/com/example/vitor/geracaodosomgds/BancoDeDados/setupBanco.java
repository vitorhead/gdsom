package com.example.vitor.geracaodosomgds.BancoDeDados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.vitor.geracaodosomgds.Modelos.EventoModel;
import com.example.vitor.geracaodosomgds.Repositorios.EventoRepo;
import com.example.vitor.geracaodosomgds.telacalendario;

/**
 * Created by Vitor on 27/04/2017.
 */

public class setupBanco extends SQLiteOpenHelper {
    public static final String BASENOME = "dbo_gds.db";
    public static final int VERSAO = 1;

    public setupBanco(Context c) {
        super(c, BASENOME, null, VERSAO);
    }

    public SQLiteDatabase getConexaoDB()
    {
       return this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
//        StringBuilder sbCreateTable = new StringBuilder();
//
//        sbCreateTable.append(	"\nCREATE TABLE IF NOT EXISTS dbo_gds_bandas ( login text primary key, senha text, nome text, descricao text, email text, telefone text, foto blob, site text ); "+
//                                "\nCREATE TABLE IF NOT EXISTS dbo_gds_eventos ( cdevento INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, dtevento datetime, qtdeingressos integer, loginadmin text ); "+
//                                "\nCREATE TABLE IF NOT EXISTS dbo_gds_apresentacoes ( cdevento integer, login text, FOREIGN KEY (cdevento) REFERENCES dbo_gds_eventos(cdevento)  FOREIGN KEY (login) REFERENCES dbo_gds_bandas(login) ); "+
//                                "\nCREATE TABLE IF NOT EXISTS dbo_gds_reservas ( cpf text, cdevento integer, nome text, dtevento datetime, FOREIGN KEY (cdevento) REFERENCES dbo_gds_eventos(cdevento) ); ");
        db.execSQL("CREATE TABLE IF NOT EXISTS dbo_gds_bandas ( login text primary key, senha text, nome text, descricao text, email text, telefone text, foto blob, site text, numacesso integer ) ");
        db.execSQL("CREATE TABLE IF NOT EXISTS dbo_gds_eventos ( cdevento INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, dtevento datetime, qtdeingressos integer, loginadmin text )");
        db.execSQL("CREATE TABLE IF NOT EXISTS dbo_gds_reservas ( idreserva integer primary key, cpf text, cdevento integer, nome text, dtevento text, FOREIGN KEY (cdevento) REFERENCES dbo_gds_eventos(cdevento) ); ");
        db.execSQL("CREATE TABLE IF NOT EXISTS dbo_gds_apresentacoes ( cdevento integer, login text, status text, FOREIGN KEY (cdevento) REFERENCES dbo_gds_eventos(cdevento), FOREIGN KEY (login) REFERENCES dbo_gds_bandas(login) ); ");
        db.execSQL("CREATE TABLE IF NOT EXISTS dbo_gds_tipo_feedback ( cd_tipo_feedback INT NOT NULL primary key, nm_tipo_feedback TEXT NOT NULL); ");
        db.execSQL("CREATE TABLE IF NOT EXISTS dbo_gds_feedback ( idfeedback integer primary key, dt_feedback text, ds_feedback text, reserva_cd_evento integer, reserva_cpf_cliente text, cd_tipo_feedback int, FOREIGN KEY (reserva_cd_evento) REFERENCES dbo_gds_reservas(cdevento), FOREIGN KEY (reserva_cd_evento) REFERENCES dbo_gds_reservas(cdevento), FOREIGN KEY (cd_tipo_feedback) REFERENCES dbo_gds_reservas(cdevento) ); ");

        db.execSQL("INSERT INTO dbo_gds_tipo_feedback VALUES (1, 'Feedback de evento')");
        db.execSQL("INSERT INTO dbo_gds_tipo_feedback VALUES (2, 'Feedback geral')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS dbo_gds_bandas");
        db.execSQL("DROP TABLE IF EXISTS dbo_gds_apresentacoes");
        db.execSQL("DROP TABLE IF EXISTS dbo_gds_eventos");
        db.execSQL("DROP TABLE IF EXISTS dbo_gds_reservas");
        onCreate(db);
    }

}
