package com.dam.kiddo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class GestaoUtilizadoresActivity extends AppCompatActivity {

    ImageView i1,i2,i3,i4,i5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestao_utilizadores);
        i1 = (ImageView)findViewById(R.id.imageView2);
        i2 = (ImageView)findViewById(R.id.imageView2);
        i3 = (ImageView)findViewById(R.id.imageView2);
        i4 = (ImageView)findViewById(R.id.imageView2);
        i5 = (ImageView)findViewById(R.id.imageView2);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            int resId = bundle.getInt("resId");
            i1.setImageResource(resId);
        }

    }


    public void addUser(View view){
        Intent intent2 = new Intent(getApplicationContext(), AdicionarPerfilActivity.class);
        startActivity(intent2);
    }

    @Override
    public void onBackPressed(){}


    public void launchAct(View view){
        Intent intent = new Intent(getApplicationContext(), LauncherAppsActivity.class);
        startActivity(intent);

    }
}

