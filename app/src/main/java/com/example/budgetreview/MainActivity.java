package com.example.budgetreview;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

// import com.github.mikephil.charting.utils.Utils;

// AppCompatActivity

public class MainActivity extends Activity implements AsyncResponse, OnItemClickListener {

    private ScrollView scrollView;
    private TableLayout tableLayout;
    AsyncTaskRunner runner = new AsyncTaskRunner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        runner.delegate = this;

        // initialize the utilities
        // Utils.init(this);

        ArrayList<ContentItem> objects = new ArrayList<ContentItem>();

        objects.add(new ContentItem("Home", "Project Description"));
        objects.add(new ContentItem("By Year", "Review budget by Year"));
        objects.add(new ContentItem("By Ministry", "Review budget by Ministry"));

        MyAdapter adapter = new MyAdapter(this, objects);

        ListView lv = (ListView) findViewById(R.id.listView1);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(this);


/*
        ScrollView scrollView = new ScrollView(this);

        TableLayout tableLayout = new TableLayout(this);
*/
    }

    @Override
    public void onItemClick(AdapterView<?> av, View v, int pos, long arg3) {
        Intent i;

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

        overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(getApplicationContext(), getString(R.string.menu_item1) + " Selected",Toast.LENGTH_LONG).show();
                return true;

            case R.id.item2:
                Toast.makeText(getApplicationContext(), getString(R.string.menu_item2) + " Selected",Toast.LENGTH_LONG).show();

                // String sleepTime = time.getText().toString();
                runner.execute();

                return true;

            case R.id.item3:
                Toast.makeText(getApplicationContext(), getString(R.string.menu_item3) + " Selected",Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
