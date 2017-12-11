package com.messedcode.swipecollapseanimationexample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<Item> dataset = new ArrayList<>();

    public CustomAdapter(String[] strings) {
        for (String string : strings) {
            dataset.add(new Item(string));
        }
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(dataset.get(position).name);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void add(Item item) {
        dataset.add(item);
        notifyItemInserted(dataset.size() - 1);
    }

    public void remove(int position) {
        dataset.remove(position);
        notifyItemRemoved(position);
    }

    public Item getItemAt(int position) {
        return dataset.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(LinearLayout v) {
            super(v);
            textView = (TextView) v.getChildAt(0);
        }
    }
}

