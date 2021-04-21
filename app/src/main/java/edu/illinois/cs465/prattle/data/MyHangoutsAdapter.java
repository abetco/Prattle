package edu.illinois.cs465.prattle.data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.illinois.cs465.prattle.HangoutPage;
import edu.illinois.cs465.prattle.R;

public class MyHangoutsAdapter extends RecyclerView.Adapter<MyHangoutsAdapter.ViewHolder> {

    private ArrayList<HangoutModel> dataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleView;
        private final TextView dateView;
        private final TextView locationView;
        private final RelativeLayout itemView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            titleView = view.findViewById(R.id.my_hangouts_title);
            dateView = view.findViewById(R.id.my_hangouts_date);
            locationView = view.findViewById(R.id.my_hangouts_location);
            itemView = view.findViewById(R.id.my_hangouts_layout);
        }

        public TextView getTitleView() {
            return titleView;
        }
        public TextView getDateView() {
            return dateView;
        }
        public TextView getLocationView() {
            return locationView;
        }
        public RelativeLayout getItemView() {
            return itemView;
        }

    }

    public MyHangoutsAdapter(ArrayList<HangoutModel> newData) {
        this.dataSet = newData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_hangouts_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTitleView().setText(dataSet.get(position).getTitle());
        holder.getDateView().setText(dataSet.get(position).getDate());
        holder.getLocationView().setText(dataSet.get(position).getLocation());
        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HangoutPage.class);
                Bundle b = new Bundle();
                b.putString("title", dataSet.get(position).getTitle());
                b.putString("date", dataSet.get(position).getDate());
                b.putString("location", dataSet.get(position).getLocation());
                b.putString("description", dataSet.get(position).getDescription());
                intent.putExtras(b);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
