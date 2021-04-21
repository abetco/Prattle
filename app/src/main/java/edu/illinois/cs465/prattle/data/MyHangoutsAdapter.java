package edu.illinois.cs465.prattle.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.illinois.cs465.prattle.R;

public class MyHangoutsAdapter extends RecyclerView.Adapter<MyHangoutsAdapter.ViewHolder> {

    private ArrayList<HangoutModel> dataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleView;
        private final TextView datetxtView;
        private final TextView locationtxtView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            titleView = view.findViewById(R.id.my_hangouts_title);
            datetxtView = view.findViewById(R.id.my_hangouts_date);
            locationtxtView = view.findViewById(R.id.my_hangouts_location);
        }

        public TextView getTitleView() {
            return titleView;
        }
        public TextView getDatetxtView() {
            return datetxtView;
        }
        public TextView getLocationtxtView() {
            return locationtxtView;
        }

    }

    public MyHangoutsAdapter(ArrayList<HangoutModel> newData) {
        dataSet = newData;
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
        holder.getDatetxtView().setText(dataSet.get(position).getDate());
        holder.getLocationtxtView().setText(dataSet.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
