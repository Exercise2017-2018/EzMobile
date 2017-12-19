package com.hoarom.ezMobile.services;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class SmsJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.w("SmsJobService", "onStartJob: Performing long running task in scheduled job");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
