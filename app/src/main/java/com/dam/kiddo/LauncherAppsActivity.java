package com.dam.kiddo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class LauncherAppsActivity extends AppCompatActivity {

    private List<AppList> installedApps;
    private AppAdapter installedAppAdapter;
    ListView userInstalledApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher_apps);

        userInstalledApps = findViewById(R.id.installed_app_list);
        installedApps = getInstalledApps(); //Log.d("myTag", "INFO: " + installedApps);
        installedAppAdapter = new AppAdapter(LauncherAppsActivity.this, installedApps);
        userInstalledApps.setAdapter(installedAppAdapter);
        userInstalledApps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                Intent intent = getPackageManager().getLaunchIntentForPackage(installedApps.get(i).packages);
                if(intent != null){ //Log.d("myTag", "INFO: " + intent);
                    startActivity(intent);
                }else {
                    Toast.makeText(LauncherAppsActivity.this, installedApps.get(i).packages + " Error, Please Try Again...", Toast.LENGTH_SHORT).show();
                }/*
                String[] colors = {" Open App", " App Info"};
                AlertDialog.Builder builder = new AlertDialog.Builder(LauncherAppsActivity.this);
                builder
                        //.setTitle("Choose Action")
                        .setItems(colors, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position of the selected item
                                if (which==0){
                                    Intent intent = getPackageManager().getLaunchIntentForPackage(installedApps.get(i).packages);
                                    if(intent != null){ Log.d("myTag", "INFO: " + intent);
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(LauncherAppsActivity.this, installedApps.get(i).packages + " Error, Please Try Again...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                if (which==1){
                                    Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS); Log.d("myTag", "INTENT INFO?: " + intent);
                                    intent.setData(Uri.parse("package:" + installedApps.get(i).packages));
                                    Toast.makeText(LauncherAppsActivity.this, installedApps.get(i).packages, Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }
                            }
                        });
                builder.show();
            */}
        });

        //Total Number of Installed-Apps(i.e. List Size)
        String  abc = userInstalledApps.getCount()+"";
        //TextView countApps = (TextView)findViewById(R.id.countApps);
        //countApps.setText("Total Installed Apps: "+abc);
        //Toast.makeText(this, abc+" Apps", Toast.LENGTH_SHORT).show();

    }

    public List<AppList> getInstalledApps() {
        PackageManager pm = getPackageManager();
        List<AppList> apps = new ArrayList<AppList>();
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
        //List<PackageInfo> packs = getPackageManager().getInstalledPackages(PackageManager.GET_PERMISSIONS);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((!isSystemPackage(p))) {
                String appName = p.applicationInfo.loadLabel(getPackageManager()).toString();
                Drawable icon = p.applicationInfo.loadIcon(getPackageManager());
                String packages = p.applicationInfo.packageName;
                apps.add(new AppList(appName, icon, packages));
            }
        }
        return apps;
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }

    public class AppAdapter extends BaseAdapter {

        public LayoutInflater layoutInflater;
        public List<AppList> listStorage;

        public AppAdapter(Context context, List<AppList> customizedListView) {
            layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listStorage = customizedListView;
        }

        @Override
        public int getCount() {
            return listStorage.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder listViewHolder;
            if(convertView == null){
                listViewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.installed_app_list, parent, false);

                listViewHolder.textInListView = (TextView)convertView.findViewById(R.id.list_app_name);
                listViewHolder.imageInListView = (ImageView)convertView.findViewById(R.id.app_icon);
                //listViewHolder.packageInListView=(TextView)convertView.findViewById(R.id.app_package);
                convertView.setTag(listViewHolder);
            }else{
                listViewHolder = (ViewHolder)convertView.getTag();
            }
            listViewHolder.textInListView.setText(listStorage.get(position).getName());
            listViewHolder.imageInListView.setImageDrawable(listStorage.get(position).getIcon());
            //listViewHolder.packageInListView.setText(listStorage.get(position).getPackages());

            return convertView;
        }

        class ViewHolder{
            TextView textInListView;
            ImageView imageInListView;
            CheckBox mAppSelect;
            RelativeLayout mItem;
            //TextView packageInListView;
        }
    }

    public void voltarDefinicoes(View view){

        Intent intent2 = new Intent(getApplicationContext(), PinActivity.class);
        startActivity(intent2);

    }

    public void irDefinicoes(View view){

        Intent intent1 = new Intent(getApplicationContext(), PinActivity2.class);
        startActivity(intent1);
    }

    public class AppList {
        private String name;
        Drawable icon;
        private String packages;
        private boolean isSelected;

        public AppList(String name, Drawable icon, String packages) {
            this.name = name;
            this.icon = icon;
            this.packages = packages;
        }
        public String getName() {
            return name;
        }
        public Drawable getIcon() {
            return icon;
        }
        public String getPackages() {
            return packages;
        }
        public boolean isSelected() {
            return isSelected;
        }
        public void setSelected(boolean selected) {
            isSelected = selected;
        }

    }
}
