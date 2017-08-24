package com.huhtamaki.marhuh.simplenotifications;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {

    ListView listView;
    List<Map<String, String>> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        listView = (ListView) findViewById(R.id.settingsList);

        listItems = new ArrayList<>();
        Map<String, String> item = new HashMap<>(2);
        item.put("Diagnostics","Test connection to a remote device.");
        listItems.add(item);

        SimpleAdapter adapter = new SimpleAdapter(this, listItems,
                android.R.layout.simple_list_item_2,
                new String[]{"Diagnostics", "Test connection to a remote device."},
                new int[]{android.R.id.text1, android.R.id.text2});

        listView.setAdapter(adapter);

        // R.layout.two_line_list_item
    }
}
