package com.dam.kiddo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class GestaoAplicacoesActivity extends AppCompatActivity {

    private RelativeLayout mRelativeLayout;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<AppInfo> installedApps;
    private FloatingActionButton shareButton;
    private AppsManager appManager;
    private final String baseURL = "http://192.168.50.48/post.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestao_aplicacao);

        installedApps = new ArrayList<AppInfo>();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        //shareButton = (FloatingActionButton) findViewById(R.id.sharebutton);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        appManager = new AppsManager(this);
        installedApps = appManager.getApps();

        // Initialize a new adapter for RecyclerView
        mAdapter = new InstalledAppsAdapter(
                getApplicationContext(),
                installedApps
        );


        mRecyclerView.setAdapter(mAdapter);

    }

    public void voltarDefinicoes(View view){

        Intent intent2 = new Intent(getApplicationContext(), DefinitionsActivity.class);
        startActivity(intent2);

    }
}