package com.huhtamaki.marhuh.simplenotifications;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class AppsActivity extends AppCompatActivity {

    ArrayList<Application> allapps = new ArrayList<>();
    ArrayList<String> allapps_string = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent .addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> pkgAppsList = getPackageManager().queryIntentActivities(intent, 0);
        PackageManager pkManager = getPackageManager();
        for(ResolveInfo info : pkgAppsList){
            Application app = new Application();
            app.setName(info.activityInfo.applicationInfo.loadLabel(pkManager).toString());
            app.setIcon(info.activityInfo.applicationInfo.loadIcon(pkManager));
            allapps.add(app);
        }

        for(Application ap : allapps){
            allapps_string.add(ap.getName());
        }

        ListAdapter myAdapter = new CustomAppAdapter(this,allapps);
        ListView listView = (ListView) findViewById(R.id.apps_listview);
        listView.setAdapter(myAdapter);
    }

}
