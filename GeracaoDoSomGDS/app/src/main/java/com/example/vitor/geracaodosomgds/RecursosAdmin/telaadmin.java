package com.example.vitor.geracaodosomgds.RecursosAdmin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.vitor.geracaodosomgds.Modelos.EventoModel;
import com.example.vitor.geracaodosomgds.Modelos.ProximoEventoModel;
import com.example.vitor.geracaodosomgds.Modelos.ReservaModel;
import com.example.vitor.geracaodosomgds.R;
import com.example.vitor.geracaodosomgds.Repositorios.ApresentacaoRepo;
import com.example.vitor.geracaodosomgds.Repositorios.EventoRepo;
import com.example.vitor.geracaodosomgds.Repositorios.FeedbackRepo;
import com.example.vitor.geracaodosomgds.Repositorios.ReservaRepo;
import com.github.clans.fab.FloatingActionButton;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.vitor.geracaodosomgds.teladiaevento.isCPF;

public class telaadmin extends AppCompatActivity {
//    private Button btnAddEvento, btnConvidarBanda, btnLimparDev, btnCancelarReserva, btnVisualizarFeedbacks;
    private TextView welcome, txtQtdReservas, txtQtdConvites, txtQtdFeedbacks, txtProxEvNomeData, txtProxEvQtdReserva;
    private FloatingActionButton fabConvBanda, fabAdmReservas, fabCriarEvento, fabVisFeed;
    private ProximoEventoModel pe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telaadmin);

        txtQtdReservas = (TextView) findViewById(R.id.txtQtdReservas);
        txtProxEvNomeData = (TextView) findViewById(R.id.txtProxEvNomeData);
        txtProxEvQtdReserva = (TextView) findViewById(R.id.txtProxEvQtdReserva);
        txtQtdFeedbacks = (TextView) findViewById(R.id.txtQtdFeedbacks);
        txtQtdConvites = (TextView) findViewById(R.id.txtQtdConvites);
        fabConvBanda = (FloatingActionButton) findViewById(R.id.fabConvBanda);
        fabAdmReservas = (FloatingActionButton) findViewById(R.id.fabAdmReservas);
        fabCriarEvento = (FloatingActionButton) findViewById(R.id.fabCriarEvento);
        fabVisFeed = (FloatingActionButton) findViewById(R.id.fabVisFeed);


        fabConvBanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itAdmConvites = new Intent(telaadmin.this, telaadmin_eventos.class);
                startActivity(itAdmConvites);
            }
        });

        fabAdmReservas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mostraReserva();
                Intent itAdmReservas = new Intent(telaadmin.this, telaadmin_listareserva.class);
                startActivity(itAdmReservas);
            }
        });

        fabCriarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreCadastroEvento();
            }
        });

        fabVisFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itAdmFeedbacks = new Intent(telaadmin.this, telaadmin_listafeedback.class);
                startActivity(itAdmFeedbacks);
            }
        });

        txtQtdReservas.setText(new ReservaRepo(getApplicationContext()).countReservaADM().toString());
        txtQtdFeedbacks.setText(new FeedbackRepo(getApplicationContext()).countFeedbacksADM().toString());
        txtQtdConvites.setText(new ApresentacaoRepo(getApplicationContext()).countConvitesAceitosADM().toString());

        pe = new ProximoEventoModel();
        pe = new ApresentacaoRepo(getApplicationContext()).getProxApresentacao();
        if (pe.getCdevento() != null ) {
            if (!pe.getCdevento().isEmpty()) {
                txtProxEvNomeData.setText(pe.getNomeBanda() + " - " + pe.getDtEvento());
                txtProxEvQtdReserva.setText("reservas feitas: " + pe.getQtdReserva());
                txtProxEvQtdReserva.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //telareservasEv
                        Intent itProxEv = new Intent(telaadmin.this, telaadmin_reservas_proxevento.class);
                        itProxEv.putExtra("cdevento", pe.getCdevento());
                        startActivity(itProxEv);
                    }
                });
            } else {
                txtProxEvNomeData.setText("não há eventos");
                txtProxEvQtdReserva.setText("não há reservas");
            }
        }
        else {
            txtProxEvNomeData.setText("não há eventos");
            txtProxEvQtdReserva.setText("não há reservas");
        }

//        btnAddEvento = (Button) findViewById(R.id.btnAddEvento);
//        btnConvidarBanda = (Button) findViewById(R.id.btnConvidarBanda);
//        btnVisualizarFeedbacks = (Button) findViewById(R.id.btnVisualizarFeedbacks);
//        btnCancelarReserva = (Button) findViewById(R.id.btnCancelarReserva);
//        btnLimparDev = (Button) findViewById(R.id.btnLimpar);

