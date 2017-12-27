package com.hoarom.ezMobile.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.hoarom.ezMobile.R;
import com.hoarom.ezMobile.adapter.PaperViewAdapter;
import com.hoarom.ezMobile.fragments.ChartFragment;
import com.hoarom.ezMobile.fragments.EventsFragment;
import com.hoarom.ezMobile.fragments.HomeFragment;
import com.hoarom.ezMobile.fragments.IndexFragment;
import com.hoarom.ezMobile.fragments.NewsFragment;
import com.hoarom.ezMobile.fragments.PriceFragment;
import com.hoarom.ezMobile.fragments.TongQuanThiTruongFragment;

import java.util.ArrayList;

import static com.hoarom.ezMobile.Settings.SEARCH_ID_SERCURITIES;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        HomeFragment.OnFragmentInteractionListener {

    DrawerLayout drawer;

    PaperViewAdapter _adapter;

    ActionBarDrawerToggle toggle;
    ViewPager view_pager;

    private Toolbar toolbar;

    private int values = 1;

    public static final int TRANG_CHU = 1;
    public static final int TONG_QUAN_THI_TRUONG = 2;
    public static final int BANG_GIA = 3;
    public static final int TIN_TUC = 4;
    public static final int LICH_SU_KIEN = 5;
    public static final int CHI_SO_THE_GIOI = 6;
    public static final int BIEU_DO = 7;
    public static final int DAT_LENH = 8;
    public static final int KY_QUY = 9;
    public static final int CHUYEN_TIEN = 10;
    public static final int BAN_LO_LE = 11;
    public static final int BAO_CAO_UNG_TRUOC = 12;
    public static final int BAO_CAO_TAI_SAN = 13;

    ArrayList<Fragment> listFragment = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createView();

        loadDataToViewPager();

        view_pager.setCurrentItem(TRANG_CHU);
    }

    private void createView() {

        toolbar = findViewById(R.id.toolbar);

        view_pager = findViewById(R.id.view_pager);


        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void loadDataToViewPager() {

        FragmentManager manager = getSupportFragmentManager();

        listFragment = new ArrayList<>();
        listFragment.add(new HomeFragment());
        listFragment.add(new TongQuanThiTruongFragment());
        listFragment.add(new PriceFragment());
        listFragment.add(new NewsFragment());
        listFragment.add(new EventsFragment());
        listFragment.add(new IndexFragment());
        listFragment.add(new ChartFragment());

        _adapter = new PaperViewAdapter(manager, listFragment);
        _adapter.notifyDataSetChanged();

        updatePager(listFragment);

        Log.w("MainActivity", "loadDataToViewPager: " + _adapter.getListFragment().size());
        view_pager.setAdapter(_adapter);

        updatetoolbar(R.id.nav_trangchu);
    }

    private void updatePager(final ArrayList<Fragment> fragments) {
        Log.w("MainActivity", "updatePager: ");
        _adapter = new PaperViewAdapter(getSupportFragmentManager(), listFragment);
        _adapter.notifyDataSetChanged();
        view_pager.setAdapter(_adapter);
        view_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.w("MainActivity", "onPageScrolled: ");
            }

            @Override
            public void onPageSelected(int position) {
                Log.w("MainActivity", "onPageSelected: ");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.w("MainActivity", "onPageScrollStateChanged: ");
            }
        });

        view_pager.setCurrentItem(1, false);
    }

    public void updatetoolbar(int k) {
        switch (k) {
            case R.id.nav_trangchu: {
                toolbar.setTitle(getString(R.string.app_name));
//                fr_layout.setBackgroundResource(R.drawable.backgroundnoel);
                break;
            }
            case R.id.nav_bieudo: {
                toolbar.setTitle("Biểu đồ VNINDEX");

                break;
            }
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    protected void onStop() {
        super.onStop();

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
//                        TextView values = view.findViewById(oR.id.values);
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

    public void updateCurDrawerType() {
        if (_adapter != null) {
            _adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SEARCH_ID_SERCURITIES) {
            //SEARCH

        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main2, menu);
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
        Log.w("MainActivity", "onNavigationItemSelected: " + id + " values = " + values);
        switch (id) {
            case R.id.nav_trangchu:
                values = TRANG_CHU;
                break;
            case R.id.nav_bieudo:
                values = BIEU_DO;
                break;


        }
        updatetoolbar(id);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        view_pager.setCurrentItem(values);

        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
