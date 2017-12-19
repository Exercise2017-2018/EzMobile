package com.hoarom.ezMobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hoarom.ezMobile.R;
import com.hoarom.ezMobile.adapter.QuoteAdapter;
import com.hoarom.ezMobile.asyncTasks.JsonTask;
import com.hoarom.ezMobile.interfaces.IModel;
import com.hoarom.ezMobile.interfaces.JsonTaskListener;
import com.hoarom.ezMobile.model.Quote;
import com.hoarom.ezMobile.model.QuoteDetail;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.hoarom.ezMobile.Manager.DECIMAL_FORMAT_NUM;
import static com.hoarom.ezMobile.Manager.TIME_DELAY_AUTO_LOAD;
import static com.hoarom.ezMobile.Manager.convertStringToCompany;
import static com.hoarom.ezMobile.Manager.convertStringToQuoteDetail;
import static com.hoarom.ezMobile.Settings.SEARCH_ID_SERCURITIES;
import static com.hoarom.ezMobile.api.api.APIS;
import static com.hoarom.ezMobile.api.api.API_LIST_QUOTE_DETAIL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView recyclerView;
    RecyclerView recyclerView_cost;

    QuoteAdapter _adapter;
    QuoteAdapter _quoteItemAdapter;

    List<IModel> _quotes = new ArrayList<>();

    List<IModel> _quotes_temp = new ArrayList<>();

    List<IModel> _quote_item = new ArrayList<>();

    LayoutInflater _inflater;
    TextView _textView_change;
    TextView _textView_values;
    TextView _textView_search;

    ProgressBar _progressBar;
    //update UI after 2s
    Handler _handler = new Handler();

    int _index = 0;//count list company download success
    JsonTaskListener _iListenner = new JsonTaskListener() {
        @Override
        public void onSuccess(String data) {
            Log.w("MainActivity", "onSuccess: " + _index);

            if (_quotes_temp.size() < APIS.size()) {
                _quotes_temp.add(convertStringToCompany(data, _index++));
            }
            if (_index == APIS.size()) {
                updateUI();
            }
        }

        @Override
        public void onError() {
            //do something
            Log.w("MainActivity", "onError: " + _index);
            if (_index == APIS.size()) {
                updateUI();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        createView();

        getData();

        updateUI();


    }

    public void clearData() {
        _index = 0;
        if (!_quotes.isEmpty()) {
            _quotes.clear();
        }
        if (!_quotes_temp.isEmpty()) {
            _quotes_temp.clear();
        }
    }

    private void getData() {
        //COMPANIES
        Log.w("MainActivity", "getData: ");
        _index = 0;
        for (int i = 0; i < APIS.size(); i++) {
            final int finalI = i;
            Runnable timedTask = new Runnable() {
                @Override
                public void run() {
                    clearData();
                    (new JsonTask(_iListenner)).execute(APIS.get(finalI));
                    _handler.postDelayed(this, TIME_DELAY_AUTO_LOAD * 1000000);
                }
            };
            _handler.post(timedTask);
        }

        //BẢNG GIÁ

        final String stringQuote = readFile("quote.txt");
        Log.w("MainActivity", "onCreate: " + stringQuote);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                (new JsonTask(new JsonTask.TaskListener() {
                    @Override
                    public void onSuccess(String data) {
                        //convert data to QuoteDetail
                        List<QuoteDetail> list = convertStringToQuoteDetail(data);
                        for (int i = 0; i < list.size(); i++) {
                            _quote_item.add((IModel) list.get(i));
                        }

                        updateUI_Cost();
                    }

                    @Override
                    public void onError() {

                    }
                })).execute(API_LIST_QUOTE_DETAIL + stringQuote);

                _handler.postDelayed(this, TIME_DELAY_AUTO_LOAD * 100000);
            }
        };
        _handler.post(runnable);
    }

    private void createView() {
        _inflater = this.getLayoutInflater();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_market);

        _textView_change = (TextView) findViewById(R.id.change);
        _textView_change.setOnClickListener(onClick_headerTable);
        _textView_values = (TextView) findViewById(R.id.values);
        _textView_values.setOnClickListener(onClick_headerTable);
        _textView_search = (TextView) findViewById(R.id.search);
        _textView_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, SearchActivity.class), SEARCH_ID_SERCURITIES);
            }
        });

        _textView_change.setText(R.string.change);
        _textView_values.setText(R.string.values);

        //phần bản giá
        recyclerView_cost = findViewById(R.id.recycler_view_price);
        LinearLayout linearLayout_title_cost = findViewById(R.id.linearlayout_title_cost);
        //thay đổi
        ((TextView) linearLayout_title_cost.findViewById(R.id.change)).setText(getString(R.string.change));
        //số lượng
        ((TextView) linearLayout_title_cost.findViewById(R.id.values)).setText(getString(R.string.quantity));
    }

    private void updateUI() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Log.w("MainActivity", _quotes.size() + "  " + _quotes_temp.size());
                if (_quotes.size() == 0 || (_quotes.size() < _quotes_temp.size())) {
                    Log.w("run", _quotes.size() + " -- " + _quotes_temp.size());
                    if (!_quotes.isEmpty()) {
                        _quotes.clear();
                    }
                    for (IModel com : _quotes_temp) {
                        _quotes.add(com);
                    }

                    _adapter = new QuoteAdapter();
                    recyclerView.setAdapter(_adapter);
                    _adapter.addAll(_quotes);

                    _adapter.notifyDataSetChanged();
                    LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
                    manager.setOrientation(LinearLayoutManager.VERTICAL);

                    recyclerView.setLayoutManager(manager);
                } else {

                }
            }
        });
    }

    private void updateUI_Cost() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.w("MainActivity", "run: " + _quote_item.size());
                _quoteItemAdapter = new QuoteAdapter();//_quote_item, MainActivity.this
                _quoteItemAdapter.addAll(_quote_item);
                recyclerView_cost.setAdapter(_quoteItemAdapter);

                _quoteItemAdapter.notifyDataSetChanged();
                LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
                manager.setOrientation(LinearLayoutManager.VERTICAL);

                ViewGroup.LayoutParams params = recyclerView_cost.getLayoutParams();
                params.height = _quote_item.size() * 70;
                recyclerView_cost.setLayoutParams(params);

                recyclerView_cost.setLayoutManager(manager);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        _handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        _handler.removeCallbacksAndMessages(null);
    }

    boolean isChange = true;
    boolean isValues = true;
    View.OnClickListener onClick_headerTable = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.change:
                    //thay đổi % và tỷ số
                    for (int i = 0; i < _quotes.size(); i++) {
                        View itemView = recyclerView.getChildAt(i);
                        Quote quote = (Quote) _quotes.get(i);
                        if (itemView == null) {
                            continue;
                        }
                        TextView change = (TextView) itemView.findViewById(R.id.change);
                        if (isChange) {
                            _textView_change.setText(R.string.percent);
                            change.setText(quote.getPercent() == null ? 0 + "" : quote.getPercent() + "");
                        } else {
                            _textView_change.setText(R.string.change);
                            change.setText(quote.getChange() == null ? 0 + "" : quote.getChange() + "");
                        }
//                        _adapter.notifyItemChanged(i);
                    }
                    isChange = !isChange;
                    break;
                case R.id.values:
                    //thay đổi giá trị và khối lượng
                    for (int i = 0; i < _quotes.size(); i++) {
                        Quote quote = (Quote) _quotes.get(i);
                        View view = recyclerView.getChildAt(i);
                        if (view == null)
                            continue;
                        TextView values = view.findViewById(R.id.values);
                        if (isValues) {
                            _textView_values.setText(R.string.quantity);
                            Log.w("companies", quote.getNum() + "");
                            values.setText(quote.getNum() == null || quote.getNum().equals(0)
                                    ? "0" : DECIMAL_FORMAT_NUM.format(Double.parseDouble(quote.getNum())));
                        } else {
                            _textView_values.setText(R.string.values);
                            values.setText(quote.getValues() == null || quote.getValues().equals(0)
                                    ? "0" : DECIMAL_FORMAT_NUM.format(Double.parseDouble(quote.getValues())));
                        }
                    }
                    isValues = !isValues;
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SEARCH_ID_SERCURITIES) {
            //SEARCH

        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String readFile(String filePath) {
        InputStream input;
        try {
            input = getAssets().open(filePath);

            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            // byte buffer into a string
            return new String(buffer);

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
