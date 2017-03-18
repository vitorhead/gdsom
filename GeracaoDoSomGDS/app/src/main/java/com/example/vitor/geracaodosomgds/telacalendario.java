package com.example.vitor.geracaodosomgds;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class telacalendario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // mostrando o botao voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
