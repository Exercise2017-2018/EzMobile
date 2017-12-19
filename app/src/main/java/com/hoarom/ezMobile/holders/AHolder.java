package com.hoarom.ezMobile.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hoarom.ezMobile.adapter.AAdapter;
import com.hoarom.ezMobile.interfaces.IModel;

/**
 * Created by Hoarom on 12/19/2017.
 */

public abstract class AHolder extends RecyclerView.ViewHolder {
    public AHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(IModel model, AAdapter.IClickCallback callback);
}

