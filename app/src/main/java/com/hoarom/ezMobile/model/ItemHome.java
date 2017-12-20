package com.hoarom.ezMobile.model;

import com.hoarom.ezMobile.interfaces.IModel;

import java.util.List;

/**
 * Created by Hoarom on 12/19/2017.
 */

public class ItemHome {

    private String title;

    private List<IModel> data;

    public ItemHome() {
    }

    public ItemHome(String title, List<IModel> data) {
        this.title = title;
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<IModel> getData() {
        return data;
    }

    public void setData(List<IModel> data) {
        this.data = data;
    }
}
