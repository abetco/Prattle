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
import edu.illinois.cs465.prattle.data.HangoutModel;
import edu.illinois.cs465.prattle.data.MyHangoutsAdapter;

public class MyHangoutsFragment extends Fragment {

    private ArrayList<HangoutModel> dataModels;
    private View v;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_my_hangouts, container, false);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.my_hangouts_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);

        dataModels = new ArrayList<>();
        dataModels.add(new HangoutModel("Dinner at McDonald's", "Friday, 4/23, 7:00 PM", "Urbana, IL",
                "For anybody craving some McDonald's", new String[]{"Ethan", "Sally", "You"}, 2, 4));
        dataModels.add(new HangoutModel("Swimming at the ARC", "Saturday 4/24, 4:00 PM", "Champaign, IL",
                "Going swimming at the ARC", new String[]{"Albert", "You"}, 1, 6));

        Boolean clicked = ((MyApplication) getActivity().getApplication()).getClicked();
        if (clicked != null && clicked) {
            dataModels.add(new HangoutModel("New thing", "2/30", "europe",
                    "", new String[0], 0, 0));
        }

        MyHangoutsAdapter adapter = new MyHangoutsAdapter(dataModels);
        recyclerView.setAdapter(adapter);
        return v;
    }

    public void updateContent() {
        if (dataModels.get(dataModels.size() - 1).getTitle() == "Six Flags Hangout!") {
            return;
        }
        dataModels.add(new HangoutModel("Six Flags Hangout!", "Sunday, 4/25, 12:00 PM", "Gurnee, IL",
                "I want to have fun", new String[]{"You"}, 1, 8));
        MyHangoutsAdapter adapter = new MyHangoutsAdapter(dataModels);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.my_hangouts_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}