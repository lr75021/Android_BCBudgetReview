package com.example.budgetreview;

import android.os.AsyncTask;

/**
 * Created by hwang on 4/8/2017.
 */

public class AsyncTaskRunner extends AsyncTask<String, String, String> {

    public enum REQ_TYPE {
        reqYearList,
        reqMinistryList,
        reqBudgetByYear,
        reqBudgetByMinistry
    }

    private String resp;

    public String strYear;
    public String strMinistry;
    public REQ_TYPE reqType;

    private String strJsonResult;

    public AsyncResponse delegate = null;

    public JsonURLRequest jsonURLReq = new JsonURLRequest();

    @Override
    protected String doInBackground(String... params) {
        publishProgress("Sleeping..."); // Calls onProgressUpdate()
        try {
            Thread.sleep(1);
            resp = "Success";

            // GatherDataFromURL();
            // String strYear = "2013";
            switch (reqType) {
                case reqYearList:
                    strJsonResult = jsonURLReq.reqYearList();
                    break;
                case reqMinistryList:
                    strJsonResult = jsonURLReq.reqMinistryList();
                    break;
                case reqBudgetByYear:
                    strJsonResult = jsonURLReq.reqBudgetByYear(strYear);
                    break;
                case reqBudgetByMinistry:
                    strJsonResult = jsonURLReq.reqBudgetByMinistry(strMinistry);
                    break;
            }

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
        delegate.processFinish(strJsonResult);
    }


    @Override
    protected void onPreExecute() {
    }


    @Override
    protected void onProgressUpdate(String... text) {
    }


}
