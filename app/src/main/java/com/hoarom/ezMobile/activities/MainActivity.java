package com.hoarom.ezMobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
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
import android.widget.TextView;

import com.hoarom.ezMobile.R;
import com.hoarom.ezMobile.adapter.ExpandableListAdapter;
import com.hoarom.ezMobile.adapter.QuoteAdapter;
import com.hoarom.ezMobile.asyncTasks.JsonTask;
import com.hoarom.ezMobile.interfaces.IModel;
import com.hoarom.ezMobile.interfaces.JsonTaskListener;
import com.hoarom.ezMobile.model.Quote;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.hoarom.ezMobile.Manager.DECIMAL_FORMAT_NUM;
import static com.hoarom.ezMobile.Manager.TIME_DELAY_AUTO_LOAD;
import static com.hoarom.ezMobile.Manager.convertStringToQuote;
import static com.hoarom.ezMobile.Manager.convertStringToListQuote;
import static com.hoarom.ezMobile.Settings.SEARCH_ID_SERCURITIES;
import static com.hoarom.ezMobile.adapter.ExpandableListAdapter.ACTION_NEXT;
import static com.hoarom.ezMobile.adapter.ExpandableListAdapter.ACTION_NEXT_ADD_EDIT;
import static com.hoarom.ezMobile.adapter.ExpandableListAdapter.TYPE_BANG_GIA;
import static com.hoarom.ezMobile.adapter.ExpandableListAdapter.TYPE_TONG_QUAN_THI_TRUONG;
import static com.hoarom.ezMobile.api.api.APIS;
import static com.hoarom.ezMobile.api.api.API_LIST_QUOTE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private  Toolbar toolbar;

    private RecyclerView recyclerview;
    private ExpandableListAdapter adapter;

    //Phần thông tin chung
    List<IModel> _quotes_general = new ArrayList<>();

    //Phần bảng giá
    List<IModel> _quotes_price = new ArrayList<>();
    Handler _handler = new Handler();

    int _index = 0;//count list company download success

    JsonTaskListener _iListenner = new JsonTaskListener() {
        @Override
        public void onSuccess(String data) {
            Log.w("MainActivity", "onSuccess: " + _index);

            if (_quotes_general.size() < APIS.size()) {
                _quotes_general.add(convertStringToQuote(data, _index++));
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

        createView();

        updateUI();

        getData();
    }

    private void createView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerview = findViewById(R.id.recyclerView);


    }

    private void updateUI() {


        Log.w("MainActivity", "updateUI: ");
        Log.w("MainActivity", "updateUI: _quotes_general= " + _quotes_general.size());
        List<ExpandableListAdapter.Item> data = new ArrayList<>();
        data.add(new ExpandableListAdapter.Item(TYPE_TONG_QUAN_THI_TRUONG, "TỔNG QUAN THỊ TRƯỜNG", ACTION_NEXT, _quotes_general,
                getString(R.string.change), getString(R.string.values)));

        //phần bảng giá
        if (_quotes_price != null && _quotes_price.size() != 0) {
            Log.w("MainActivity", "updateUI: _quotes_price.size()= " + _quotes_price.size());
            data.add(new ExpandableListAdapter.Item(TYPE_BANG_GIA, "BẢNG GIÁ", ACTION_NEXT_ADD_EDIT, _quotes_price,
                    getString(R.string.change), getString(R.string.quantity)));
        } else {
            data.add(new ExpandableListAdapter.Item("BẢNG GIÁ", ACTION_NEXT_ADD_EDIT,
                    getString(R.string.change), getString(R.string.quantity)));
        }

        adapter = new ExpandableListAdapter(data);
        recyclerview.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerview.addItemDecoration(new DividerItemDecoration(MainActivity.this, layoutManager.getOrientation()));

        recyclerview.setLayoutManager(layoutManager);

        adapter.notifyDataSetChanged();
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

                    (new JsonTask(_iListenner)).execute(APIS.get(finalI));
                    _handler.postDelayed(this, TIME_DELAY_AUTO_LOAD * 1000000);
                }
            };
            _handler.post(timedTask);
        }

        //BẢNG GIÁ
        final String stringQuote = readFile("quote.txt");
        Log.w("MainActivity", "getData: " + stringQuote);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                (new JsonTask(new JsonTask.TaskListener() {
                    @Override
                    public void onSuccess(String data) {
                        //convert data to QuoteDetail

                        List<Quote> list = convertStringToListQuote(data);
                        if(_quotes_price.size() == list.size()){
                            _quotes_price.clear();
                        }
                        for (int i = 0; i < list.size(); i++) {
                            _quotes_price.add(list.get(i));
                        }
                        updateUI();
                    }

                    @Override
                    public void onError() {

                    }
                })).execute(API_LIST_QUOTE + stringQuote);

                _handler.postDelayed(this, TIME_DELAY_AUTO_LOAD * 10);
            }
        };
        _handler.post(runnable);
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
//    View.OnClickListener onClick_headerTable = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.change:
//                    //thay đổi % và tỷ số
//                    for (int i = 0; i < _quotes.size(); i++) {
//                        View itemView = recyclerView.getChildAt(i);
//                        Quote quote = (Quote) _quotes.get(i);
//                        if (itemView == null) {
//                            continue;
//                        }
//                        TextView change = (TextView) itemView.findViewById(R.id.change);
//                        if (isChange) {
//                            _textView_change.setText(R.string.percent);
//                            change.setText(quote.getPercent() == null ? 0 + "" : quote.getPercent() + "");
//                        } else {
//                            _textView_change.setText(R.string.change);
//                            change.setText(quote.getChangePrice() == null ? 0 + "" : quote.getChangePrice() + "");
//                        }
////                        _adapter.notifyItemChanged(i);
//                    }
//                    isChange = !isChange;
//                    break;
//                case R.id.values:
//                    //thay đổi giá trị và khối lượng
//                    for (int i = 0; i < _quotes.size(); i++) {
//                        Quote quote = (Quote) _quotes.get(i);
//                        View view = recyclerView.getChildAt(i);
//                        if (view == null)
//                            continue;
//                        TextView values = view.findViewById(R.id.values);
//                        if (isValues) {
//                            _textView_values.setText(R.string.quantity);
//                            Log.w("companies", quote.getTotalQtty() + "");
//                            values.setText(quote.getTotalQtty() == null || quote.getTotalQtty().equals(0)
//                                    ? "0" : DECIMAL_FORMAT_NUM.format(Double.parseDouble(quote.getTotalQtty())));
//                        } else {
//                            _textView_values.setText(R.string.values);
//                            values.setText(quote.getValues() == null || quote.getValues().equals(0)
//                                    ? "0" : DECIMAL_FORMAT_NUM.format(Double.parseDouble(quote.getValues())));
//                        }
//                    }
//                    isValues = !isValues;
//                    break;
//                default:
//                    break;
//            }
//        }
//    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SEARCH_ID_SERCURITIES) {
            //SEARCH

        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
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

}
