package com.example.budgetreview;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by hwang on 4/8/2017.
 */

public class AsyncTaskRunner extends AsyncTask<String, String, String> {

    private String strJsonByYear;
    private String resp;
    ProgressDialog progressDialog;
    public String strYear;

    public AsyncResponse delegate = null;

    @Override
    protected String doInBackground(String... params) {
        publishProgress("Sleeping..."); // Calls onProgressUpdate()
        try {
            // int time = Integer.parseInt(params[0])*1000;
            int time = 1;    // ignore the enter.

            Thread.sleep(time);
            // resp = "Slept for " + params[0] + " seconds";
            resp = "Slept for " + 1 + " seconds";

            // GatherDataFromURL();
            // String strYear = "2013";
            strJsonByYear = reqBudgetByYear(strYear);

        } catch (InterruptedException e) {
            e.printStackTrace();
            resp = e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            resp = e.getMessage();
        }
        return resp;
    }


    @Override
    protected void onPostExecute(String result) {
        // execution of result of Long time consuming operation
        // progressDialog.dismiss();
        // finalResult.setText(result);

        delegate.processFinish(strJsonByYear);
    }


    @Override
    protected void onPreExecute() {
        /*
        progressDialog = ProgressDialog.show(MainActivity.this,
                "ProgressDialog",
                "Wait for "+ 1 + " seconds");
        */
        // time.getText().toString()
    }


    @Override
    protected void onProgressUpdate(String... text) {
        // finalResult.setText(text[0]);

    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private static JsonObject readJsonObj(String strURL) {
        try {
            URL url = new URL(strURL);
            InputStream is = url.openStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            System.out.println(jsonText);

            JsonParser parser = new JsonParser();
            JsonElement rootNode = parser.parse(jsonText);
            JsonObject details = rootNode.getAsJsonObject();

//             JsonObject details = new JsonObject(jsonText);

            is.close();

            return details;
        }
        catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    private static JsonArray readJsonArray(String strURL) {
        try {
            URL url = new URL(strURL);
            InputStream is = url.openStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            System.out.println(jsonText);

            JsonParser parser = new JsonParser();
            JsonElement rootNode = parser.parse(jsonText);
            JsonArray details = rootNode.getAsJsonArray();

//             JsonArray details = new JsonArray(jsonText);

            is.close();

            return details;
        }
        catch (IOException e) {
            return null;
        }
    }

    private static String readJsonString(String strURL) {
        try {
            URL url = new URL(strURL);
            InputStream is = url.openStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            System.out.println(jsonText);

            is.close();

            return jsonText;
        }
        catch (IOException e) {
            return null;
        }
    }

    private String reqBudgetByYear(String strYear) throws IOException {
        String strURL;
        // JsonObject item;

        strURL = "http://52.25.205.73:8088/reqBudgetByYear?year=" + strYear;
        System.out.println("");
        System.out.println(">>> " + strURL);
        return readJsonString(strURL);
    }

    private void GatherDataFromURL() throws IOException {
        String strURL;
        JsonObject item;

        strURL = "http://52.25.205.73:8088/reqYearList";
        System.out.println(">>> " + strURL);
        JsonObject details = readJsonObj(strURL);

        JsonArray listYear = details.getAsJsonArray("YearList");
        System.out.println("size = " + listYear.size());

        for (int i = 0; i < listYear.size(); i++) {
            item = listYear.get(i).getAsJsonObject();
            System.out.println("item = " + item.get("id") + " Year = " + item.get("Year"));
        }

        strURL = "http://52.25.205.73:8088/reqMinistryList";
        System.out.println(">>> " + strURL);
        JsonArray listMinistry = readJsonArray(strURL);

        // JsonArray listMinistry = details.getAsJsonArray("");
        System.out.println("size = " + listMinistry.size());

        for (int i = 0; i < listMinistry.size(); i++) {
            item = listMinistry.get(i).getAsJsonObject();
            System.out.println("id = " + item.get("id") + " Name = " + item.get("name"));
        }

        strURL = "http://52.25.205.73:8088/reqBudgetByYear?year=2013";
        System.out.println("");
        System.out.println(">>> " + strURL);
        JsonArray listByYear = readJsonArray(strURL);

        // JsonArray listMinistry = details.getAsJsonArray("");
        System.out.println("size = " + listByYear.size());

        for (int i = 0; i < listByYear.size(); i++) {
            item = listByYear.get(i).getAsJsonObject();
            System.out.println("Ministry = " + item.get("Ministry") + " Budgeted = " + item.get("Budgeted"));
        }

        strURL = "http://52.25.205.73:8088/reqBudgetByMinistry?ministry=Health";
        System.out.println("");
        System.out.println(">>> " + strURL);
        JsonArray listByMinistry = readJsonArray(strURL);

        // JsonArray listMinistry = details.getAsJsonArray("");
        System.out.println("size = " + listByMinistry.size());

        for (int i = 0; i < listByMinistry.size(); i++) {
            item = listByMinistry.get(i).getAsJsonObject();
            System.out.println("Year = " + item.get("Year")
                    + " Budgeted = " + item.get("Budgeted")
                    + " Actual = " + item.get("Actual"));
        }

    }

}
