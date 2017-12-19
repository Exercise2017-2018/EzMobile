package com.hoarom.ezMobile.adapter;

import android.support.v7.widget.RecyclerView;

import com.hoarom.ezMobile.holders.AHolder;
import com.hoarom.ezMobile.interfaces.IModel;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Hoarom on 12/19/2017.
 */

public abstract class AAdapter extends RecyclerView.Adapter<AHolder> {

    public static interface IClickCallback {
        void onClick(IModel model, int position);
    }

    protected IClickCallback _callback;

    protected ArrayList<IModel> items = new ArrayList<>();

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void add(IModel model) {
        items.add(model);
    }

    public void add(int idx, IModel model) {
        items.add(idx, model);
    }

    public void addAll(Collection<IModel> model) {
        items.addAll(model);
    }

    public void clear() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void setClickCallback(IClickCallback callback) {
        _callback = callback;
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public ArrayList<IModel> getItems() {
        return items;
    }

    public IModel getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        IModel model = getItem(position);
        return model.getViewType();
    }


    @Override
    public void onBindViewHolder(AHolder holder, int position) {
        holder.bind(getItem(position), _callback);
    }
}
