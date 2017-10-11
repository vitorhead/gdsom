package com.example.vitor.geracaodosomgds.RecursosAdmin;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vitor.geracaodosomgds.Modelos.FeedbackLista_Model;
import com.example.vitor.geracaodosomgds.Modelos.ReservaLista_Model;
import com.example.vitor.geracaodosomgds.R;

import java.util.List;

/**
 * Created by Vitor on 08/10/2017.
 */

public class ReservaLista_Adapter  extends RecyclerView.Adapter<ReservaLista_Adapter.MyViewHolder> {
    private List<ReservaLista_Model> reservas;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView nomeCpf, bandaData;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            nomeCpf = (TextView) itemView.findViewById(R.id.lblNomeCpf_ReservaLista);
            bandaData = (TextView) itemView.findViewById(R.id.lblNomeBandaDataAp_ListaReserva);
        }
    }

    public ReservaLista_Adapter(List<ReservaLista_Model> reservas)
    {
        this.reservas = reservas;
    }
    @Override
    public ReservaLista_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservalista_adapter, parent, false);
        return new ReservaLista_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReservaLista_Adapter.MyViewHolder holder, int position) {
        ReservaLista_Model reserva = reservas.get(position);
        holder.nomeCpf.setText(reserva.getNome()+" - "+reserva.getCPF());
        holder.bandaData.setText(reserva.getNomeBanda()+ " - "+reserva.getDtEvento().substring(0, 10));
    }

    @Override
    public int getItemCount()
    {
        return reservas.size();
    }

}
