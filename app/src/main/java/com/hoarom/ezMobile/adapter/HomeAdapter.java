package com.hoarom.ezMobile.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoarom.ezMobile.R;
import com.hoarom.ezMobile.model.ItemHome;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

/**
 * Created by Hoarom on 12/19/2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private static final int UNSELECTED = -1;

    private int selectedItem = UNSELECTED;

    LayoutInflater inflater;

    List<ItemHome> list;

    public HomeAdapter() {
    }

    public HomeAdapter(List<ItemHome> list) {
        this.list = list;
    }

    public HomeAdapter(Context context, List<ItemHome> list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());

        return new HomeViewHolder(inflater.inflate(R.layout.item_home_recyclerview_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final HomeViewHolder holder, int position) {
        Log.w("HomeAdapter", "onBindViewHolder: " + position);
        holder.bind(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class HomeViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, ExpandableLayout.OnExpansionUpdateListener {

        TextView title;

        RecyclerView recyclerView;

        ImageView imageView;

        ExpandableLayout expandableLayout;

        private int position;

        public HomeViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title).findViewById(R.id.textView);

            recyclerView = itemView.findViewById(R.id.recyclerView);

            imageView = itemView.findViewById(R.id.imageView);

            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            expandableLayout.setInterpolator(new OvershootInterpolator());
            expandableLayout.setOnExpansionUpdateListener(this);
        }

        public void bind(int position) {
            this.position = position;
            if (list.get(position).getData() == null) {
                return;
            }
            Log.w("HomeAdapter", "onBindViewHolder: " + list.get(position).getData().size());
            title.setText(list.get(position).getTitle().toString());

            QuoteAdapter adapter = new QuoteAdapter();
            adapter.addAll(list.get(position).getData());

            recyclerView.setAdapter(adapter);

            adapter.notifyDataSetChanged();

            LinearLayoutManager manager = new LinearLayoutManager(inflater.getContext());
            manager.setOrientation(LinearLayoutManager.VERTICAL);

            recyclerView.setLayoutManager(manager);
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expandableLayout.isExpanded()) {
                        expandableLayout.collapse();
                    } else {
                        expandableLayout.expand();
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            Log.w("HomeViewHolder", "onClick: ");
            HomeViewHolder holder = (HomeViewHolder) recyclerView.findViewHolderForAdapterPosition(selectedItem);
            if (holder != null) {
                holder.title.setSelected(false);
                holder.expandableLayout.collapse();
            }

            if (position == selectedItem) {
                selectedItem = UNSELECTED;
            } else {
                title.setSelected(true);
                expandableLayout.expand();
                selectedItem = position;
            }
        }

        @Override
        public void onExpansionUpdate(float expansionFraction) {
            Log.w("HomeViewHolder", "onExpansionUpdate: ");
            recyclerView.smoothScrollToPosition(getAdapterPosition());
        }
    }
}
