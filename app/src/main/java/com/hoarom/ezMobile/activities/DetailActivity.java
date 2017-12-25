package com.hoarom.ezMobile.activities;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.hoarom.ezMobile.R;
import com.hoarom.ezMobile.asyncTasks.JsonTask;
import com.hoarom.ezMobile.fragments.ChangeDetailFragment;
import com.hoarom.ezMobile.fragments.DiagramDetailFragment;
import com.hoarom.ezMobile.interfaces.JsonTaskListener;
import com.hoarom.ezMobile.model.Quote;
import com.hoarom.ezMobile.model.S;

import static com.hoarom.ezMobile.Manager.DECIMAL_FORMAT;
import static com.hoarom.ezMobile.Manager.DECIMAL_FORMAT_NUM;
import static com.hoarom.ezMobile.Manager.TIME_DELAY_AUTO_LOAD;
import static com.hoarom.ezMobile.Manager.convertStringToQuote;
import static com.hoarom.ezMobile.Settings.STRING_API;
import static com.hoarom.ezMobile.Settings.STRING_COMPANY_NAME;
import static com.hoarom.ezMobile.api.api.ARGUMENT_TYPE_PRICE_DOWN;
import static com.hoarom.ezMobile.api.api.ARGUMENT_TYPE_PRICE_UP;
import static com.hoarom.ezMobile.api.api.ARGUMENT_TYPE_QUANTITY_UP;

