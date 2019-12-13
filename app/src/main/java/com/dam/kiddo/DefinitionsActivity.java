package com.dam.kiddo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DefinitionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.definitions_activity);
    }

    public void activityGestaoPerfil(View view){
        Intent intent1 = new Intent(getApplicationContext(),GestaoPerfil_Activity.class);
        startActivity(intent1);
    }


    public void activityGestaoAplicacao(View view){

        Intent intent1 = new Intent(getApplicationContext(), GestaoAplicacoesActivity.class);
        startActivity(intent1);

    }

    public void activityGestaoTempo(View view){

        Intent intent1 = new Intent(getApplicationContext(), GestaoTempoActivity.class);
        startActivity(intent1);

    }

    public void activityLocalMapas(View view){

        Intent intent1 = new Intent(getApplicationContext(), LocalMapasActivity.class);
        startActivity(intent1);

    }

    public void activitySeguranca(View view){

        Intent intent1 = new Intent(getApplicationContext(), SecurityActivity.class);
        startActivity(intent1);

    }

    public void activityAcerca(View view){

        Intent intent1 = new Intent(getApplicationContext(), AcercaActivity.class);
        startActivity(intent1);

    }

    public void voltarMainActivity(View view){

        Intent intent1 = new Intent(getApplicationContext(), GestaoUtilizadoresActivity.class);
        startActivity(intent1);
    }

    public void closeApplication(View view) {
        finish();
        moveTaskToBack(true);
    }

    @Override
    public void onBackPressed(){}


}
