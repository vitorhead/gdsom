package com.example.vitor.geracaodosomgds;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class telaReserva extends AppCompatActivity {
    EditText txt1, txt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_reserva);
        // mostrando o botao voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("RESERVA");
    }
    public void efetuarReserva()
    {
//        if(CamposVazios()==false)
//        {
//            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
//            alerta.setMessage("Verifique o preenchimento dos campos");
//            alerta.setCancelable(true);
//
//            alerta.setPositiveButton(
//                    "OK",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.cancel();
//                        }
//                    });
//            AlertDialog msgErroCampoVazio = alerta.create();
//            msgErroCampoVazio.show();
//        }

        if  (!camposVazios() )
        {
            Toast.makeText(getApplicationContext(), "CAMPO INVÁLIDO", Toast.LENGTH_SHORT).show();
        }
        else
        {
                //efetuar a reserva de fato
        }

    }

    public boolean camposVazios()
    {
        txt1 =  (EditText) findViewById(R.id.txtNomeReserva);
        txt2 =  (EditText) findViewById(R.id.txtCPFReserva);
        return((txt1.getTextSize()>0)||(txt2.getTextSize()>0));
    }
}
