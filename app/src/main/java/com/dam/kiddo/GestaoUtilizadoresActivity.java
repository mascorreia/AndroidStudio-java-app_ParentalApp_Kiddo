package com.dam.kiddo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class GestaoUtilizadoresActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestao_utilizadores);

    }

    public void addUser(View view){

        Intent intent2 = new Intent(getApplicationContext(), DefinitionsActivity.class);//REMOVER DEPOIS DE IMPLEMENTAR LAUNCHER
        startActivity(intent2);//REMOVER DEPOIS DE IMPLEMENTAR LAUNCHER

    }
    
    @Override
    public void onBackPressed(){}
}

