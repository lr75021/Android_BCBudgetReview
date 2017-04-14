package com.example.budgetreview;

import android.os.Bundle;
import android.widget.ListView;

import com.github.mikephil.charting.data.PieEntry;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

/**
 * Created by hwang on 4/9/2017.
 */

public class ByMinistryActivity extends DemoBase implements AsyncResponse {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_ministry);

        setContentView(R.layout.activity_main);

        setTitle("Budget Review - Ministry List");

        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.delegate = this;
//         runner.reqType = AsyncTaskRunner.REQ_TYPE.reqBudgetByYear;
        runner.reqType = AsyncTaskRunner.REQ_TYPE.reqMinistryList;
        runner.strMinistry = "Health";
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
        JsonArray details = rootNode.getAsJsonArray();

        // ScrollView scrollView = new ScrollView(this);
        // TableLayout tableLayout = new TableLayout(this);

        ArrayList<ContentItem> objects = new ArrayList<ContentItem>();

        for (int i = 0; i < details.size(); i++) {
            item = details.get(i).getAsJsonObject();
            // int value = Integer.parseInt(item.get("Budgeted").toString());
            String label = item.get("name").toString();
            objects.add(new ContentItem(label, ""));

        }

        MyAdapter adapter = new MyAdapter(this, objects);

        ListView lv = (ListView) findViewById(R.id.listView1);
        lv.setAdapter(adapter);

        // lv.setOnItemClickListener(this);
    }
}