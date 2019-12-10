package com.dam.kiddo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class GestaoUtilizadoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestao_utilizadores);
    }

    public void voltarDefinicoes(View view){

        Intent intent2 = new Intent(getApplicationContext(), DefinitionsActivity.class);//REMOVER DEPOIS DE IMPLEMENTAR LAUNCHER
        startActivity(intent2);//REMOVER DEPOIS DE IMPLEMENTAR LAUNCHER

    }
    
    @Override
    public void onBackPressed(){}
}

