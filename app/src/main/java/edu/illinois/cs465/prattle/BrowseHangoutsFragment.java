package edu.illinois.cs465.prattle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        dataModels.add(new HangoutModel("Basketball", "5/31", "Chicago",
                "", new String[0], 0, 0));
        dataModels.add(new HangoutModel("Soccer", "4/30", "Virtual",
                "", new String[0], 0, 0));
        dataModels.add(new HangoutModel("Music", "3/31", "Virtual",
                "", new String[0], 0, 0));
        dataModels.add(new HangoutModel("Food", "5/18", "Urbana",
                "", new String[0], 0, 0));
        dataModels.add(new HangoutModel("Netflix", "5/23", "Champaign",
                "", new String[0], 0, 0));

        BrowseHangoutsAdapter adapter = new BrowseHangoutsAdapter(dataModels);
        recyclerView.setAdapter(adapter);
        return v;
    }
}