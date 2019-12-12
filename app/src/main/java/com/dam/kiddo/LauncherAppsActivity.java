package com.dam.kiddo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;

public class LauncherAppsActivity extends AppCompatActivity /*implements AdapterView.OnItemClickListener*/ {

    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher_apps);

        //installedApps = new ArrayList<AppInfo>();
        RecyclerView recyclerView = findViewById(R.id.rvNumbers);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        AppsManager appManager = new AppsManager(this);
        ArrayList<AppInfo> installedApps = appManager.getApps();

        recyclerView.setAdapter(new InstalledAppsAdapter(getApplicationContext(), installedApps));
    }

    /*@Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PackageInfo packageInfo = (PackageInfo) parent.getItemAtPosition(position);
        AppInfo appInfo = (AppInfo) getApplicationContext();
        appInfo.setAppPackage(packageInfo);

        Intent intent = new Intent(getApplicationContext(), AppInfo.class);
        startActivity(intent);
    }*/


}
