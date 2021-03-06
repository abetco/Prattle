package edu.illinois.cs465.prattle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.illinois.cs465.prattle.data.BrowseHangoutsAdapter;
import edu.illinois.cs465.prattle.data.HangoutModel;

public class BrowseHangoutsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_browse_hangouts, container, false);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.browse_hangouts_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<HangoutModel> dataModels;
        dataModels = new ArrayList<>();
        dataModels.add(new HangoutModel("Basketball at ARC", "Monday, 05/31, 6:30 PM", "Champaign, IL",
                "Come play basketball!", new String[]{"Amy", "Bob", "Joe"}, 2, 10));
        dataModels.add(new HangoutModel("Movie night", "Friday, 05/07, 8:30 PM", "Virtual",
                "Watching movies! We'll vote on one before we start.", new String[]{"Billy", "Joe"}, 1, 10));
        dataModels.add(new HangoutModel("Music Jam Session", "Tuesday, 05/18, 3:00 PM", "Virtual",
                "Moosic", new String[]{"Amy"}, 2, 4));
        Switch matchSwitch = v.findViewById(R.id.hangouts_switch);
        matchSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dataModels.remove(dataModels.size() - 1);
                }
                else {
                    dataModels.add(new HangoutModel("Music Jam Session", "Tuesday, 4/27, 3:00 PM", "Virtual",
                            "Moosic", new String[]{"Amy"}, 2, 4));
                }
                BrowseHangoutsAdapter adapter = new BrowseHangoutsAdapter(dataModels);
                recyclerView.setAdapter(adapter);
            }
        });

        BrowseHangoutsAdapter adapter = new BrowseHangoutsAdapter(dataModels);
        recyclerView.setAdapter(adapter);
        return v;
    }
}