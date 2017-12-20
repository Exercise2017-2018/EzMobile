package com.hoarom.ezMobile.activities;

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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.hoarom.ezMobile.R;
import com.hoarom.ezMobile.adapter.HomeAdapter;
import com.hoarom.ezMobile.asyncTasks.JsonTask;
import com.hoarom.ezMobile.interfaces.IModel;
import com.hoarom.ezMobile.interfaces.JsonTaskListener;
import com.hoarom.ezMobile.model.ItemHome;

import java.util.ArrayList;
import java.util.List;

import static com.hoarom.ezMobile.Manager.TIME_DELAY_AUTO_LOAD;
import static com.hoarom.ezMobile.Manager.convertStringToCompany;
import static com.hoarom.ezMobile.api.api.APIS;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;


    List<IModel> _quotes_temp = new ArrayList<>();

    ProgressBar _progressBar;
    //update UI after 2s
    Handler _handler = new Handler();
    HomeAdapter adapter;

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

            Log.w("HomeActivity", "onSuccess: " + data);
        }

        @Override
        public void onError() {
            //do something
            Log.w("MainActivity", "onError: " + _index);
            if (_index == APIS.size()) {

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

    }


    private void createView() {
        recyclerView = findViewById(R.id.recyclerView);


    }

    private void getData() {
        //COMPANIES
        Log.w("HomeActivity", "getData: ");
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
    }

    private void updateUI() {
        List<ItemHome> list = new ArrayList<>();

        list.add(0, new ItemHome("TỔNG QUAN THỊ TRƯỜNG", _quotes_temp));
        Log.w("HomeActivity", "updateUI: _quotes_temp = " + _quotes_temp.size());
        adapter = new HomeAdapter(HomeActivity.this, list);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        LinearLayoutManager manager = new LinearLayoutManager(HomeActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        Log.w("HomeActivity", "updateUI: " + list.size());
        recyclerView.setLayoutManager(manager);


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
        getMenuInflater().inflate(R.menu.home, menu);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