public class DetailActivity extends AppCompatActivity implements ChangeDetailFragment.OnFragmentInteractionListener,
        DiagramDetailFragment.OnFragmentInteractionListener {
    private Toolbar toolbar;

    Quote _quote = new Quote();

    LinearLayout _linearLayout;// bảng chi tiết các thông số Si

    String _quoteName = "";
    String _api = "";
    TextView _txtMain;
    TextView _txtNum;
    TextView _txtKL;
    TextView _txtGT;
    TextView _textUpDown;
    TextView _textThoaThuanGT;
    TextView _textThoaThuanSL;

    //fragment
    ViewPager _viewPager;
    TabLayout _tabLayout;

    JsonTaskListener _iListenner = new JsonTaskListener() {
        @Override
        public void onSuccess(String data) {
            _quote = convertStringToQuote(data, -1);

            updateUI();
        }

        @Override
        public void onError() {
//do something
        }
    };

    //update UI after 2s
    Handler _handler = new Handler();
    Runnable timedTask = new Runnable() {
        @Override
        public void run() {
            if (_api != null) {
                new JsonTask(_iListenner).execute(_api);
                _handler.postDelayed(timedTask, TIME_DELAY_AUTO_LOAD * 100000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        FirebaseApp.initializeApp(DetailActivity.this);

        createView();

        Bundle bundle = getIntent().getExtras();
        if (bundle.getSerializable(STRING_API) != null) {
            if (bundle.getSerializable(STRING_API) != null) {
                _api = (String) bundle.getSerializable(STRING_API);
            }
            _quoteName = (String) bundle.getSerializable(STRING_COMPANY_NAME);
            JsonTask jsonTask = new JsonTask(_iListenner);
            jsonTask.execute(_api);
        } else {
        }

        if (_quoteName != null) {
            getSupportActionBar().setTitle(_quoteName + " Chi tiết");
            toolbar.setTitle(_quoteName + " Chi tiết");
        } else {
            toolbar.setTitle(getString(R.string.app_name));
        }

        _handler.post(timedTask);
    }

    private void createView() {

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        _txtMain = findViewById(R.id.textMain);
        _txtNum = findViewById(R.id.textNum);
        _txtKL = findViewById(R.id.textKL);
        _txtGT = findViewById(R.id.textGT);
        _textUpDown = findViewById(R.id.textUpDown);
        _textThoaThuanGT = findViewById(R.id.textThoaThuanGT);
        _textThoaThuanSL = findViewById(R.id.textThoaThuanSL);

        _linearLayout = findViewById(R.id.linearlayout);

        //fragment
        _viewPager = findViewById(R.id.view_pager);
        _tabLayout = findViewById(R.id.tab_layout);
        FragmentManager manager = getSupportFragmentManager();

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(manager);
        _viewPager.setAdapter(adapter);
        _tabLayout.setupWithViewPager(_viewPager);
        _viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(_tabLayout));
        _tabLayout.setTabsFromPagerAdapter(adapter);
        //        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void updateUI() {
        if (_quote != null) {
            if (_quoteName != null) {
                _txtMain.setText(_quoteName + ": " + _quote.getMatchPrice());
            } else {
                _txtMain.setText(": " + (_quote.getMatchPrice() == null ? "0" : _quote.getMatchPrice()));
            }

            if (_quote.getTotalQtty() != null) {
                _txtKL.setText(Html.fromHtml(getString(R.string.sl, DECIMAL_FORMAT_NUM.format(Double.parseDouble(_quote.getTotalQtty())))));
            }
            if (_quote.getValues() != null) {
                _txtGT.setText(Html.fromHtml(getString(R.string.gt, DECIMAL_FORMAT.format(Double.parseDouble(_quote.getValues())))));
            }

            if (_quote.getChangePrice() != null && _quote.getChangePrice() > 0) {
                _txtNum.setText(Html.fromHtml(getString(R.string.up, _quote.getChangePrice() + "", _quote.getPercent() + "")));
            } else if (_quote.getChangePrice() != null && _quote.getChangePrice() == 0) {
                _txtNum.setText(Html.fromHtml(getString(R.string.average, _quote.getChangePrice() + "", _quote.getPercent() + "")));
            } else {
                _txtNum.setText(Html.fromHtml(getString(R.string.down, _quote.getChangePrice() + "", _quote.getPercent() + "")));
            }

            _textUpDown.setText(Html.fromHtml(getString(R.string.up_down, _quote.getNumUp() + "", _quote.getNumAverage() + "",
                    _quote.getNumDown() + "")));
            if (_quote.getNum1() != null) {
                _textThoaThuanSL.setText(Html.fromHtml("SL:" + DECIMAL_FORMAT.format(_quote.getNum1())));
            }
            if (_quote.getValues1() != null) {
                _textThoaThuanGT.setText(Html.fromHtml("GT: " + DECIMAL_FORMAT.format(_quote.getValues1())));
            }
            Log.w("DetailActivity", "updateUI: " + _quote.getListS().size());

            LayoutInflater inflater = this.getLayoutInflater();
            LinearLayout linearLayout_row;
            TextView price;
            TextView percent;
            TextView num;
            TextView values;

            // after auto load (2s)
            //remove all view before add new view
            _linearLayout.removeAllViews();

            for (int i = 0; i < _quote.getListS().size(); i++) {

                S s = _quote.getListS().get(i);
                linearLayout_row = (LinearLayout) inflater.inflate(R.layout.item_table_detail_row_s_layout, null);

                price = linearLayout_row.findViewById(R.id.price);
                percent = (TextView) linearLayout_row.findViewById(R.id.percent);
                num = linearLayout_row.findViewById(R.id.num);
                values = (TextView) linearLayout_row.findViewById(R.id.values);

                price.setText("S" + (i + 1) + ": " + s.getMain());
                if (s.getNum1() > 0) {
                    percent.setText(Html.fromHtml(getString(R.string.up, s.getNum1() + "", s.getPercent() + "")));
                } else if (s.getNum1() == 0) {
                    percent.setText(Html.fromHtml(getString(R.string.average, s.getNum1() + "", s.getPercent() + "")));
                } else {
                    percent.setText(Html.fromHtml(getString(R.string.down, s.getNum1() + "", s.getPercent() + "")));
                }
                num.setText("SL: " + DECIMAL_FORMAT.format(s.getSl()));
                values.setText("GT: " + DECIMAL_FORMAT.format(s.getGt()));

                _linearLayout.addView(linearLayout_row);
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        onBackPressed();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        _handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        _handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class FragmentPagerAdapter extends FragmentStatePagerAdapter {

        private final int COUNT_FRAGMENT = 4;

        public FragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.w("FragmentPagerAdapter", "getItem: " + _quoteName);
            switch (position) {
                case 0://diagram
                    return new DiagramDetailFragment().newInstance();
                case 1:
//                best up
                    return new ChangeDetailFragment().newInstance(ARGUMENT_TYPE_PRICE_UP, _quoteName);
                case 2:
//                best down
                    return new ChangeDetailFragment().newInstance(ARGUMENT_TYPE_PRICE_DOWN, _quoteName);
                default:
//            case 3:
                    //most popular
                    return new ChangeDetailFragment().newInstance(ARGUMENT_TYPE_QUANTITY_UP, _quoteName);
            }
        }

        @Override
        public int getCount() {
            return COUNT_FRAGMENT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = "";
            switch (position) {
                case 0:
                    title = "BIỂU ĐỒ";
                    break;
                case 1:
                    title = "TĂNG MẠNH";
                    break;
                case 2:
                    title = "GIẢM MẠNH";
                    break;
                case 3:
                    title = "GD NHIỀU";
                    break;

            }
            return title;
        }
    }
}