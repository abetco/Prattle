package edu.illinois.cs465.prattle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FreetimeAdapter extends RecyclerView.Adapter<FreetimeAdapter.dayHolder> {

    String[] week_days;
    Context context;

    public FreetimeAdapter(Context ct, String[] days){
        context = ct;
        week_days = days;
    }
    @NonNull
    @Override
    public dayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.day_freetime_item, parent,false);
        return new dayHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull dayHolder holder, int position) {
        holder.mytext1.setText(week_days[position]);
    }

    @Override
    public int getItemCount() {
        return week_days.length;
    }

    public class dayHolder extends RecyclerView.ViewHolder {
        TextView mytext1;
        public dayHolder(@NonNull View itemView) {
            super(itemView);
            mytext1 = itemView.findViewById(R.id.week_day);
        }
    }
}
