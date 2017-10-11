package com.example.vitor.geracaodosomgds;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class telaprincipal extends Activity {
    private Button btnCalendario, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Removendo a barra de atividade
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_telaprincipal);
        btnCalendario = (Button) findViewById(R.id.btnCalendario);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreCalendario();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreLogin();
            }
        });

    }

    public void abreCalendario() {
        Intent intent = new Intent(telaprincipal.this, telacalendario.class);
        startActivity(intent);
    }

    public void abreLogin()
    {
        Intent intent = new Intent(telaprincipal.this, loginBanda.class);
        startActivity(intent);
    }

}
