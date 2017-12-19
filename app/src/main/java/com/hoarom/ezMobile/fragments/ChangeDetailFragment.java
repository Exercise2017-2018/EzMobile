package com.hoarom.ezMobile.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hoarom.ezMobile.R;
import com.hoarom.ezMobile.adapter.QuoteAdapter;
import com.hoarom.ezMobile.asyncTasks.JsonTask;
import com.hoarom.ezMobile.interfaces.IModel;
import com.hoarom.ezMobile.interfaces.JsonTaskListener;
import com.hoarom.ezMobile.model.QuoteDetail;

import java.util.ArrayList;
import java.util.List;

import static com.hoarom.ezMobile.Manager.TIME_DELAY_AUTO_LOAD;
import static com.hoarom.ezMobile.Manager.getCByQuoteName;
import static com.hoarom.ezMobile.Manager.getListQuoteFromString;
import static com.hoarom.ezMobile.api.api.API_DETAIL;

public class ChangeDetailFragment extends Fragment {

    //ARGUMENT
    public static final String ARGUMENT_C = "ARGUMENT_C";
    public static final String ARGUMENT_TYPE = "ARGUMENT_TYPE";
    /**
     * type = best up/ best down/ most popular
     */
    private List<IModel> listQuote = new ArrayList<>();

    private int _type;
    private int _c;//tùy thuộc tên công ty

    private OnFragmentInteractionListener mListener;

    RecyclerView _recyclerView;
    QuoteAdapter _adapter;

    public ChangeDetailFragment() {
    }

    public static ChangeDetailFragment newInstance(int type, String quoteName) {
        ChangeDetailFragment fragment = new ChangeDetailFragment();
        Bundle args = new Bundle();
        Log.w("ChangeDetailFragment", "newInstance:  " + type + " " + quoteName);
        args.putInt(ARGUMENT_C, getCByQuoteName(quoteName));
        args.putInt(ARGUMENT_TYPE, type);

        fragment.setArguments(args);
        return fragment;
    }

    Handler _handler = new Handler();

    JsonTaskListener _iListenner = new JsonTaskListener() {
        @Override
        public void onSuccess(String data) {
            List<QuoteDetail> list = getListQuoteFromString(data);
            for (int i = 0; i < list.size(); i++) {
                listQuote.add((IModel) list.get(i));
            }
            updateUI();
        }

        @Override
        public void onError() {
            //do something
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _type = getArguments().getInt(ARGUMENT_TYPE);
            _c = getArguments().getInt(ARGUMENT_C);
        }

        Log.w("ChangeDetailFragment", "onCreate: c == " + _c + " type = " + _type);

        getData();
    }

    private void getData() {
        Runnable timedTask = new Runnable() {
            @Override
            public void run() {
                (new JsonTask(_iListenner)).execute(API_DETAIL + "c=" + _c + "&type=" + _type);
                _handler.postDelayed(this, TIME_DELAY_AUTO_LOAD * 1000000);
            }
        };
        _handler.post(timedTask);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_detail_change_layout, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        _recyclerView = view.findViewById(R.id.recyclerView);


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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void updateUI() {

        _adapter = new QuoteAdapter();
        _adapter.addAll(listQuote);
        _recyclerView.setAdapter(_adapter);

        _adapter.notifyDataSetChanged();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        _recyclerView.setLayoutManager(manager);

    }
}
