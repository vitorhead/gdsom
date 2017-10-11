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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vitor.geracaodosomgds.BordaItemConvite;
import com.example.vitor.geracaodosomgds.Modelos.ApresentacaoModel;
import com.example.vitor.geracaodosomgds.Modelos.BandaModel;
import com.example.vitor.geracaodosomgds.Modelos.FeedbackLista_Model;
import com.example.vitor.geracaodosomgds.R;
import com.example.vitor.geracaodosomgds.RecyclerTouchListener;
import com.example.vitor.geracaodosomgds.Repositorios.ApresentacaoRepo;
import com.example.vitor.geracaodosomgds.Repositorios.BandaRepo;
import com.example.vitor.geracaodosomgds.Repositorios.FeedbackRepo;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class telaadmin_listafeedback extends AppCompatActivity {
    private List<FeedbackLista_Model> fbs;
    private RecyclerView myView;
    private FeedbackLista_Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telaadmin_listafeedback);

        getSupportActionBar().setTitle("LISTA DE FEEDBACKS");

        fbs = new FeedbackRepo(getApplicationContext()).listaFeedbacks_ADM();
        if (fbs.size() <= 0)
        {
            LinearLayout ll_dialogo = new LinearLayout(this);
            ll_dialogo.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(80, 80, 80, 80);
            final TextView txtNada = new TextView(this);
            txtNada.setText("Não há feedbacks para mostrar");
            txtNada.setTextSize(22f);
            ll_dialogo.addView(txtNada, lp);
            setContentView(ll_dialogo);
        }
        else
        {
            myView = (RecyclerView) findViewById(R.id.recviewAdmin_ListaFeedback);
            myAdapter = new FeedbackLista_Adapter(fbs);
            RecyclerView.LayoutManager myLayout = new LinearLayoutManager(getApplicationContext());
            myView.setLayoutManager(myLayout);
            myView.setItemAnimator(new DefaultItemAnimator());
            myView.addItemDecoration(new BordaItemConvite(this, LinearLayoutManager.VERTICAL));
            myView.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();

            myView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), myView, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    mostraFeedback(position);
                }

                @Override
                public void onLongClick(View view, int position) {
                }

            }));
        }
    }

    public void mostraFeedback(int i)
    {
        final AlertDialog.Builder b = new AlertDialog.Builder(this);
        LinearLayout ll_dialogo = new LinearLayout(this);
        ll_dialogo.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(80, 0, 80, 0);


        switch (Integer.valueOf(fbs.get(i).getTipofeedback()))
        {
            // de evento
            case 1:
                b.setTitle("FEEDBACK NÚMERO " + fbs.get(i).getIdFeedback());
                b.setMessage("\"" + fbs.get(i).getDsfeedback() + "\"");
                final TextView txtInfoEvento = new TextView(this);
                txtInfoEvento.setHint("código do evento "+fbs.get(i).getCdevento()+" - data "+fbs.get(i).getDtevento().substring(0, 10));
                txtInfoEvento.setTextSize(14f);
                ll_dialogo.addView(txtInfoEvento, lp);
                break;

            // sobre o app
            case 2:
                b.setTitle("FEEDBACK NÚMERO " + fbs.get(i).getIdFeedback());
                b.setMessage("\"" + fbs.get(i).getDsfeedback() + "\"");
                break;
        }

//        if (fbs.get(i).getTipofeedback().equals("1"))
//        {
//            // Feedback de evento
//            b.setTitle("FEEDBACK NÚMERO " + fbs.get(i).getIdFeedback());
//            b.setMessage("\"" + fbs.get(i).getDsfeedback() + "\"");
//            final TextView txtInfoEvento = new TextView(this);
//            txtInfoEvento.setHint("código do evento "+fbs.get(i).getCdevento()+" - data "+fbs.get(i).getDtevento().substring(0, 10));
//            txtInfoEvento.setTextSize(14f);
//            ll_dialogo.addView(txtInfoEvento, lp);
//        }
//        else
//        {
//            // Feedback geral sobre o app
//            b.setTitle("FEEDBACK NÚMERO " + fbs.get(i).getIdFeedback());
//            b.setMessage("\"" + fbs.get(i).getDsfeedback() + "\"");
//        }

        b.setNeutralButton("VOLTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //faznada
            }
        });

        b.setView(ll_dialogo);
        final AlertDialog adConvidaBanda = b.create();
        adConvidaBanda.show();
    }
}
