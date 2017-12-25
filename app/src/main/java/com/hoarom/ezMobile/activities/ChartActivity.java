package com.hoarom.ezMobile.activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hoarom.ezMobile.R;
import com.hoarom.ezMobile.asyncTasks.JsonTask;

import static com.hoarom.ezMobile.Manager.TIME_DELAY_AUTO_LOAD;
import static com.hoarom.ezMobile.api.api.APIS;
import static com.hoarom.ezMobile.api.api.API_CHART;

public class ChartActivity extends AppCompatActivity {
    Handler _handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        Runnable timedTask = new Runnable() {
            @Override
            public void run() {

                (new JsonTask(new JsonTask.TaskListener() {
                    @Override
                    public void onSuccess(String data) {
                        Log.w("ChartActivity", "onSuccess: " + data);


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
}
