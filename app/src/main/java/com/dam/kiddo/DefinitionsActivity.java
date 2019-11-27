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

    public void activitySeguranca(View view){

        Intent intent1 = new Intent(getApplicationContext(), SecurityActivity.class);
        startActivity(intent1);

    }
}
