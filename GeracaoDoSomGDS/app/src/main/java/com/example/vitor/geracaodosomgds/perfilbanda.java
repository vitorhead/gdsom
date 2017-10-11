package com.example.vitor.geracaodosomgds;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.vitor.geracaodosomgds.BitmapToByteArray.getImage;

public class perfilbanda extends AppCompatActivity
{
    private TextView txtNome, txtEmail, txtSite, txtTelefone, txtDescricao;
    private Button btnEditar;
    private dados_SessaoIniciada dsi;
    private ImageView fotoBanda;

//    private GestureDetectorCompat objGesto;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfilbanda);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MEU PERFIL");

        btnEditar = (Button) findViewById(R.id.btnEditarPerfil);

//        objGesto = new GestureDetectorCompat(this, new MyGestureListener());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Snackbar.make(v, "CONVITES", Snackbar.LENGTH_LONG)
                        .setAction("CONFERIR", abreConvites()).show();
            }
        });
        fab.bringToFront();

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreEdita();
            }
        });

        carregaDados();
    }

    public void abreEdita(){
        Intent intent = new Intent(perfilbanda.this, telacadastro.class);
        Boolean bEdita = true;
        intent.putExtra("bEdita", bEdita);
        intent.putExtra("login", txtEmail.getText().toString());
        startActivity(intent);
        finish();
    }

    public View.OnClickListener abreConvites()
    {
        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent itConvites = new Intent(perfilbanda.this, telaconvitesbanda.class);
                itConvites.putExtra("login", dsi.getLogin());
                startActivity(itConvites);
            }
        };
        return listener;
    }

    public void carregaDados()
    {
        txtNome = (TextView) findViewById(R.id.txtPerfilNomeBanda);
        txtDescricao = (TextView) findViewById(R.id.txtDescricaoBanda);
        txtSite = (TextView) findViewById(R.id.txtSiteBanda);
        txtTelefone = (TextView) findViewById(R.id.txtTelefoneBanda);
        txtEmail = (TextView) findViewById(R.id.txtEmailBanda);
        fotoBanda = (ImageView) findViewById(R.id.imgPerfilBanda);

        dsi = new dados_SessaoIniciada();
        txtEmail.setText(dsi.getLogin());
        txtNome.setText(dsi.getNomebanda());
        txtDescricao.setText(dsi.getDesc());
        txtSite.setText(dsi.getSite());
        txtTelefone.setText(dsi.getTelefone());
        fotoBanda.setImageBitmap(getImage(dsi.getFoto()));
    }

    // implementar esse metodo para passar ele como parametro no make([...]) /\
    public View.OnClickListener clickSair()
    {
        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish(); //sair dessa tela
            }
        };
        return listener;
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent evento)
//    {
//        this.objGesto.onTouchEvent(evento);
//        return super.onTouchEvent(evento);
//    }

//    //pra fazer o swipe left funcionar
//    class MyGestureListener extends GestureDetector.SimpleOnGestureListener
//    {
//
//        @Override
//        public boolean onFling(MotionEvent event1, MotionEvent event2,
//                               float velocityX, float velocityY)
//        {
//            if(event2.getX() < event1.getX())
//            {
//                Intent itConvites = new Intent(perfilbanda.this, telaconvitesbanda.class);
//                startActivity(itConvites);
//            }
//
//            return true;
//        }
//    }

}
