package com.huhtamaki.marhuh.simplenotifications;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AppsActivity extends AppCompatActivity {

    ArrayList<Application> allapps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent .addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> pkgAppsList = getPackageManager().queryIntentActivities( intent, 0);
        PackageManager pkManager = getPackageManager();
        for(ResolveInfo info : pkgAppsList){
            Application app = new Application();
            app.setName((String) info.loadLabel(pkManager));
            app.setIcon(info.icon);
            allapps.add(app);
        }


        ListAdapter myAdapter = new CustomAppAdapter(this,allapps);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(myAdapter);
    }

}
