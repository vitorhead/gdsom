package com.example.vitor.geracaodosomgds;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vitor.geracaodosomgds.Modelos.EventoModel;

import java.util.List;

import static com.example.vitor.geracaodosomgds.BitmapToByteArray.getImage;

/**
 * Created by Vitor on 01/10/2017.
 */

public class FeedbackEventoAdapter extends RecyclerView.Adapter<FeedbackEventoAdapter.MyViewHolder>
{
    private List<FeedbackEvento> eventosParaDarFeedback;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView nomeBanda, dtEvento;
        public ImageView fotoBanda;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            nomeBanda = (TextView) itemView.findViewById(R.id.lblNomeBanda_FeedbackEvento);
            dtEvento = (TextView) itemView.findViewById(R.id.lblDataEvento_FeedbackEvento);
            fotoBanda = (ImageView) itemView.findViewById(R.id.imgBanda_FeedBackEvento);
        }
    }

    public FeedbackEventoAdapter(List<FeedbackEvento> eventosParaDarFeedback)
    {
        this.eventosParaDarFeedback = eventosParaDarFeedback;
    }
    @Override
    public FeedbackEventoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedbackevento_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeedbackEventoAdapter.MyViewHolder holder, int position)
    {
        FeedbackEvento feedback = eventosParaDarFeedback.get(position);
        holder.nomeBanda.setText(feedback.getNomeBanda());
        holder.dtEvento.setText(feedback.getDataEvento()); // dd/mm/yyyy = 10 caracteres
        holder.fotoBanda.setImageBitmap(getImage(feedback.getFoto()));
    }

    @Override
    public int getItemCount()
    {
        return eventosParaDarFeedback.size();
    }
}

