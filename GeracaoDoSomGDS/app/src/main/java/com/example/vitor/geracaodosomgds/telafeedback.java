package com.example.vitor.geracaodosomgds;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vitor.geracaodosomgds.Modelos.FeedbackModel;
import com.example.vitor.geracaodosomgds.Repositorios.FeedbackRepo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class telafeedback extends AppCompatActivity {

    private List<FeedbackEvento> fbs = new ArrayList<>();
    private RecyclerView myView;
    private FeedbackEventoAdapter myAdapter;
    private String CPF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telafeedback);
        getSupportActionBar().setTitle("FEEDBACK DE EVENTOS");
        CPF = getIntent().getExtras().get("cpf").toString();

        fbs = new FeedbackRepo(this).listaFeedbacksCPF(CPF);

        if (fbs.size() <= 0)
        {
            LinearLayout ll_dialogo = new LinearLayout(this);
            ll_dialogo.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(80, 80, 80, 80);
            final TextView txtNada = new TextView(this);
            txtNada.setText("Você ainda não foi em um show!");
            txtNada.setTextSize(22f);
            ll_dialogo.addView(txtNada, lp);
            setContentView(ll_dialogo);
        }
        else
        {
            myView = (RecyclerView) findViewById(R.id.recview_feedback);
            myAdapter = new FeedbackEventoAdapter(fbs);
            RecyclerView.LayoutManager myLayout = new LinearLayoutManager(getApplicationContext());
            myView.setLayoutManager(myLayout);
            myView.setItemAnimator(new DefaultItemAnimator());
            //usarei a mesma borda do convite mesmo
            myView.addItemDecoration(new BordaItemConvite(this, LinearLayoutManager.VERTICAL));
            myView.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();

            myView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                    myView, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    montaFeedbackEv(fbs.get(position));
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));
        }
    }

    public void montaFeedbackEv(final FeedbackEvento fClicado)
    {
        final AlertDialog.Builder bFBApp = new AlertDialog.Builder(this);
        bFBApp.setTitle("FEEDBACK");
        bFBApp.setMessage("Sinta-se livre para escrever abaixo o que achou da apresentação, banda ou local do evento!");

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
                FeedbackModel f = new FeedbackModel();
                f.setCd_tipo_feedback(1);
                f.setDs_feedback(edtFeedback.getText().toString().trim().toLowerCase());
                String dtAgora = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                f.setDt_feedback(dtAgora);
                f.setReserva_cd_evento(fClicado.getReserva_cd_evento());
                f.setReserva_cpf_cliente(CPF);
                new FeedbackRepo(getApplicationContext()).insertFeedback(f);
            }
        });

        bFBApp.setView(ll_dialogo);
        final AlertDialog adFBApp = bFBApp.create();
        adFBApp.show();
    }
}
