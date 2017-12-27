package com.hoarom.ezMobile.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hoarom.ezMobile.R;
import com.hoarom.ezMobile.adapter.ExpandableListAdapter;
import com.hoarom.ezMobile.asyncTasks.JsonTask;
import com.hoarom.ezMobile.interfaces.IModel;
import com.hoarom.ezMobile.interfaces.JsonTaskListener;
import com.hoarom.ezMobile.model.Quote;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.hoarom.ezMobile.Manager.TIME_DELAY_AUTO_LOAD;
import static com.hoarom.ezMobile.Manager.convertStringToListQuote;
import static com.hoarom.ezMobile.Manager.convertStringToQuote;
import static com.hoarom.ezMobile.adapter.ExpandableListAdapter.ACTION_NEXT;
import static com.hoarom.ezMobile.adapter.ExpandableListAdapter.ACTION_NEXT_ADD_EDIT;
import static com.hoarom.ezMobile.adapter.ExpandableListAdapter.TYPE_BANG_GIA;
import static com.hoarom.ezMobile.adapter.ExpandableListAdapter.TYPE_TONG_QUAN_THI_TRUONG;
import static com.hoarom.ezMobile.api.api.APIS;
import static com.hoarom.ezMobile.api.api.API_LIST_QUOTE;

public class HomeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;


    private RecyclerView recyclerview;
    private ExpandableListAdapter adapter;



    //Phần thông tin chung
    List<IModel> _quotes_general = new ArrayList<>();

    //Phần bảng giá
    List<IModel> _quotes_price = new ArrayList<>();
    Handler _handler = new Handler();

    int _index = 0;//count list company download success


    public HomeFragment() {

    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        updateUI();

    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_layout, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerview = view.findViewById(R.id.recyclerView);



    }
    private void updateUI() {

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

//        adapter = new ExpandableListAdapter(data);
//        recyclerview.setAdapter(adapter);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        recyclerview.addItemDecoration(new DividerItemDecoration(MainActivity.this, layoutManager.getOrientation()));
//
//        recyclerview.setLayoutManager(layoutManager);
//
//        adapter.notifyDataSetChanged();
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
                        if (_quotes_price.size() == list.size()) {
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
            input = getActivity().getAssets().open(filePath);

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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        _handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStop() {
        super.onStop();
        _handler.removeCallbacksAndMessages(null);
    }
}
