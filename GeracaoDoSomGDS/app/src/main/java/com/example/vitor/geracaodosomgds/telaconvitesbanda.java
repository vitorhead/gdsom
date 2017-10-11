package com.example.vitor.geracaodosomgds;


import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vitor.geracaodosomgds.Modelos.ApresentacaoModel;
import com.example.vitor.geracaodosomgds.Modelos.EventoModel;
import com.example.vitor.geracaodosomgds.RecursosAdmin.EventoAdapter;
import com.example.vitor.geracaodosomgds.Repositorios.ApresentacaoRepo;

import java.util.ArrayList;
import java.util.List;

public class telaconvitesbanda extends AppCompatActivity
{
    private List<EventoModel> convites = new ArrayList<>();
    private RecyclerView myView;
    private ConviteAdapter myAdapter;
    private String login;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telaconvitesbanda);
        getSupportActionBar().setTitle("CONVITES");
        login = getIntent().getExtras().get("login").toString();

        convites = new ApresentacaoRepo(this).listaConvitesPorLogin(login);
        if (convites.size() <= 0)
        {
            LinearLayout ll_dialogo = new LinearLayout(this);
            ll_dialogo.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(80, 80, 80, 80);
            final TextView txtNada = new TextView(this);
            txtNada.setText("Não há convites para mostrar");
            txtNada.setTextSize(22f);
            ll_dialogo.addView(txtNada, lp);
            setContentView(ll_dialogo);
        }
        else
        {
            myView = (RecyclerView) findViewById(R.id.recview);
            myAdapter = new ConviteAdapter(convites);
            RecyclerView.LayoutManager myLayout = new LinearLayoutManager(getApplicationContext());
            myView.setLayoutManager(myLayout);
            myView.setItemAnimator(new DefaultItemAnimator());
            myView.addItemDecoration(new BordaItemConvite(this, LinearLayoutManager.VERTICAL));
            myView.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();

            myView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                    myView, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    showConvite(convites.get(position));
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));
        }
    }

    private void showConvite(final EventoModel c)
    {
        final AlertDialog.Builder b = new AlertDialog.Builder(this);
        LinearLayout ll_dialogo = new LinearLayout(this);
        ll_dialogo.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(80, 0, 80, 0);

        final TextView txtC = new TextView(this);
        txtC.setText("Evento "+c.getCdEvento()+"\n"+c.getDtEvento());
        txtC.setTextSize(30f);
        ll_dialogo.addView(txtC, lp);

        b.setMessage(txtC.getText());

        b.setPositiveButton("ACEITAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                convites.remove(c);
                ApresentacaoModel a = new ApresentacaoModel();
                a.setLogin(login);
                a.setCd_evento(c.getCdEvento());
                a.setStatus("S");
                new ApresentacaoRepo(getApplicationContext()).responderConvite(a);
                myAdapter.notifyDataSetChanged();
            }
        });

        b.setNeutralButton("VOLTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //faz nada
            }
        });

        b.setNegativeButton("RECUSAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                convites.remove(c);
                ApresentacaoModel a = new ApresentacaoModel();
                a.setLogin(login);
                a.setCd_evento(c.getCdEvento());
                a.setStatus("N");
                new ApresentacaoRepo(getApplicationContext()).responderConvite(a);
                myAdapter.notifyDataSetChanged();
            }
        });

        final AlertDialog alertaconvite = b.create();
        alertaconvite.show();
    }

}
