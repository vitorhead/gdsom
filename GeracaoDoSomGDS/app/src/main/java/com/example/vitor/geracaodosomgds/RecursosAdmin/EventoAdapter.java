package com.example.vitor.geracaodosomgds.RecursosAdmin;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vitor.geracaodosomgds.Convite;
import com.example.vitor.geracaodosomgds.Modelos.EventoModel;
import com.example.vitor.geracaodosomgds.R;

import java.util.List;

/**
 * Created by Vitor on 28/05/2017.
 */

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.MyViewHolder>
{
    private List<EventoModel> eventos;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView evento, dia, qtdeIngressos;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            evento = (TextView) itemView.findViewById(R.id.lblEventoAdapterTitulo);
            dia = (TextView) itemView.findViewById(R.id.lblEventoAdapterData);
            qtdeIngressos = (TextView) itemView.findViewById(R.id.lblEventoAdapterIng);
        }
    }

    public EventoAdapter(List<EventoModel> eventos)
    {
        this.eventos = eventos;
    }
    @Override
    public EventoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.evento_adapter, parent, false);
        return new EventoAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EventoAdapter.MyViewHolder holder, int position)
    {
        EventoModel evento = eventos.get(position);
        holder.evento.setText("Evento "+(evento.getCdEvento()));
        holder.dia.setText(evento.getDtEvento());
        holder.qtdeIngressos.setText(evento.getQtdeIngressos());
    }

    @Override
    public int getItemCount()
    {
        return eventos.size();
    }
}
