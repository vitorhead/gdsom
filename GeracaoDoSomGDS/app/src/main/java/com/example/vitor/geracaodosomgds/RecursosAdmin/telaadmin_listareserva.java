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
                    mostraReserva();
                }

                @Override
                public void onLongClick(View view, int position) {
                }

            }));
        }
    }

    public void mostraReserva()
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("RESERVA");
        b.setMessage("Informe o CPF que deseja cancelar a reserva");
        LinearLayout ll_dialogo = new LinearLayout(this);
        ll_dialogo.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(80, 0, 80, 0);

        final EditText edtCPF = new EditText(this);
        edtCPF.setHint("CPF (apenas números)");
        edtCPF.setTextSize(18f);
        edtCPF.setInputType(InputType.TYPE_CLASS_NUMBER);
        ll_dialogo.addView(edtCPF, lp);

        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (edtCPF.equals("") )
                {
                    Toast.makeText(getApplicationContext(), "INFORME O CPF!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (isCPF(edtCPF.getText().toString()) == false)
                        Toast.makeText(getApplicationContext(), "CPF INVÁLIDO", Toast.LENGTH_SHORT).show();
                    else
                    {
                        if (new ReservaRepo(getApplicationContext()).reservaExiste(edtCPF.getText().toString()))
                        {
                            //deletar
                            new ReservaRepo(getApplicationContext()).deletaReserva(edtCPF.getText().toString());
                        }
                    }
                }
            }
        });

        b.setNegativeButton("VOLTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        b.setView(ll_dialogo);
        final AlertDialog reserva = b.create();
        reserva.show();
    }
}
