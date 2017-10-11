package com.example.vitor.geracaodosomgds.RecursosAdmin;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.vitor.geracaodosomgds.BordaItemConvite;
import com.example.vitor.geracaodosomgds.ConviteAdapter;
import com.example.vitor.geracaodosomgds.Modelos.ApresentacaoModel;
import com.example.vitor.geracaodosomgds.Modelos.BandaModel;
import com.example.vitor.geracaodosomgds.Modelos.EventoModel;
import com.example.vitor.geracaodosomgds.R;
import com.example.vitor.geracaodosomgds.RecyclerTouchListener;
import com.example.vitor.geracaodosomgds.Repositorios.ApresentacaoRepo;
import com.example.vitor.geracaodosomgds.Repositorios.BandaRepo;
import com.example.vitor.geracaodosomgds.Repositorios.EventoRepo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class telaadmin_eventos extends AppCompatActivity {
    private List<EventoModel> eventos = new ArrayList<>();
    private RecyclerView myView;
    private EventoAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telaadmin_eventos);
        getSupportActionBar().setTitle("CONVIDAR BANDA");

        eventos = new EventoRepo(getApplicationContext()).selectAllEventos();
        myView = (RecyclerView) findViewById(R.id.recviewAdmin);
        myAdapter = new EventoAdapter(eventos);
        RecyclerView.LayoutManager myLayout = new LinearLayoutManager(getApplicationContext());
        myView.setLayoutManager(myLayout);
        myView.setItemAnimator(new DefaultItemAnimator());
        myView.addItemDecoration(new BordaItemConvite(this, LinearLayoutManager.VERTICAL    ));
        myView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

        myView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), myView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                convidaBanda(eventos.get(position).getCdEvento());
            }

            @Override
            public void onLongClick(View view, int position) {
            }

        }));
    }

    public void convidaBanda(final int cdEvento)
    {
        final AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("CONVIDAR BANDA");
        b.setMessage("Selecione abaixo a banda que deseja convidar para o evento escolhido");
        LinearLayout ll_dialogo = new LinearLayout(this);
        ll_dialogo.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(80, 0, 80, 0);

        final Spinner spBanda = new Spinner(this);
        //Selecionando as bandas da base para o adm poder escolher qual quer
        final List<BandaModel> bandas = new BandaRepo(this).listaBandas();
        if(!bandas.isEmpty())
        {
            List<String> s = new ArrayList<String>();
            for (int i = 0; i < bandas.size(); i++)
            {
                s.add(bandas.get(i).getNome());
            }

            ArrayAdapter<String> dadosAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, s);
            spBanda.setAdapter(dadosAdapter);
        }
        ll_dialogo.addView(spBanda, lp);

        b.setNeutralButton("VOLTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //faznada
            }
        });

        b.setPositiveButton("CONVIDAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String loginBanda = bandas.get(spBanda.getSelectedItemPosition()).getLogin();
                ApresentacaoModel a = new ApresentacaoModel();
                a.setLogin(loginBanda);
                a.setCd_evento(cdEvento);
                a.setStatus("AG"); // AG =aguardando a resposta (acabou de ser convidada)
                if (new ApresentacaoRepo(getApplicationContext()).listaApresentacaoPorLogin(a.getLogin(), a.getCd_evento()).size() == 0)
                {
                    long ik = new ApresentacaoRepo(getApplicationContext()).insertApresentacao(a);
                    if (ik != -1)
                        Toast.makeText(getApplicationContext(), "Convite enviado!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Convite já foi enviado para essa banda.", Toast.LENGTH_SHORT).show();
            }
        });

        b.setView(ll_dialogo);
        final AlertDialog adConvidaBanda = b.create();
        adConvidaBanda.show();

    }
}
