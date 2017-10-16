package com.example.vitor.geracaodosomgds.RecursosAdmin;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vitor.geracaodosomgds.BordaItemConvite;
import com.example.vitor.geracaodosomgds.Modelos.FeedbackLista_Model;
import com.example.vitor.geracaodosomgds.Modelos.ReservaLista_Model;
import com.example.vitor.geracaodosomgds.R;
import com.example.vitor.geracaodosomgds.RecyclerTouchListener;
import com.example.vitor.geracaodosomgds.Repositorios.FeedbackRepo;
import com.example.vitor.geracaodosomgds.Repositorios.ReservaRepo;

import java.util.List;

import static com.example.vitor.geracaodosomgds.teladiaevento.isCPF;

public class telaadmin_listareserva extends AppCompatActivity {
    private List<ReservaLista_Model> reservas;
    private RecyclerView myView;
    private ReservaLista_Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telaadmin_listareserva);
        getSupportActionBar().setTitle("ADMINISTRAR RESERVAS");
        reservas = new ReservaRepo(getApplicationContext()).selectReservas_Lista();
        if (reservas.size() <= 0)
        {
            LinearLayout ll_dialogo = new LinearLayout(this);
            ll_dialogo.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(80, 80, 80, 80);
            final TextView txtNada = new TextView(this);
            txtNada.setText("Não há reservas para mostrar");
            txtNada.setTextSize(22f);
            ll_dialogo.addView(txtNada, lp);
            setContentView(ll_dialogo);
        }
        else
        {
            myView = (RecyclerView) findViewById(R.id.recviewAdmin_ListaReserva);
            myAdapter = new ReservaLista_Adapter(reservas);
            RecyclerView.LayoutManager myLayout = new LinearLayoutManager(getApplicationContext());
            myView.setLayoutManager(myLayout);
            myView.setItemAnimator(new DefaultItemAnimator());
            myView.addItemDecoration(new BordaItemConvite(this, LinearLayoutManager.VERTICAL));
            myView.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();

            myView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), myView, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    mostraReserva(position);
                }

                @Override
                public void onLongClick(View view, int position) {
                }

            }));
        }
    }

    public void mostraReserva(final Integer position)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("RESERVA");
        b.setMessage("Efetua as alterações necessárias e clique em OK para confirmar");
        LinearLayout ll_dialogo = new LinearLayout(this);
        ll_dialogo.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(80, 0, 80, 0);

        final EditText edtNome = new EditText(this);
        edtNome.setHint("Nome completo");
        edtNome.setText(reservas.get(position).getNome());
        edtNome.setTextSize(18f);
        ll_dialogo.addView(edtNome, lp);

        final EditText edtCPF = new EditText(this);
        edtCPF.setHint("CPF (apenas números)");
        edtCPF.setText(reservas.get(position).getCPF());
        edtCPF.setTextSize(18f);
        edtCPF.setInputType(InputType.TYPE_CLASS_NUMBER);
        ll_dialogo.addView(edtCPF, lp);

        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (edtCPF.equals("") || edtNome.equals("") )
                {
                    Toast.makeText(getApplicationContext(), "Preencha os campos!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (isCPF(edtCPF.getText().toString()) == false)
                        Toast.makeText(getApplicationContext(), "CPF INVÁLIDO", Toast.LENGTH_SHORT).show();
                    else
                    {
                        if (new ReservaRepo(getApplicationContext()).reservaExiste(edtCPF.getText().toString()))
                        {
                            new ReservaRepo(getApplicationContext()).updateReserva(reservas.get(position).getIdReserva(), edtNome.getText().toString().trim(), edtCPF.getText().toString().trim());
                            myAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });

        b.setNegativeButton("CANCELAR RESERVA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new ReservaRepo(getApplicationContext()).deletaReserva(reservas.get(position).getIdReserva());
                reservas.remove(position);
                myAdapter.notifyDataSetChanged();
            }
        });

        b.setNeutralButton("VOLTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        b.setView(ll_dialogo);
        final AlertDialog reserva = b.create();
        reserva.show();
    }
}
