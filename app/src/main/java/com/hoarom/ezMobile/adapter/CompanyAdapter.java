package com.hoarom.ezMobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hoarom.ezMobile.R;
import com.hoarom.ezMobile.activities.DetailActivity;
import com.hoarom.ezMobile.model.Company;

import java.util.List;

import static com.hoarom.ezMobile.Settings.STRING_API;
import static com.hoarom.ezMobile.Settings.STRING_COMPANY_NAME;

/**
 * Created by Hoarom on 11/23/2017.
 */

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder> {
    List<Company> list;

    LayoutInflater inflater;

    public CompanyAdapter(List<Company> list, Context context) {
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public CompanyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_table_main_row_layout, parent, false);

        return new CompanyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CompanyViewHolder holder, int position) {
        final Company company = list.get(position);
        holder.name.setText(company.getName());
        holder.price.setText(company.getPrice() == null ? 0 + "" : company.getPrice() + "");
        holder.values.setText(company.getValues() == null ? 0 + "" : company.getValues() + "");
        if (company.getChange() != null) {
            holder.change.setText(company.getChange() + "");
            Log.w("company", company.getChange() + "");
            if (company.getChange() > 0) {
                holder.change.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.up));
                holder.price.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.up));
            } else if (company.getChange() == 0) {
                holder.change.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.average));
                holder.price.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.average));
            } else {
                holder.change.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.down));
                holder.price.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.down));
            }
        } else {
            holder.change.setText("0");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(STRING_API, company.getApi());
                bundle.putSerializable(STRING_COMPANY_NAME, company.getName());

                intent.putExtras(bundle);

                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CompanyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView price;
        TextView change;
        TextView values;

        public CompanyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            price = (TextView) view.findViewById(R.id.price);
            change = (TextView) view.findViewById(R.id.change);
            values = (TextView) view.findViewById(R.id.values);
        }
    }
}
