package com.hoarom.ezMobile.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hoarom.ezMobile.R;
import com.hoarom.ezMobile.activities.DetailActivity;
import com.hoarom.ezMobile.activities.QuoteDetailActivity;
import com.hoarom.ezMobile.holders.AHolder;
import com.hoarom.ezMobile.interfaces.IModel;
import com.hoarom.ezMobile.model.Quote;
import com.hoarom.ezMobile.model.QuoteDetail;

import static com.hoarom.ezMobile.Settings.STRING_API;
import static com.hoarom.ezMobile.Settings.STRING_COMPANY_NAME;
import static com.hoarom.ezMobile.interfaces.IModel.T_QUOTE;
import static com.hoarom.ezMobile.interfaces.IModel.T_QUOTE_ITEM;

/**
 * Created by Hoarom on 11/23/2017.
 */

public class QuoteAdapter extends AAdapter {


    public QuoteAdapter() {

    }

    @Override
    public AHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        Log.w("QuoteAdapter", "onCreateViewHolder: " + viewType);
        switch (viewType) {
            case T_QUOTE: {
                return new QuoteViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_table_main_row_layout, parent, false));
            }
            case IModel.T_QUOTE_DETAIL:
                return new QuoteDetailViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_table_main_row_layout, parent, false));
            case T_QUOTE_ITEM: {
                return new QuoteItemViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_table_detail_cell_layout, parent, false));
            }

//            case T_QUOTE_DETAIL_ITEM:
            default:
                return new QuoteViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_table_main_row_layout, parent, false));
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class QuoteViewHolder extends AHolder {
        TextView name;
        TextView price;
        TextView change;
        TextView values;

        public QuoteViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            price = (TextView) view.findViewById(R.id.price);
            change = (TextView) view.findViewById(R.id.change);
            values = (TextView) view.findViewById(R.id.values);
        }

        @Override
        public void bind(IModel model, IClickCallback callback) {
            final Quote quote = (Quote) model;
            name.setText(quote.getName());
            price.setText(quote.getPrice() == null ? 0 + "" : quote.getPrice() + "");
            values.setText(quote.getValues() == null ? 0 + "" : quote.getValues() + "");
            if (quote.getChange() != null) {
                change.setText(quote.getChange() + "");
                Log.w("QuoteViewHolder", "bind: " + quote.getName());
                if (quote.getChange() > 0) {
                    change.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color_text_up));
                    price.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color_text_up));
                } else if (quote.getChange() == 0) {
                    change.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color_text_average));
                    price.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color_text_average));
                } else {
                    change.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color_text_down));
                    price.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color_text_down));
                }
            } else {
                change.setText("0");
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(STRING_API, quote.getApi());
                    bundle.putSerializable(STRING_COMPANY_NAME, quote.getName());

                    intent.putExtras(bundle);

                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }

    public class QuoteDetailViewHolder extends AHolder {
        TextView name;
        TextView price;
        TextView change;
        TextView values;

        public QuoteDetailViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            price = (TextView) view.findViewById(R.id.price);
            change = (TextView) view.findViewById(R.id.change);
            values = (TextView) view.findViewById(R.id.values);
        }

        @Override
        public void bind(IModel model, IClickCallback callback) {
            Log.w("QuoteDetailViewHolder", "bind: ");
        }
    }

    public class QuoteItemViewHolder extends AHolder {
        TextView name;//cột 1: mã
        TextView price;//cột 2: giá
        TextView change;//cột 3: thay đổi: phần trăm, hoặc khối lượng
        TextView values;//cột 4: khối lượng

        public QuoteItemViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            price = view.findViewById(R.id.price);
            change = view.findViewById(R.id.change);
            values = view.findViewById(R.id.values);
        }

        @Override
        public void bind(IModel model, AAdapter.IClickCallback callback) {
            Log.w("QuoteItemViewHolder", "bind: ");
            final QuoteDetail quote = (QuoteDetail) model;

            name.setText(quote.getCode());
            price.setText(quote.getMatchPrice() == null ? 0 + "" : quote.getMatchPrice() + "");
            //thay đổi
            values.setText(quote.getTotalQtty() == null ? 0 + "" : quote.getTotalQtty() + "");
            if (quote.getChange() != null) {
                change.setText(quote.getChangePrice() + "");
                Log.w("QuoteItemAdapter", "onBindViewHolder: " + quote.getChangePrice());
                if (quote.getRefPrice() > 0) {
                    change.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color_text_up));
                    price.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color_text_up));

                } else if (quote.getRefPrice() == 0) {
                    change.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color_text_average));
                    price.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color_text_average));

                } else {
                    change.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color_text_down));
                    price.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color_text_down));

                }
            } else {
                change.setText("0");
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), QuoteDetailActivity.class);
                    Bundle bundle = new Bundle();
//                bundle.putSerializable(STRING_API, quote.getApi());
                    intent.putExtras(bundle);

                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
