package com.dam.kiddo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;


public class GestaoAplicacoesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestao_aplicacoes);

    }

    public void voltarDefinicoes(View view){

        Intent intent2 = new Intent(getApplicationContext(), DefinitionsActivity.class);
        startActivity(intent2);

    }

    @Override
    public void onBackPressed(){}
}