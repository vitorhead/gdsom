package com.example.vitor.geracaodosomgds;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vitor.geracaodosomgds.Modelos.EventoModel;

import java.util.Collections;
import java.util.List;


/**
 * Created by Vitor on 21/04/2017.
 */

public class ConviteAdapter extends RecyclerView.Adapter<ConviteAdapter.MyViewHolder>
{
    private List<EventoModel> convites;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView evento, dia, hora;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            evento = (TextView) itemView.findViewById(R.id.lblConviteAdapterTitulo);
            dia = (TextView) itemView.findViewById(R.id.lblConviteAdapterDia);
            hora = (TextView) itemView.findViewById(R.id.lblConviteAdapterHora);
        }
    }

    public ConviteAdapter(List<EventoModel> convites)
    {
        this.convites = convites;
    }
    @Override
    public ConviteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.convite_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ConviteAdapter.MyViewHolder holder, int position)
    {
        EventoModel convite = convites.get(position);
        holder.evento.setText("Evento "+convite.getCdEvento());
        holder.dia.setText(convite.getDtEvento().substring(0,10)); // dd/mm/yyyy = 10 caracteres
        holder.hora.setText(convite.getDtEvento().substring(11, convite.getDtEvento().length()));
    }

    @Override
    public int getItemCount()
    {
        return convites.size();
    }
}
