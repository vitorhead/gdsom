package com.example.vitor.geracaodosomgds.RecursosAdmin;

import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vitor.geracaodosomgds.BordaItemConvite;
import com.example.vitor.geracaodosomgds.Modelos.ReservaLista_Model;
import com.example.vitor.geracaodosomgds.R;
import com.example.vitor.geracaodosomgds.RecyclerTouchListener;
import com.example.vitor.geracaodosomgds.Repositorios.ReservaRepo;

import java.util.List;

public class telaadmin_reservas_proxevento extends AppCompatActivity {
    private List<ReservaLista_Model> reservas;
    private RecyclerView myView;
    private ReservaLista_ProxEvento_Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telaadmin_reservas_proxevento);
        getSupportActionBar().setTitle("GERAÇÃO DO SOM");
        String cdevento = "";
        cdevento = getIntent().getExtras().get("cdevento").toString();
        if (cdevento.isEmpty())
            finish();
        reservas = new ReservaRepo(getApplicationContext()).reservasProxEvento(cdevento);
        if (reservas.size() <= 0) {
            LinearLayout ll_dialogo = new LinearLayout(this);
            ll_dialogo.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(80, 80, 80, 80);
            final TextView txtNada = new TextView(this);
            txtNada.setText("Não há reservas para mostrar");
            txtNada.setTextSize(22f);
            ll_dialogo.addView(txtNada, lp);
            setContentView(ll_dialogo);
        } else {
            myView = (RecyclerView) findViewById(R.id.recviewAdmin_ListaReservaProxEvento);
            myAdapter = new ReservaLista_ProxEvento_Adapter(reservas);
            RecyclerView.LayoutManager myLayout = new LinearLayoutManager(getApplicationContext());
            myView.setLayoutManager(myLayout);
            myView.setItemAnimator(new DefaultItemAnimator());
            myView.addItemDecoration(new BordaItemConvite(this, LinearLayoutManager.VERTICAL));
            myView.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();

            myView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), myView, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    new ReservaRepo(getApplicationContext()).updateReservaCompareceu(reservas.get(position).getIdReserva(), true);
                }

                @Override
                public void onLongClick(View view, int position) {
                }

            }));
        }
    }
}
