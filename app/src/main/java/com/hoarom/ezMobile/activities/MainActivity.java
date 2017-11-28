package com.hoarom.ezMobile.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hoarom.ezMobile.R;
import com.hoarom.ezMobile.adapter.CompanyAdapter;
import com.hoarom.ezMobile.interfaces.IDownload;
import com.hoarom.ezMobile.model.Company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.hoarom.ezMobile.Manager.DECIMAL_FORMAT_NUM;
import static com.hoarom.ezMobile.Manager.TIME_DELAY_AUTO_LOAD;
import static com.hoarom.ezMobile.Manager.convertStringToCompany;
import static com.hoarom.ezMobile.Manager.isOnline;
import static com.hoarom.ezMobile.Settings.SEARCH_ID_SERCURITIES;
import static com.hoarom.ezMobile.api.api.APIS;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CompanyAdapter _adapter;

    List<Company> _companies = new ArrayList<>();

    List<Company> _companies_temp = new ArrayList<>();

    LayoutInflater _inflater;
    TextView _textView_change;
    TextView _textView_values;
    TextView _textView_search;

    ProgressBar _progressBar;
    //update UI after 2s
    Handler _handler = new Handler();

    int _index = 0;//count list company download success
    IDownload _iDownload = new IDownload() {
        @Override
        public void onSuccess(String data) {
            Log.w("onSuccess", data + " " + _index);

            if (_companies_temp.size() < APIS.size()) {
                _companies_temp.add(convertStringToCompany(data, _index++));
            }
            if (_index == APIS.size()) {
                updateUI();
            }
        }

        @Override
        public void onError() {
            //do something
            Log.w("onError", "" + _index);
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

        getData();

        updateUI();
    }

    public void clearData() {
        _index = 0;
        if (!_companies.isEmpty()) {
            _companies.clear();
        }
        if (!_companies_temp.isEmpty()) {
            _companies_temp.clear();
        }
    }

    private void getData() {
        //COMPANIES
        Log.w("getData", "getda");
        _index = 0;
        for (int i = 0; i < APIS.size(); i++) {
            final int finalI = i;
            Runnable timedTask = new Runnable() {
                @Override
                public void run() {
                    clearData();
                    new JsonTask().execute(APIS.get(finalI));
                    _handler.postDelayed(this, TIME_DELAY_AUTO_LOAD * 10);
                }
            };
            _handler.post(timedTask);
        }
    }

    private void createView() {
        _inflater = this.getLayoutInflater();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

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

    }

    private void updateUI() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {


                Log.w("run", _companies.size() + "  " + _companies_temp.size());
                if (_companies.size() == 0 || (_companies.size() < _companies_temp.size())) {
                    Log.w("run", _companies.size() + " -- " + _companies_temp.size());
                    if (!_companies.isEmpty()) {
                        _companies.clear();
                    }
                    for (Company com : _companies_temp) {
                        _companies.add(com);
                    }

                    _adapter = new CompanyAdapter(_companies_temp, MainActivity.this);
                    recyclerView.setAdapter(_adapter);

                    _adapter.notifyDataSetChanged();
                    LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
                    manager.setOrientation(LinearLayoutManager.VERTICAL);

                    recyclerView.setLayoutManager(manager);
                } else {

                }
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
                    for (int i = 0; i < _companies.size(); i++) {
                        View itemView = recyclerView.getChildAt(i);
                        if (itemView == null) {
                            continue;
                        }
                        TextView change = (TextView) itemView.findViewById(R.id.change);
                        if (isChange) {
                            _textView_change.setText(R.string.percent);
                            change.setText(_companies.get(i).getPercent() == null ? 0 + "" : _companies.get(i).getPercent() + "");
                        } else {
                            _textView_change.setText(R.string.change);
                            change.setText(_companies.get(i).getChange() == null ? 0 + "" : _companies.get(i).getChange() + "");
                        }
//                        _adapter.notifyItemChanged(i);
                    }
                    isChange = !isChange;
                    break;
                case R.id.values:
                    //thay đổi giá trị và khối lượng
                    for (int i = 0; i < _companies.size(); i++) {
                        View view = recyclerView.getChildAt(i);
                        if (view == null)
                            continue;
                        TextView values = (TextView) view.findViewById(R.id.values);
                        if (isValues) {
                            _textView_values.setText(R.string.num);
                            Log.w("companies", _companies.get(i).getNum() + "");
                            values.setText(_companies.get(i).getNum() == null || _companies.get(i).getNum().equals(0)
                                    ? "0" : DECIMAL_FORMAT_NUM.format(Double.parseDouble(_companies.get(i).getNum())));
                        } else {
                            _textView_values.setText(R.string.values);
                            values.setText(_companies.get(i).getValues() == null || _companies.get(i).getValues().equals(0)
                                    ? "0" : DECIMAL_FORMAT_NUM.format(Double.parseDouble(_companies.get(i).getValues())));
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


    private class JsonTask extends AsyncTask<String, String, String> {
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            if (!isOnline(MainActivity.this)) {

                return null;
            }
            try {
                URL url = new URL(params[0]);

                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                _iDownload.onError();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            _iDownload.onSuccess(result);
        }

    }


}
