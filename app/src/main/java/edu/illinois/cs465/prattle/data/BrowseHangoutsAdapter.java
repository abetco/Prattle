package edu.illinois.cs465.prattle.data;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.illinois.cs465.prattle.HangoutPage;
import edu.illinois.cs465.prattle.R;

public class BrowseHangoutsAdapter extends RecyclerView.Adapter<BrowseHangoutsAdapter.ViewHolder> {

    private ArrayList<HangoutModel> dataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleView;
        private final TextView dateView;
        private final TextView locationView;
        private final TextView[] namesView;
        private final ImageView[] profilesView;
        private final TextView participantsView;
        private final TextView minParticipantsView;
        private final RelativeLayout itemView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            titleView = view.findViewById(R.id.browse_hangouts_title);
            dateView = view.findViewById(R.id.browse_hangouts_date);
            locationView = view.findViewById(R.id.browse_hangouts_location);
            namesView = new TextView[]{view.findViewById(R.id.browse_hangouts_name_1),
                    view.findViewById(R.id.browse_hangouts_name_2),
                    view.findViewById(R.id.browse_hangouts_name_3),
                    view.findViewById(R.id.browse_hangouts_name_4)};
            profilesView = new ImageView[]{view.findViewById(R.id.browse_hangouts_icon_1),
                    view.findViewById(R.id.browse_hangouts_icon_2),
                    view.findViewById(R.id.browse_hangouts_icon_3),
                    view.findViewById(R.id.browse_hangouts_icon_4)};
            participantsView = view.findViewById(R.id.browse_hangouts_participants);
            minParticipantsView = view.findViewById(R.id.browse_hangouts_min_participants);
            itemView = view.findViewById(R.id.browse_hangouts_layout);
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
        public TextView[] getNamesView() {
            return namesView;
        }
        public TextView getParticipantsView() {
            return participantsView;
        }
        public TextView getMinParticipantsView() {
            return minParticipantsView;
        }
        public ImageView[] getProfilesView() {
            return profilesView;
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
        String names[] = dataSet.get(position).getParticipants();
        TextView namesViews[] = holder.getNamesView();
        ImageView profilesViews[] = holder.getProfilesView();
        for (int i = 0; i < names.length; i++) {
            namesViews[i].setText(names[i]);
            profilesViews[i].setImageResource(R.drawable.ic_baseline_account_circle_24);
            if (names[i] == "Sally") {
                profilesViews[i].setImageResource(R.drawable.sally);
            }
            else if (names[i] == "Joe") {
                profilesViews[i].setImageResource(R.drawable.joe);
            }
            else if (names[i] == "Albert") {
                profilesViews[i].setImageResource(R.drawable.albert);
            }
            else if (names[i] == "Amy") {
                profilesViews[i].setImageResource(R.drawable.amy);
            }
            else if (names[i] == "Billy") {
                profilesViews[i].setImageResource(R.drawable.billy);
            }
            else if (names[i] == "Bob") {
                profilesViews[i].setImageResource(R.drawable.bob);
            }
            else if (names[i] == "Ethan") {
                profilesViews[i].setImageResource(R.drawable.ethan);
            }
        }
        String participantsText = "Attendees: " +
                String.valueOf(dataSet.get(position).getParticipants().length) + "/" +
                String.valueOf(dataSet.get(position).getMaxParticipants());
        holder.getParticipantsView().setText(participantsText);
        String minParticipantsText = "minimum: " + String.valueOf(dataSet.get(position).getMinParticipants());
        holder.getMinParticipantsView().setText(minParticipantsText);
        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HangoutPage.class);
                Bundle b = new Bundle();
                b.putString("title", dataSet.get(position).getTitle());
                b.putString("date", dataSet.get(position).getDate());
                b.putString("location", dataSet.get(position).getLocation());
                b.putString("description", dataSet.get(position).getDescription());
                b.putStringArray("participants", dataSet.get(position).getParticipants());
                b.putInt("minParticipants", dataSet.get(position).getMinParticipants());
                b.putInt("maxParticipants", dataSet.get(position).getMaxParticipants());
                b.putBoolean("isMyHangouts", false);
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
