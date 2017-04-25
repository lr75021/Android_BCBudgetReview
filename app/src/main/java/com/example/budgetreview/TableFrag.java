package com.example.budgetreview;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Created by hwang on 4/24/2017.
 */

public class TableFrag extends SimpleFragment {

    private TableLayout tl;
    private TableRow tr;

    public static Fragment newInstance(JsonArray list) {

        TableFrag tf = new TableFrag();
        tf.myList = list;
        return tf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_simple_table, container, false);

        tl = (TableLayout) v.findViewById(R.id.table1);

        addHeaders();
        addData();

        return v;

    }

    /** This function add the headers to the table **/
    public void addHeaders(){

        /** Create a TableRow dynamically **/
        tr = new TableRow(getActivity());
        tr.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        /** Creating a TextView to add to the row **/
        TextView colYear = new TextView(getActivity());
        colYear.setText("Years");
        colYear.setTextColor(Color.GRAY);
        //colYear.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        colYear.setPadding(5, 5, 5, 0);
        colYear.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(colYear);  // Adding textView to tablerow.

        /** Creating another textview **/
        TextView colBudgeted = new TextView(getActivity());
        colBudgeted.setText("Budgeted");
        colBudgeted.setTextColor(Color.GRAY);
        //colBudgeted.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        colBudgeted.setPadding(5, 5, 5, 0);
        colBudgeted.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(colBudgeted); // Adding textView to tablerow.

        /** Creating another textview **/
        TextView colActual = new TextView(getActivity());
        colActual.setText("Actual");
        colActual.setTextColor(Color.GRAY);
        //colBudgeted.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        colActual.setPadding(5, 5, 5, 0);
        colActual.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(colActual); // Adding textView to tablerow.

        // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.FILL_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        // we are adding two textviews for the divider because we have two columns
        tr = new TableRow(getActivity());
        tr.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.FILL_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        /** Creating another textview **/
        TextView divider = new TextView(getActivity());
        divider.setText("-----------------");
        divider.setTextColor(Color.GREEN);
        //divider.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));
        divider.setPadding(5, 0, 0, 0);
        divider.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(divider); // Adding textView to tablerow.

        TextView divider2 = new TextView(getActivity());
        divider2.setText("-------------------------");
        divider2.setTextColor(Color.GREEN);
       // divider2.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));
        divider2.setPadding(5, 0, 0, 0);
        divider2.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(divider2); // Adding textView to tablerow.

        TextView divider3 = new TextView(getActivity());
        divider3.setText("-------------------------");
        divider3.setTextColor(Color.GREEN);
        // divider2.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));
        divider3.setPadding(5, 0, 0, 0);
        divider3.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(divider3); // Adding textView to tablerow.

        // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.FILL_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
    }

    /** This function add the data to the table **/
    public void addData(){

        JsonObject item;
        TextView colYear, colBudgeted, colActual;

        for (int i = 0; i < myList.size(); i++) {
            item = myList.get(i).getAsJsonObject();

            /** Create a TableRow dynamically **/
            tr = new TableRow(getActivity());
            tr.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.FILL_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));

            /** Creating a TextView to add to the row **/
            colYear = new TextView(getActivity());
            colYear.setText(item.get("Year").toString());
            colYear.setTextColor(Color.RED);
            colYear.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            //colYear.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));
            colYear.setPadding(5, 5, 5, 5);
            tr.addView(colYear);  // Adding textView to tablerow.

            /** Creating another textview **/
            colBudgeted = new TextView(getActivity());
            colBudgeted.setText(item.get("Budgeted").toString());
            colBudgeted.setTextColor(Color.GREEN);
            //colBudgeted.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));
            colBudgeted.setPadding(5, 5, 5, 5);
            colBudgeted.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(colBudgeted); // Adding textView to tablerow.

            /** Creating another textview **/
            colActual = new TextView(getActivity());
            colActual.setText(item.get("Actual").toString());
            colActual.setTextColor(Color.GREEN);
            //colBudgeted.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));
            colActual.setPadding(5, 5, 5, 5);
            colActual.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(colActual); // Adding textView to tablerow.

            // Add the TableRow to the TableLayout
            tl.addView(tr, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.FILL_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }

}