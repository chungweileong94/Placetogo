package com.example.chungwei.placetogo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chungwei.placetogo.R;

import java.util.ArrayList;


public class MissionRecyclerViewAdapter extends RecyclerView.Adapter<MissionRecyclerViewAdapter.ViewHolder> {

    private Context contex;
    private ArrayList<String> list;

    public MissionRecyclerViewAdapter(Context contex, ArrayList<String> list) {
        this.contex = contex;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(contex).inflate(R.layout.item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.text);
        }
    }
}
