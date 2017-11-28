package com.hoarom.ezMobile.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hoarom.ezMobile.R;
import com.hoarom.ezMobile.interfaces.IDownload;
import com.hoarom.ezMobile.model.Company;
import com.hoarom.ezMobile.model.S;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.hoarom.ezMobile.Manager.DECIMAL_FORMAT;
import static com.hoarom.ezMobile.Manager.DECIMAL_FORMAT_NUM;
import static com.hoarom.ezMobile.Manager.TIME_DELAY_AUTO_LOAD;
import static com.hoarom.ezMobile.Manager.convertStringToCompany;
import static com.hoarom.ezMobile.Settings.STRING_API;
import static com.hoarom.ezMobile.Settings.STRING_COMPANY_NAME;

public class DetailActivity extends AppCompatActivity {

    Company _company = new Company();

    LinearLayout _linearLayout;// bảng chi tiết các thông số Si

    String _companyName = "";
    String _api = "";
    TextView _txtMain;
    TextView _txtNum;
    TextView _txtKL;
    TextView _txtGT;
    TextView _textUpDown;
    TextView _textThoaThuanGT;
    TextView _textThoaThuanSL;


    IDownload _iDownload = new IDownload() {
        @Override
        public void onSuccess(String data) {
            _company = convertStringToCompany(data, -1);

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
                new JsonTask().execute(_api);
                _handler.postDelayed(timedTask, TIME_DELAY_AUTO_LOAD);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        _txtMain = (TextView) findViewById(R.id.textMain);
        _txtNum = (TextView) findViewById(R.id.textNum);
        _txtKL = (TextView) findViewById(R.id.textKL);
        _txtGT = (TextView) findViewById(R.id.textGT);
        _textUpDown = (TextView) findViewById(R.id.textUpDown);
        _textThoaThuanGT = (TextView) findViewById(R.id.textThoaThuanGT);
        _textThoaThuanSL = (TextView) findViewById(R.id.textThoaThuanSL);

        _linearLayout = (LinearLayout) findViewById(R.id.linearlayout);

        //
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle bundle = getIntent().getExtras();
        if (bundle.getSerializable(STRING_API) != null) {
            _api = (String) bundle.getSerializable(STRING_API);
            _companyName = (String) bundle.getSerializable(STRING_COMPANY_NAME);
            new JsonTask().execute(_api);
        } else {
        }

        if (_companyName != null) {
            getSupportActionBar().setTitle(_companyName + " Chi tiết");
        } else {
            getSupportActionBar().setTitle(getString(R.string.app_name));
        }

        _handler.post(timedTask);
    }


    private void updateUI() {
        if (_company != null) {
            if (_companyName != null) {
                _txtMain.setText(_companyName + ": " + _company.getPrice());
            } else {
                _txtMain.setText(": " + _company.getPrice());
            }

            if (_company.getNum() != null) {
                _txtKL.setText(Html.fromHtml(getString(R.string.sl, DECIMAL_FORMAT_NUM.format(Double.parseDouble(_company.getNum())))));
            }
            if (_company.getValues() != null) {
                _txtGT.setText(Html.fromHtml(getString(R.string.gt, DECIMAL_FORMAT.format(Double.parseDouble(_company.getValues())))));
            }

            if (_company.getChange() != null && _company.getChange() > 0) {
                _txtNum.setText(Html.fromHtml(getString(R.string.up, _company.getChange() + "", _company.getPercent() + "")));
            } else if (_company.getChange() != null && _company.getChange() == 0) {
                _txtNum.setText(Html.fromHtml(getString(R.string.average, _company.getChange() + "", _company.getPercent() + "")));
            } else {
                _txtNum.setText(Html.fromHtml(getString(R.string.down, _company.getChange() + "", _company.getPercent() + "")));
            }

            _textUpDown.setText(Html.fromHtml(getString(R.string.up_down, _company.getNumUp() + "", _company.getNumAverage() + "",
                    _company.getNumDown() + "")));
            if (_company.getNum1() != null) {
                _textThoaThuanSL.setText(Html.fromHtml("SL:" + DECIMAL_FORMAT.format(_company.getNum1())));
            }
            if (_company.getValues1() != null) {
                _textThoaThuanGT.setText(Html.fromHtml("GT: " + DECIMAL_FORMAT.format(_company.getValues1())));
            }
            Log.w("list", _company.getListS().size() + "");

            LayoutInflater inflater = this.getLayoutInflater();
            LinearLayout linearLayout_row;
            TextView price;
            TextView percent;
            TextView num;
            TextView values;

            // after auto load (2s)
            //remove all view before add new view
            _linearLayout.removeAllViews();

            for (int i = 0; i < _company.getListS().size(); i++) {

                S s = _company.getListS().get(i);
                linearLayout_row = (LinearLayout) inflater.inflate(R.layout.item_table_detail_row_layout, null);

                price = (TextView) linearLayout_row.findViewById(R.id.price);
                percent = (TextView) linearLayout_row.findViewById(R.id.percent);
                num = (TextView) linearLayout_row.findViewById(R.id.num);
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

    private class JsonTask extends AsyncTask<String, String, String> {
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

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
