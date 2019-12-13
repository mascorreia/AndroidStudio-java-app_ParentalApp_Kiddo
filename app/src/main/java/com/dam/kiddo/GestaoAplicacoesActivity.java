package com.dam.kiddo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GestaoAplicacoesActivity extends AppCompatActivity {

    private LauncherAppsActivity launcher;
    private List<LauncherAppsActivity.AppList> installedApps;
    private LauncherAppsActivity.AppAdapter installedAppAdapter;
    ListView userInstalledApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestao_aplicacoes);

        userInstalledApps = findViewById(R.id.installed_app_list); Log.d("myTag", "INFO: " + userInstalledApps);  //NULLLLLLLLLL
        installedApps = launcher.getInstalledApps(); Log.d("myTag", "INFO: " + installedApps);
    }

    public void voltarDefinicoes(View view){

        Intent intent2 = new Intent(getApplicationContext(), DefinitionsActivity.class);
        startActivity(intent2);

    }
}