package com.example.vitor.geracaodosomgds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;

import java.util.Calendar;

public class telacalendario extends AppCompatActivity {
    int diaClicado, diaProxEvento;
    CalendarView calendarioGDS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // mostrando o botao voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        calendarioGDS=(CalendarView) findViewById(R.id.cvCalendarioGDS);
        montaCalendario();
    }

    public void montaCalendario()
    {
        Calendar cgds = Calendar.getInstance();
        int diaMax = cgds.getActualMaximum(Calendar.DAY_OF_MONTH);
        int diaMin = cgds.getActualMinimum(Calendar.DAY_OF_MONTH);
        calendarioGDS.setMinDate(diaMin);
        calendarioGDS.setMaxDate(diaMax);
        calendarioGDS.setOnDateChangeListener(new OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                //Toast.makeText(getApplicationContext(), ""+dayOfMonth, Toast.LENGTH_SHORT).show(); //teste pra fazer mostrar o dia
                diaClicado = dayOfMonth;
                abreEvento();

            }
        });
    }

    public void abreEvento()
    {
        if (diaClicado == 28 || diaClicado == 04 || diaClicado == 16)
        {
            Intent intent = new Intent(telacalendario.this, teladiaevento.class);
            intent.putExtra("diaClicado", diaClicado);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "NÃO HÁ EVENTOS PARA ESSE DIA!", Toast.LENGTH_SHORT).show();
        }
    }


}
