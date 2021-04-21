package edu.illinois.cs465.prattle.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.illinois.cs465.prattle.R;

public class BrowseHangoutsAdapter extends RecyclerView.Adapter<BrowseHangoutsAdapter.ViewHolder> {

    private ArrayList<HangoutModel> dataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleView;
        private final TextView dateView;
        private final TextView locationView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            titleView = view.findViewById(R.id.browse_hangouts_title);
            dateView = view.findViewById(R.id.browse_hangouts_date);
            locationView = view.findViewById(R.id.browse_hangouts_location);
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

    }

    public BrowseHangoutsAdapter(ArrayList<HangoutModel> newData) {
        dataSet = newData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.browse_hangouts_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTitleView().setText(dataSet.get(position).getTitle());
        holder.getDateView().setText(dataSet.get(position).getDate());
        holder.getLocationView().setText(dataSet.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
