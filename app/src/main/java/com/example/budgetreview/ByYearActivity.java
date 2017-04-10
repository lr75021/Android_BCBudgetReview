package com.example.budgetreview;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by hwang on 4/9/2017.
 */

public class ByYearActivity extends DemoBase implements AsyncResponse{

    AsyncTaskRunner runner = new AsyncTaskRunner();
    private ScrollView scrollView;
    private TableLayout tableLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_year);

        scrollView = new ScrollView(this);

        tableLayout = new TableLayout(this);

        runner.delegate = this;

        runner.execute();
    }

    @Override
    public void processFinish(String output) {
        JsonObject item;
        String strMinistry;

        System.out.println(output);

        JsonParser parser = new JsonParser();
        JsonElement rootNode = parser.parse(output);
        JsonArray details = rootNode.getAsJsonArray();

        ScrollView scrollView = new ScrollView(this);

        TableLayout tableLayout = new TableLayout(this);

        for (int i = 0; i < details.size(); i++) {

            TableRow tableRow = new TableRow(this);

            TextView tv = new TextView(this);

            tv.setPadding(10, 10, 10, 10);

            tv.setGravity(Gravity.CENTER);

            item = details.get(i).getAsJsonObject();
            strMinistry = item.get("Ministry") + " - " + item.get("Budgeted");

            tv.setText(strMinistry);

            tableRow.addView(tv);

            tableLayout.addView(tableRow);
            View v = new View(this);
            v.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, 1));
            v.setBackgroundColor(Color.rgb(51, 51, 51));
            tableLayout.addView(v);
        }

        scrollView.addView(tableLayout);

        setContentView(scrollView);
    }

}