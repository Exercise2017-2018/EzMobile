package com.hoarom.ezMobile.asyncTasks;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Hoarom on 12/13/2017.
 */

public class JsonTask extends AsyncTask<String, String, String> {

    public interface TaskListener {
        public void onSuccess(String data);

        public  void onError();
    }

    private TaskListener taskListener;

    public  JsonTask (TaskListener taskListener){
        this.taskListener = taskListener;
    }

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
//            _iListenner.onError();
            if(taskListener != null){
                taskListener.onError();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

//        _iListenner.onSuccess(result);
        if(taskListener != null){
            if(result != null){
                taskListener.onSuccess(result);
            }else {
                taskListener.onError();
            }
        }
    }

}

