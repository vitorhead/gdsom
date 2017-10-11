package com.example.vitor.geracaodosomgds.RecursosAdmin;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vitor.geracaodosomgds.Modelos.EventoModel;
import com.example.vitor.geracaodosomgds.Modelos.FeedbackLista_Model;
import com.example.vitor.geracaodosomgds.R;

import java.util.List;

/**
 * Created by Vitor on 07/10/2017.
 */

public class FeedbackLista_Adapter extends RecyclerView.Adapter<FeedbackLista_Adapter.MyViewHolder> {
    private List<FeedbackLista_Model> fbs;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tpfeedback, dsfeedback, datafeedback;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            tpfeedback = (TextView) itemView.findViewById(R.id.lblTipoFeedback);
            dsfeedback = (TextView) itemView.findViewById(R.id.lblDsFeedback_Lista);
            datafeedback = (TextView) itemView.findViewById(R.id.lblDataFeedback_FeedbackLista);
        }
    }

    public FeedbackLista_Adapter(List<FeedbackLista_Model> fbs)
    {
        this.fbs = fbs;
    }
    @Override
    public FeedbackLista_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedbacklista_adapter, parent, false);
        return new FeedbackLista_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeedbackLista_Adapter.MyViewHolder holder, int position) {
        FeedbackLista_Model fbmodel = fbs.get(position);
        holder.tpfeedback.setText(fbmodel.getTipofeedback());
        if (fbmodel.getDsfeedback().length() >= 14)
            holder.dsfeedback.setText(fbmodel.getDsfeedback().substring(0, 14)+"...");
        else
            holder.dsfeedback.setText(fbmodel.getDsfeedback());
        holder.datafeedback.setText("recebido em "+fbmodel.getDtfeedback().replace('-', '/'));
    }

    @Override
    public int getItemCount()
    {
        return fbs.size();
    }

}
