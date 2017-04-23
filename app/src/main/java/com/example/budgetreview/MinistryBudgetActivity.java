package com.example.budgetreview;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
// import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v13.app.FragmentStatePagerAdapter;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ListView;

import com.github.mikephil.charting.data.PieEntry;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

/**
 * Created by hwang on 4/16/2017.
 */

// FragmentActivity  - not show title bar


public class MinistryBudgetActivity extends DemoBase implements AsyncResponse {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    //private static final int NUM_PAGES = 2;
    private String strMinistry;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;
    private JsonArray myList;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_ministry_budget);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);

        PageAdapter a = new PageAdapter(getSupportFragmentManager());
        pager.setAdapter(a);

        Intent i = getIntent();
        strMinistry = i.getExtras().getString("Ministry");
        System.out.println(strMinistry);

        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.delegate = this;
//         runner.reqType = AsyncTaskRunner.REQ_TYPE.reqBudgetByYear;
        runner.reqType = AsyncTaskRunner.REQ_TYPE.reqBudgetByMinistry;
        runner.strMinistry = strMinistry;
        runner.execute();   // Get data from URL.
/*

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When changing pages, reset the action bar actions since they are dependent
                // on which page is currently active. An alternative approach is to have each
                // fragment expose actions itself (rather than the activity exposing actions),
                // but for simplicity, the activity provides the actions in this sample.
                invalidateOptionsMenu();
            }
        });
        */
/*
        ArrayList<ContentItem> objects = new ArrayList<ContentItem>();

        objects.add(new ContentItem("T1", "asdfaswerl;asdf"));
        objects.add(new ContentItem("T2", "weiadfjwerjasldf"));
        objects.add(new ContentItem("T3", "owerjaslfdjwef"));

        MyAdapter adapter = new MyAdapter(this, objects);

        ListView lv = (ListView) findViewById(R.id.listView2);
        lv.setAdapter(adapter);
*/
    }

    @Override
    public void processFinish(String output) {
        JsonObject item;
        String strMinistry;

        System.out.println(output);

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        JsonParser parser = new JsonParser();
        JsonElement rootNode = parser.parse(output);
        myList = rootNode.getAsJsonArray();

        // ScrollView scrollView = new ScrollView(this);
        // TableLayout tableLayout = new TableLayout(this);

        ArrayList<ContentItem> objects = new ArrayList<ContentItem>();

        for (int i = 0; i < myList.size(); i++) {
            item = myList.get(i).getAsJsonObject();
            // int value = Integer.parseInt(item.get("Budgeted").toString());
            String year = item.get("Year").toString();
            String budgeted = item.get("Budgeted").toString();
            objects.add(new ContentItem(year, budgeted));
        }
/*
        MyAdapter adapter = new MyAdapter(this, objects);

        ListView lv = (ListView) findViewById(R.id.listView1);
        lv.setAdapter(adapter);

        // lv.setOnItemClickListener(this);
*/
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_ministry_budget, menu);

        menu.findItem(R.id.action_table).setEnabled(mPager.getCurrentItem() > 0);

        // Add either a "next" or "finish" button to the action bar, depending on which page
        // is currently selected.
        MenuItem item = menu.add(Menu.NONE, R.id.action_chart, Menu.NONE,
                (mPager.getCurrentItem() == mPagerAdapter.getCount() - 1)
                        ? R.string.action_table
                        : R.string.action_chart);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // case android.R.id.table:
                // Navigate "up" the demo structure to the launchpad activity.
                // See http://developer.android.com/design/patterns/navigation.html for more.
                NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            //   return true;


            case R.id.action_table:
                // Go to the previous step in the wizard. If there is no previous step,
                // setCurrentItem will do nothing.
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                return true;

            case R.id.action_chart:
                // Advance to the next step in the wizard. If there is no next step, setCurrentItem
                // will do nothing.
                mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
    /**
     * A simple pager adapter that represents 5 {@link ScreenSlidePageFragment} objects, in
     * sequence.

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return ScreenSlidePageFragment.create(position);
        }

        @Override
        public int getCount() {

            return NUM_PAGES;
        }
    }
     */
    private class PageAdapter extends FragmentPagerAdapter {

        public PageAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int pos) {
            android.support.v4.app.Fragment f = null;

            switch(pos) {
                case 0:
                    f = SineCosineFragment.newInstance();
                    break;
                case 1:
                    f = BarChartFrag.newInstance();
                    break;
                case 2:
                    f = PieChartFrag.newInstance();
                    break;
            }

            return f;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}
