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
        dataModels.add(new HangoutModel("Basketball", "5/31", "Chicago",
                "Come play basketball with me!", new String[0], 0, 0));
        dataModels.add(new HangoutModel("Soccer", "4/30", "Virtual",
                "", new String[0], 0, 0));
//        dataModels.add(new HangoutModel("Music", "3/31", "Virtual",
//                "", new String[0], 0, 0));
//        dataModels.add(new HangoutModel("Food", "5/18", "Urbana",
//                "", new String[0], 0, 0));
//        dataModels.add(new HangoutModel("Netflix", "5/23", "Champaign",
//                "", new String[0], 0, 0));

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
        dataModels.add(new HangoutModel("New thing", "2/30", "europe",
                "", new String[0], 0, 0));
        MyHangoutsAdapter adapter = new MyHangoutsAdapter(dataModels);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.my_hangouts_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}