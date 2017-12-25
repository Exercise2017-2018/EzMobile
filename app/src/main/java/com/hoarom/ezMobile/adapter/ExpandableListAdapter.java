package com.hoarom.ezMobile.adapter;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoarom.ezMobile.R;
import com.hoarom.ezMobile.interfaces.IModel;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

public class ExpandableListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //phần header đề mục của mỗi phần
    //VD: TỔNG QUAN THỊ TRƯỜNG
    // BẢNG GIÁ
    public static final int TYPE_TONG_QUAN_THI_TRUONG = 0;

    //phần  bảng
    public static final int TYPE_BANG_GIA = 1;

    public static final int ACTION_NEXT = 1;
    public static final int ACTION_NEXT_ADD_EDIT = 2;


    private List<Item> data;

    public ExpandableListAdapter(List<Item> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = null;
        Context context = parent.getContext();
        float dp = context.getResources().getDisplayMetrics().density;
        int subItemPaddingLeft = (int) (18 * dp);
        int subItemPaddingTopAndBottom = (int) (5 * dp);
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.item_recyclerview_main_layout, parent, false);
        ListHeaderViewHolder header = new ListHeaderViewHolder(view);
        return header;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Item item = data.get(position);
        final ListHeaderViewHolder viewHolder = (ListHeaderViewHolder) holder;
        viewHolder.title.setText(item.title);
        if (item.action == ACTION_NEXT) {
            viewHolder.btn_next.setVisibility(View.VISIBLE);
            viewHolder.btn_add.setVisibility(View.GONE);
            viewHolder.btn_edit.setVisibility(View.GONE);
        } else {
//                action == ACTION_NEXT_ADD_EDIT
            viewHolder.btn_next.setVisibility(View.VISIBLE);
            viewHolder.btn_add.setVisibility(View.VISIBLE);
            viewHolder.btn_edit.setVisibility(View.VISIBLE);
        }

        viewHolder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set expand or closepand
                if (viewHolder.expandableLayout.isExpanded()) {
                    viewHolder.expandableLayout.collapse();
                } else {
                    viewHolder.expandableLayout.expand();
                }
            }
        });
        //Phần bảng
        viewHolder.change.setText(item.text1);
        viewHolder.values.setText(item.text2);


        LinearLayoutManager manager = new LinearLayoutManager(viewHolder.itemView.getContext(),
                LinearLayoutManager.VERTICAL, false);
        viewHolder.recyclerView.setLayoutManager(manager);
        QuoteAdapter adapter = new QuoteAdapter();
        adapter.addAll(item.listData);
        viewHolder.recyclerView.setAdapter(adapter);
        viewHolder.recyclerView.addItemDecoration(
                new DividerItemDecoration(viewHolder.itemView.getContext(), manager.getOrientation()));

        adapter.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private static class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView btn_next;
        public ImageView btn_edit;
        public ImageView btn_add;
        public RecyclerView recyclerView;
        public ExpandableLayout expandableLayout;
        public TextView change;
        public TextView values;

        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView);
            btn_next = itemView.findViewById(R.id.next);
            btn_edit = itemView.findViewById(R.id.edit);
            btn_add = itemView.findViewById(R.id.add);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            change = itemView.findViewById(R.id.header).findViewById(R.id.change);
            values = itemView.findViewById(R.id.header).findViewById(R.id.values);
        }
    }

    //mỗi item là 1 item on expandableListView
    public static class Item {
        //kiểu HEAD, CHILD
        public int type;
        //Title của từng phần tử recyclerview
        public String title;
        //hai giá trị text chỗ thay đổi, khối lượng
        public String text1;
        public String text2;
        //danh sách giá trị Quote truyền vào
        public List<IModel> listData;
        //action: add, edit, next
        public int action;


        public Item() {
        }

        public Item(int type, String title, int action, List<IModel> listData, String text1, String text2) {
            this.action = action;
            this.text1 = text1;
            this.text2 = text2;
            this.title = title;
            this.type = type;
            this.listData = listData;
        }

        public Item(String title, int action, String text1, String text2) {
            this.action = action;
            this.text1 = text1;
            this.text2 = text2;
            this.title = title;

            this.listData = new ArrayList<>();
        }
    }
}
