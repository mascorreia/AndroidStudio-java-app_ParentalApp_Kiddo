package com.dam.kiddo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AcercaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca);

        TextView mTitleWindow = (TextView) findViewById(R.id.messageWindow);
        TextView mMessageWindow = (TextView) findViewById(R.id.titleWindow);

        mTitleWindow.setText("Kiddo Application\n");

        StringBuilder stringBuilder = new StringBuilder();
        String someMessage = "Esta aplicação foi realizada no âmbito da unicade curricular de Computação Móvel.\n" +
                "Equipa de desenvolvimento:\n" +
                "João Madeira\n" +
                "Gonçalo Alexandre\n" +
                "Miguel Correia\n" +
                "Rafael Bandeira\n" +
                "Miguel Carromeu\n";


        for (int i=0; i < 1; i++){
            stringBuilder.append(someMessage);
        }
        mMessageWindow.setText(stringBuilder.toString());
    }

    public void voltarDefinicoes(View view){

        Intent intent2 = new Intent(getApplicationContext(), DefinitionsActivity.class);
        startActivity(intent2);

    }

    @Override
    public void onBackPressed(){}
}
