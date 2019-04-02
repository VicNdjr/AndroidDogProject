package com.example.victorianadjar.myproject.View;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.victorianadjar.myproject.Model.Breed;
import com.example.victorianadjar.myproject.Controller.OnItemClickListener;
import com.example.victorianadjar.myproject.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable {
    private List<Breed> values;
    private List<Breed> allValues;
    private final OnItemClickListener listener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtHeader;
        public View layout;

        public MyViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
        }
    }

    public MyAdapter(List<Breed> myDataset, OnItemClickListener listener) {
        values = myDataset;
        this.listener = listener;
        allValues = new ArrayList<>(myDataset);
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.my_text_view, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Breed name = values.get(position);
        holder.txtHeader.setText(name.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(name);
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Breed> filteredList = new ArrayList<>();
            if (constraint==null||constraint.length()==0){
                filteredList.addAll(allValues);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Breed breed : allValues) {
                    if(breed.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(breed);
                    }

                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            values.clear();
            values.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
