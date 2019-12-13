package com.dam.kiddo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AjudaActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajuda);

        TextView mTitleWindow = (TextView) findViewById(R.id.messageWindow);
        TextView mMessageWindow = (TextView) findViewById(R.id.titleWindow);

        mTitleWindow.setText("Kiddo Application\n");

        StringBuilder stringBuilder = new StringBuilder();
        String someMessage = "Para sair da aplicação tem de ir as definicoes e clicar no botao de sair\n Para mais alguma ajuda contacte a equipa desenvolvedora da aplicacao :D\n kiddodev@gmail.com";


        for (int i=0; i < 1; i++){
            stringBuilder.append(someMessage);
        }
        mMessageWindow.setText(stringBuilder.toString());
    }

    public void voltarDefinicoes(View view){

        Intent intent2 = new Intent(getApplicationContext(), LauncherAppsActivity.class);
        startActivity(intent2);

    }

    @Override
    public void onBackPressed(){}
}
