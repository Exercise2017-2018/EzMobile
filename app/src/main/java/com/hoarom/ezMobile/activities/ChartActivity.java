package com.hoarom.ezMobile.activities;

import android.graphics.RectF;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.hoarom.ezMobile.DemoBase;
import com.hoarom.ezMobile.R;
import com.hoarom.ezMobile.asyncTasks.JsonTask;
import com.hoarom.ezMobile.custorm.DayAxisValueFormatter;
import com.hoarom.ezMobile.custorm.MyAxisValueFormatter;
import com.hoarom.ezMobile.custorm.XYMarkerView;
import com.hoarom.ezMobile.fragments.ChartFragment;
import com.hoarom.ezMobile.model.Quote;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.hoarom.ezMobile.Manager.FORMAT_DATE;
import static com.hoarom.ezMobile.Manager.TIME_DELAY_AUTO_LOAD;
import static com.hoarom.ezMobile.Manager.convertStringToListQuoteChart;
import static com.hoarom.ezMobile.api.api.API_CHART;

public class ChartActivity extends DemoBase implements ChartFragment.OnFragmentInteractionListener, OnChartValueSelectedListener {

    PaperViewAdapter paperViewAdapter;
    Handler _handler = new Handler();

    Spinner spinner;//type of chart
    EditText code;// mã chứng khoán


    Toolbar toolbar;
    private BarChart mChart;
    TabLayout tabLayout;
    ViewPager pager;

    BarChart mChart1;

    private final int ONE_WEEK = 0;
    private final int ONE_MONTH = 1;
    private final int THREE_MONTH = 2;
    private final int SIX_MONTH = 3;
    private final int ONE_YEAR = 4;
    private final int TWO_YEAR = 5;
    private final int ALL = 6;

    int time = ONE_WEEK;
    //    private SmartTabLayout mTabLayout;
    List<List<Quote>> quoteList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        createView();

        Runnable timedTask = new Runnable() {
            @Override
            public void run() {

                (new JsonTask(new JsonTask.TaskListener() {
                    @Override
                    public void onSuccess(String data) {
                        Log.w("ChartActivity", "onSuccess: " + data);
                        quoteList = convertStringToListQuoteChart(data);
                        Log.w("ChartActivity", "onSuccess: " + quoteList.size());
                        updateUI(time);
                    }

                    @Override
                    public void onError() {
                        Log.w("ChartActivity", "onError: ");
                    }
                })).execute(API_CHART);
                _handler.postDelayed(this, TIME_DELAY_AUTO_LOAD * 100000);
            }
        };
        _handler.post(timedTask);

    }

    private void createView() {

        paperViewAdapter = new PaperViewAdapter(getSupportFragmentManager());

        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tab_layout);

        toolbar.setTitle(R.string.title_chart);
        pager = findViewById(R.id.view_pager);

        pager.setAdapter(paperViewAdapter);
        tabLayout.setupWithViewPager(pager);
        tabLayout.setTabsFromPagerAdapter(paperViewAdapter);

        tabLayout.addOnTabSelectedListener(onTabSelected);
        spinner = findViewById(R.id.spinner);
        code = findViewById(R.id.code);
        mChart = findViewById(R.id.barchart);
        mChart1 = findViewById(R.id.barchart1);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ChartActivity.this,
                R.array.type_chart, R.layout.item_spinner_layout);
        adapter.setDropDownViewResource(R.layout.item_spinner_layout);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mChart.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);


        custormChart1(this);
    }

    private void custormChart1(OnChartValueSelectedListener listener) {
        ///chart 1
        mChart1.setOnChartValueSelectedListener(listener);

        mChart1.setDrawBarShadow(false);
        mChart1.setDrawValueAboveBar(true);

        mChart1.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart1.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart1.setPinchZoom(false);

        mChart1.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mChart);

        XAxis xAxis = mChart1.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

