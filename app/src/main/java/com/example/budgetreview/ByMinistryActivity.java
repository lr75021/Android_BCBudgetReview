package com.example.budgetreview;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mikephil.charting.data.PieEntry;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

/**
 * Created by hwang on 4/9/2017.
 */

public class ByMinistryActivity extends DemoBase implements AsyncResponse, OnItemClickListener {
    
    JsonArray listMinistry;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_ministry);

        // setContentView(R.layout.activity_main);

        setTitle("Budget Review - Ministry List");

        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.delegate = this;
//         runner.reqType = AsyncTaskRunner.REQ_TYPE.reqBudgetByYear;
        runner.reqType = AsyncTaskRunner.REQ_TYPE.reqMinistryList;
        runner.execute();   // Get data from URL.
    }

    @Override
    public void processFinish(String output) {
        JsonObject item;
        String strMinistry;

        System.out.println(output);

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        JsonParser parser = new JsonParser();
        JsonElement rootNode = parser.parse(output);
        listMinistry = rootNode.getAsJsonArray();

        // ScrollView scrollView = new ScrollView(this);
        // TableLayout tableLayout = new TableLayout(this);

        ArrayList<ContentItem> objects = new ArrayList<ContentItem>();

        for (int i = 0; i < listMinistry.size(); i++) {
            item = listMinistry.get(i).getAsJsonObject();
            // int value = Integer.parseInt(item.get("Budgeted").toString());
            String label = item.get("name").toString();
            objects.add(new ContentItem(label, ""));

        }

        MyAdapter adapter = new MyAdapter(this, objects);

        ListView lv = (ListView) findViewById(R.id.listView1);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> av, View v, int pos, long arg3) {
        Intent i;
        JsonObject item;
        item = listMinistry.get(pos).getAsJsonObject();
        // int value = Integer.parseInt(item.get("Budgeted").toString());
        String strMinistry = item.get("name").toString().replaceAll("\"", "");
        Toast.makeText(getApplicationContext(), strMinistry + " Selected",Toast.LENGTH_LONG).show();

        i = new Intent(this, MinistryBudgetActivity.class);
        i.putExtra("Ministry", strMinistry);
        startActivity(i);

/*
        switch (pos) {
            case 0:
                i = new Intent(this, HomeActivity.class);
                startActivity(i);
                break;
            case 1:
                i = new Intent(this, ByYearActivity.class);
                startActivity(i);
                break;
            case 2:
                i = new Intent(this, ByMinistryActivity.class);
                startActivity(i);
                break;
        }
*/
        overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
    }
}