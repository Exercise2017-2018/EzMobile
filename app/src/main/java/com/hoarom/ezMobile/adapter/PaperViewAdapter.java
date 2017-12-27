package com.hoarom.ezMobile.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hoarom.ezMobile.R;
import com.hoarom.ezMobile.fragments.ChartFragment;
import com.hoarom.ezMobile.fragments.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import static com.hoarom.ezMobile.activities.MainActivity.BANG_GIA;
import static com.hoarom.ezMobile.activities.MainActivity.BIEU_DO;
import static com.hoarom.ezMobile.activities.MainActivity.TONG_QUAN_THI_TRUONG;
import static com.hoarom.ezMobile.activities.MainActivity.TRANG_CHU;

/**
 * Created by dinht on 12/26/2017.
 */

public class PaperViewAdapter extends FragmentStatePagerAdapter {
    List<Fragment> listFragment = new ArrayList<>();

    public List<Fragment> getListFragment() {
        notifyDataSetChanged();
        return listFragment;
    }

    public PaperViewAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        notifyDataSetChanged();
        this.listFragment = fragments;

    }

    @Override
    public Fragment getItem(int position) {
        notifyDataSetChanged();
        Fragment fragment;
        Bundle bundle = new Bundle();

        switch (position) {
            case TRANG_CHU:
                fragment = new HomeFragment();
                fragment.setArguments(bundle);
                listFragment.add(fragment);
                return fragment;

            case BIEU_DO:
                fragment = new ChartFragment();
                fragment.setArguments(bundle);
                listFragment.add(fragment);
                return fragment;

        }

        fragment = new HomeFragment();
        listFragment.add(fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case TRANG_CHU:
                title = String.valueOf(R.string.app_name);
                break;
            case BANG_GIA:
                title = "BANG_GIA";
                break;
            case BIEU_DO:
                title = "BIEU_DO";
                break;
            case TONG_QUAN_THI_TRUONG:
                title = "TONG_QUAN_THI_TRUONG";
                break;
        }
        return title;
    }
}