//        btnLimparDev.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (new EventoRepo(getApplicationContext()).selectAllEventos().size() != 0)
//                    new EventoRepo(getApplicationContext()).deletaTudo();
//                if (new ApresentacaoRepo(getApplicationContext()).selectAllApresentacoes().size() != 0)
//                    new ApresentacaoRepo(getApplicationContext()).deletaTudo();
//            }
//        });


//
//        btnAddEvento.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                abreCadastroEvento();
//            }
//        });
//
//        btnConvidarBanda.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent itAdmConvites = new Intent(telaadmin.this, telaadmin_eventos.class);
//                startActivity(itAdmConvites);
//            }
//        });
//
//        btnVisualizarFeedbacks.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent itAdmFeedbacks = new Intent(telaadmin.this, telaadmin_listafeedback.class);
//                startActivity(itAdmFeedbacks);
//            }
//        });
//
//        btnCancelarReserva.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                mostraReserva();
//                Intent itAdmReservas = new Intent(telaadmin.this, telaadmin_listareserva.class);
//                startActivity(itAdmReservas);
//            }
//        });


//        welcome = (TextView) findViewById(R.id.txtBoasVindas);
//        welcome.setText("Bem vindo "+getIntent().getExtras().get("nome")+"!\n           O que deseja fazer?");
    }

    public void abreCadastroEvento()
    {
        final AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("ADICIONAR EVENTO");
        LinearLayout ll_dialogo = new LinearLayout(this);
        ll_dialogo.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(80, 0, 80, 0);

        final EditText edtIngressos = new EditText(this);
        edtIngressos.setHint("Quantidade máxima de ingressos");
        edtIngressos.setSingleLine();
        edtIngressos.setTextSize(18f);
        ll_dialogo.addView(edtIngressos, lp);

        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        final EditText edtData = new EditText(this);
        edtData.setHint("Clique para escolher a data do evento");
        edtData.setTextSize(18f);
        edtData.setFocusable(false); //com essas 2 linhas eu desabilito que digitem texto mas deixo clicar
        edtData.setClickable(true);
        edtData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                DatePickerDialog dtEvento = new DatePickerDialog(b.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar dataEscolhida = Calendar.getInstance();
                        dataEscolhida.set(year, month, dayOfMonth);
                        edtData.setText(dateFormatter.format(dataEscolhida.getTime()));
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dtEvento.setTitle("Selecione o dia");
                dtEvento.setMessage("");
                dtEvento.show();
            }
        });
        ll_dialogo.addView(edtData, lp);

        final EditText edtHora = new EditText(this);
        edtHora.setHint("Clique para escolher a hora do evento");
        edtHora.setTextSize(18f);
        edtHora.setFocusable(false); //com essas 2 linhas eu desabilito que digitem texto mas deixo clicar
        edtHora.setClickable(true);
        edtHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar t = Calendar.getInstance();
                int hora = t.get(Calendar.HOUR_OF_DAY);
                int minuto = t.get(Calendar.MINUTE);
                TimePickerDialog horaEvento = new TimePickerDialog(b.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        edtHora.setText( (hourOfDay < 10 ? "0"+hourOfDay : hourOfDay ) +":"+(minute < 10 ? "0"+minute : minute ));
                    }
                }, hora, minuto, true); // true define se é 24hr ou nao
                horaEvento.show();
                edtHora.setText("Selecione a hora");
            }
        });
        ll_dialogo.addView(edtHora, lp);

        b.setNeutralButton("VOLTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //faznada
            }
        });
        b.setPositiveButton("CRIAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String qtIng = edtIngressos.getText().toString().trim();
                String dt = edtData.getText().toString().trim();
                String hr = edtHora.getText().toString().trim();
                criaEvento(qtIng, dt, hr);
            }
        });

        b.setView(ll_dialogo);
        AlertDialog alertaCriarEvento = b.create();
        alertaCriarEvento.show();
    }

    public void criaEvento(String qtIng, String dt, String hr)
    {
        EventoModel em = new EventoModel();
        em.setQtdeIngressos(qtIng);
        em.setLoginAdmin(getIntent().getExtras().get("nome").toString());
        em.setDtEvento(dt+" "+hr);
        if (new EventoRepo(this).insertEvento(em) != -1)
            Toast.makeText(getApplicationContext(), "Evento criado!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "Erro, culpa do Vitor!", Toast.LENGTH_SHORT).show();

    }


}

