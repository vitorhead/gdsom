package com.example.vitor.geracaodosomgds;

import android.content.DialogInterface;
import android.content.Intent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.vitor.geracaodosomgds.Modelos.EventoModel;
import com.example.vitor.geracaodosomgds.Modelos.FeedbackModel;
import com.example.vitor.geracaodosomgds.Repositorios.ApresentacaoRepo;
import com.example.vitor.geracaodosomgds.Repositorios.EventoRepo;
import com.example.vitor.geracaodosomgds.Repositorios.FeedbackRepo;

public class telacalendario extends AppCompatActivity {
    private CalendarView calendarioGDS;
    private Integer iIniciaEvento;
    private Toast mensagem;
    private DatePicker dtGDS;
    private Button btnFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iIniciaEvento = -1;

        calendarioGDS = (CalendarView) findViewById(R.id.calendarioGDS);
        montaCalendario();
        calendarioGDS.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                abreEvento(dayOfMonth);
            }
        });


        // Calendario deprecated 15/09/2017 -> tava ruim d+ isso
        dtGDS = (DatePicker) findViewById(R.id.dtGDS);
        montaDatePicker(); //o listener fica nesse método

        btnFeedback = (Button) findViewById(R.id.btnFeedback);
        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                montaFeedback();
            }
        });

    }

    public String formataData(String dataEvento)
    {
        SimpleDateFormat dtFormat = new SimpleDateFormat(dataEvento);
        Date dtEvento1 = new Date();
        return dtFormat.format(dtEvento1);
    }

    public void montaCalendario() {
        Calendar dtMax = Calendar.getInstance();
        dtMax.set(2017, 5, 30);
        Calendar dtMin = Calendar.getInstance();
        dtMin.set(2017, 5, 1);
        calendarioGDS.setMaxDate(dtMax.getTimeInMillis());
        calendarioGDS.setMinDate(dtMin.getTimeInMillis());
    }

    public void abreEvento(int diaClicado) {
        List<EventoModel> eventosCalendario = new EventoRepo(this).selectAllEventos();
        if (mensagem != null)
            mensagem.cancel();
//        if(eventosCalendario.size() == 0) {
//            EventoModel em1 = new EventoModel();
//            em1.setLoginAdmin("vitstipa");
//            em1.setQtdeIngressos("10");
//            em1.setDtEvento(formataData("28/06/2017 21:00:00"));
//
//            EventoModel em2 = new EventoModel();
//            em2.setLoginAdmin("vitstipa");
//            em2.setQtdeIngressos("10");
//            em2.setDtEvento(formataData("04/06/2017 21:00:00"));
//
//            EventoModel em3 = new EventoModel();
//            em3.setLoginAdmin("vitstipa");
//            em3.setQtdeIngressos("10");
//            em3.setDtEvento(formataData("16/06/2017 21:00:00"));
//            new EventoRepo(this).insertEvento(em1);
//            new EventoRepo(this).insertEvento(em2);
//            new EventoRepo(this).insertEvento(em3);
//        }
        for (int i = 0; i < eventosCalendario.size(); i++)
        {
            if (diaClicado == eventosCalendario.get(i).getDia()) {
                iIniciaEvento = i;
                break;
            }
            else
                iIniciaEvento = -1;
        }

        if(iIniciaEvento != -1)
        {
            if (new ApresentacaoRepo(getApplicationContext()).retornaApresentacao(eventosCalendario.get(iIniciaEvento).getCdEvento().toString()).size()
                    > 0)
            {
                Intent intent = new Intent(telacalendario.this, teladiaevento.class);
                intent.putExtra("diaClicado", diaClicado);
                intent.putExtra("cdevento", eventosCalendario.get(iIniciaEvento).getCdEvento());
                intent.putExtra("dtevento", eventosCalendario.get(iIniciaEvento).getDtEvento());
                intent.putExtra("qtdeingressos", eventosCalendario.get(iIniciaEvento).getQtdeIngressos());
                startActivity(intent);
            }
            else
            {
                mensagem= Toast.makeText(getApplicationContext(), "NÃO HÁ EVENTOS PARA ESSE DIA!", Toast.LENGTH_SHORT);
                mensagem.show();
            }
        }
        else
        {
            mensagem= Toast.makeText(getApplicationContext(), "NÃO HÁ EVENTOS PARA ESSE DIA!", Toast.LENGTH_SHORT);
            mensagem.show();
        }
    }

    public void montaDatePicker()
    {
        String sdtMax = "31/12/2017";
        String sdtMin = "01/12/2017";
        SimpleDateFormat sdfMax = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfMin = new SimpleDateFormat("dd/MM/yyyy");
        Date dtMax = null;
        Date dtMin = null;
        try{
            dtMax = sdfMax.parse(sdtMax);
            dtMin = sdfMin.parse(sdtMin);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        dtGDS.setMaxDate(dtMax.getTime());
        dtGDS.setMinDate(dtMin.getTime());

        dtGDS.init(2017, 12, 01, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                abreEvento(dayOfMonth);
            }
        });
    }

    public void montaFeedback()
    {
        final AlertDialog.Builder bFeedback = new AlertDialog.Builder(this);
        bFeedback.setTitle("FEEDBACK");
        bFeedback.setMessage("Obrigado por colaborar conosco!. \nDeseja nos enviar um feedback sobre um evento que compareceu ou sobre o aplicativo?");
        LinearLayout ll_dialogo = new LinearLayout(this);
        ll_dialogo.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(80, 0, 80, 0);

        bFeedback.setNeutralButton("SOBRE O APP", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Dismiss nesse e monta outro
                montaFeedBackApp();
            }
        });

        bFeedback.setPositiveButton("SOBRE UM EVENTO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Pede o cpf, da dismiss e chama outra tela
                montaFeedBackEvento();
            }
        });
        bFeedback.setView(ll_dialogo);
        final AlertDialog adFeedback = bFeedback.create();
        adFeedback.show();
    }

    public void montaFeedBackApp()
    {
        final AlertDialog.Builder bFBApp = new AlertDialog.Builder(this);
        bFBApp.setTitle("FEEDBACK");
        bFBApp.setMessage("Vamos lá! \nSinta-se livre para escrever abaixo o que achou sobre o app.");

        LinearLayout ll_dialogo = new LinearLayout(this);
        ll_dialogo.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(80, 0, 80, 0);

        final EditText edtFeedback = new EditText(this);
        edtFeedback.setSingleLine(false);
        edtFeedback.setLines(4);
        edtFeedback.setMaxLines(10);
        edtFeedback.setGravity(Gravity.LEFT | Gravity.TOP);
        edtFeedback.setHorizontalScrollBarEnabled(true);
        ll_dialogo.addView(edtFeedback, lp);

        bFBApp.setNeutralButton("VOLTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //nada
            }
        });

        bFBApp.setPositiveButton("ENVIAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (edtFeedback.getText().toString().trim().isEmpty()) {
                    mensagem = Toast.makeText(getApplicationContext(), "PREENCHA O CAMPO ANTES DE ENVIAR O FEEDBACK!", Toast.LENGTH_SHORT);
                    mensagem.show();
                }
                else {
                    FeedbackModel f = new FeedbackModel();
                    f.setCd_tipo_feedback(2);
                    f.setDs_feedback(edtFeedback.getText().toString().trim().toLowerCase());
                    String dtAgora = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                    f.setDt_feedback(dtAgora);
                    new FeedbackRepo(getApplicationContext()).insertFeedback(f);
                }
            }
        });

        bFBApp.setView(ll_dialogo);
        final AlertDialog adFBApp = bFBApp.create();
        adFBApp.show();
    }

    public void montaFeedBackEvento()
    {
        final AlertDialog.Builder bFBEvento = new AlertDialog.Builder(this);
        bFBEvento.setTitle("FEEDBACK");
        bFBEvento.setMessage("Digite seu CPF para podermos buscar os eventos em que compareceu");

        LinearLayout ll_dialogo = new LinearLayout(this);
        ll_dialogo.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(80, 0, 80, 0);

        final EditText edtCPF = new EditText(this);
        edtCPF.setHint("CPF (apenas números)");
        edtCPF.setTextSize(18f);
        edtCPF.setInputType(InputType.TYPE_CLASS_NUMBER);
        ll_dialogo.addView(edtCPF, lp);

        bFBEvento.setNeutralButton("VOLTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //nada
            }
        });

        bFBEvento.setPositiveButton("IR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (teladiaevento.isCPF(edtCPF.getText().toString().trim()) == false)
                    Toast.makeText(getApplicationContext(), "CPF INVÁLIDO", Toast.LENGTH_SHORT).show();
                else
                {
                    Intent intent = new Intent(telacalendario.this, telafeedback.class);
                    intent.putExtra("cpf", edtCPF.getText().toString().trim());
                    startActivity(intent);
                }
            }
        });

        bFBEvento.setView(ll_dialogo);
        final AlertDialog adFBApp = bFBEvento.create();
        adFBApp.show();

    }
}
