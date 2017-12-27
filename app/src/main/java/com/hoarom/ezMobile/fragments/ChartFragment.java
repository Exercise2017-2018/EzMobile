package com.hoarom.ezMobile.fragments;

import android.content.Context;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hoarom.ezMobile.R;
import com.hoarom.ezMobile.activities.ChartActivity;
import com.hoarom.ezMobile.model.Quote;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.hoarom.ezMobile.Manager.FORMAT_DATE;

public class ChartFragment extends Fragment {

    private OnFragmentInteractionListener mListener;


    Handler _handler = new Handler();


    private BarChart mChart;
    private SmartTabLayout mTabLayout;
    List<Quote> quoteList = new ArrayList<>();

    public ChartFragment() {
    }

    public static ChartFragment newInstance() {
        ChartFragment fragment = new ChartFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTabLayout =view.findViewById(R.id.smart_tab);
        mTabLayout.setCustomTabView(_tabProvider);

        mChart = view.findViewById(R.id.barchart);
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chart_layout, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    private void showChart() {
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        mChart.getAxisLeft().setDrawGridLines(false);

        mChart.animateY(2500);

        mChart.getLegend().setEnabled(false);

        for (int i = 15; i < 20; i += 1) {

            float mult = (quoteList.size());
            float val = (float) (quoteList.get(i).getMatchPrice() * 1);
            try {
                Date date = FORMAT_DATE.parse(quoteList.get(i).getDate() + "");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Log.w("ChartActivity", "showChart: " + i + " " + val + " ." + quoteList.get(i).getDate() + ".");
            yVals1.add(new BarEntry(i, val));//x, y
        }

        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
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
    }


    private SmartTabLayout.TabProvider _tabProvider = new SmartTabLayout.TabProvider() {
        @Override
        public View createTabView(ViewGroup container, int position, android.support.v4.view.PagerAdapter adapter) {
            Log.w("ChartActivity", "createTabView: ");
            TextView textView = new TextView(getActivity());
            textView.setGravity(Gravity.CENTER);
//            Category category = _pagerAdapter.getCategory(position);
//            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, DensityUtil.dip2px(14));
            textView.setText("1M");
            textView.setAllCaps(true);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(4, 4, 4, 4);

            textView.setTextColor(makeSelectorColor(0xff444444, 0xff999999));

            return textView;
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static ColorStateList makeSelectorColor(int selector, int normal) {
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_selected},
                new int[]{android.R.attr.state_checked},
                new int[]{android.R.attr.state_pressed},
                new int[]{}
        };
        int[] colors = new int[]{
                selector,
                selector,
                selector,
                normal
        };
        ColorStateList res = new ColorStateList(states, colors);
        return res;
    }
}
