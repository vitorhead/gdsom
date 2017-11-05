package com.example.vitor.geracaodosomgds.RecursosAdmin;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.vitor.geracaodosomgds.Modelos.ReservaLista_Model;
import com.example.vitor.geracaodosomgds.R;

import java.util.List;

/**
 * Created by Vitor on 02/11/2017.
 */

public class ReservaLista_ProxEvento_Adapter extends RecyclerView.Adapter<ReservaLista_ProxEvento_Adapter.MyViewHolder>{
    private List<ReservaLista_Model> reservas;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView nome, cpf;
        public CheckBox chkReservaProxEvento;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            nome = (TextView) itemView.findViewById(R.id.lblNome_ReservaProxEv);
            cpf = (TextView) itemView.findViewById(R.id.lblCpf_ReservaProxEvento);
            chkReservaProxEvento = (CheckBox) itemView.findViewById(R.id.chkReservaProxEvento);
        }
    }

    public ReservaLista_ProxEvento_Adapter(List<ReservaLista_Model> reservas)
    {
        this.reservas = reservas;
    }
    @Override
    public ReservaLista_ProxEvento_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservalista_proxevento_adapter, parent, false);
        return new ReservaLista_ProxEvento_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ReservaLista_Model reserva = reservas.get(position);
        holder.nome.setText(reserva.getNome());
        holder.cpf.setText(reserva.getCPF());
        holder.chkReservaProxEvento.setChecked(reserva.getCheckBoxReserva());
    }

    @Override
    public int getItemCount()
    {
        return reservas.size();
    }

}