//        IAxisValueFormatter custom = new MyAxisValueFormatter();
//
//        YAxis leftAxis = mChart1.getAxisLeft();
//        leftAxis.setTypeface(mTfLight);
//        leftAxis.setLabelCount(8, false);
//        leftAxis.setValueFormatter(custom);
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis.setSpaceTop(15f);
//        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//
//        YAxis rightAxis = mChart1.getAxisRight();
//        rightAxis.setDrawGridLines(false);
//        rightAxis.setTypeface(mTfLight);
//        rightAxis.setLabelCount(8, false);
//        rightAxis.setValueFormatter(custom);
//        rightAxis.setSpaceTop(15f);
//        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = mChart1.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });

        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        mv.setChartView(mChart1); // For bounds control
        mChart1.setMarker(mv); // Set the marker to the chart
    }

    private void setDataForChart1(List<Quote> listquote) {
        Log.w("ChartActivity", "setDataForChart1: ");
        float start = 1f;

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < listquote.size(); i++) {
            float mult = (listquote.size());
            float val = (float) (listquote.get(i).getMatchPrice() * 1);
            Log.w("ChartActivity", "setDataForChart1: " + listquote.get(i).getDate()
                    + " " + listquote.get(i).getMatchPrice());
            Date date = null;
            try {
                date = FORMAT_DATE.parse(listquote.get(i).getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            yVals1.add(new BarEntry(i, val));//x, y
        }
        BarDataSet set1;

        if (mChart1.getData() != null && mChart1.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart1.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart1.getData().notifyDataChanged();
            mChart1.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "");

            set1.setDrawIcons(false);
            set1.setColors(ColorTemplate.MATERIAL_COLORS);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(mTfLight);
            data.setBarWidth(0.9f);

            mChart1.setData(data);
        }
        mChart1.invalidate();
    }

    protected RectF mOnValueSelectedRectF = new RectF();


    TabLayout.OnTabSelectedListener onTabSelected = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int id = tab.getPosition();
            Log.w("ChartActivity", "onTabSelected: ");
            switch (id) {
                case 0:
                    //1W
                    time = ONE_WEEK;
                    break;
                case 1:
                    //1M
                    time = ONE_MONTH;
                    break;
                case 2:
                    //3M
                    time = THREE_MONTH;
                    break;
                case 3:
                    //6M
                    time = SIX_MONTH;
                    break;
                case 4:
                    //1Y
                    time = ONE_YEAR;
                    break;
                case 5:
                    //2Y
                    time = TWO_YEAR;
                    break;
                case 6:
                    //ALL
                    time = ALL;
                    break;
            }

            updateUI(time);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    private void showChart(List<Quote> listQuote) {

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        mChart.getAxisLeft().setDrawGridLines(false);

        //set time delay show chart=)) ignore
        //hiệu ứng biểu đồ xuất hiện
//        mChart.animateY(2500);

        mChart.getLegend().setEnabled(false);

        for (int i = 0; i < listQuote.size(); i++) {
            float mult = (listQuote.size());
            float val = (float) (listQuote.get(i).getMatchPrice() * 1);
            yVals1.add(new BarEntry(i, val));//x, y
        }
        BarDataSet set1;

        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "Data Set");
            set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
            set1.setDrawValues(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            mChart.setData(data);
            mChart.setFitBars(true);
        }

        mChart.invalidate();

        setDataForChart1(listQuote);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void updateUI(int time) {

        Calendar calendar = Calendar.getInstance();
        List<Quote> temp = new ArrayList<>();
        if (time == ONE_WEEK) {
            showChart(quoteList.get(ONE_WEEK));
        } else if (time == ONE_MONTH) {
            showChart(quoteList.get(ONE_MONTH));
        } else if (time == THREE_MONTH) {
            //3M
            showChart(quoteList.get(THREE_MONTH));
        } else if (time == SIX_MONTH) {
            //6M
            showChart(quoteList.get(SIX_MONTH));
        } else if (time == ONE_YEAR) {
            //1Y
            showChart(quoteList.get(ONE_YEAR));
        } else if (time == TWO_YEAR) {
            //2Y
            showChart(quoteList.get(TWO_YEAR));
        } else if (time == ALL) {
            //ALL
            showChart(quoteList.get(ALL));
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;

        RectF bounds = mOnValueSelectedRectF;
        mChart1.getBarBounds((BarEntry) e, bounds);
        MPPointF position = mChart1.getPosition(e, YAxis.AxisDependency.LEFT);
        Log.w("ChartActivity", "onValueSelected: ");
        Log.w("ChartActivity", "onValueSelected: " + bounds.toString());
        Log.w("ChartActivity", "onValueSelected: " + position.toString());

        Log.w("ChartActivity", "onValueSelected: low: " + mChart1.getLowestVisibleX() + ", high: "
                + mChart1.getHighestVisibleX());

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {

    }

    class PaperViewAdapter extends FragmentStatePagerAdapter {
        private int k;
        ArrayList<Fragment> lsfrgment = new ArrayList<>();

        public ArrayList<Fragment> getLsfrgment() {
            return lsfrgment;
        }

        public PaperViewAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            Fragment a;
            Bundle bundle = new Bundle();


            return ChartFragment.newInstance();
        }

        @Override
        public int getCount() {
            return 7;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = "";
            switch (position) {
                case 0:
                    title = "1W";
                    break;
                case 1:
                    title = "1M";
                    break;
                case 2:
                    title = "3M";
                    break;
                case 3:
                    title = "6M";
                    break;
                case 4:
                    title = "1Y";
                    break;
                case 5:
                    title = "2Y";
                    break;
                case 6:
                    title = "ALL";
                    break;
            }
            return title;
        }

    }

}