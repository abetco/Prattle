package edu.illinois.cs465.prattle;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
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
        dataModels = getMyHangouts(getContext());
        MyHangoutsAdapter adapter = new MyHangoutsAdapter(dataModels);
        recyclerView.setAdapter(adapter);
        return v;
    }

    public static ArrayList<HangoutModel> getMyHangouts(Context context) {
        JSONArray myHangoutsList = null;
        try {
            File f = new File("/data/data/" + context.getPackageName() + "/myHangouts.json");
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String mResponse = new String(buffer);
            myHangoutsList = new JSONArray(mResponse);
        }
        catch (IOException | JSONException e){
            e.printStackTrace();
        }
        ArrayList<HangoutModel> newHangouts = new ArrayList<>();
        if (myHangoutsList != null) {
            for (int i = 0; i < myHangoutsList.length(); i++) {
                try {
                    JSONObject hangout = myHangoutsList.getJSONObject(i);
                    JSONArray participantsJSON = hangout.getJSONArray("participants");
                    String participants[] = new String[participantsJSON.length()];
                    for (int j = 0; j < participantsJSON.length(); j++) {
                        participants[j] = participantsJSON.get(j).toString();
                    }
                    newHangouts.add(new HangoutModel(hangout.getString("title"), hangout.getString("date"),
                            hangout.getString("location"), hangout.getString("description"),
                            participants, hangout.getInt("minParticipants"), hangout.getInt("maxParticipants")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return newHangouts;
    }

    public static void writeMyHangouts(ArrayList<HangoutModel> data, Context context) {
        try {
            JSONArray myHangoutsList = new JSONArray();
            for (int i = 0; i < data.size(); i++) {
                JSONObject hangout = new JSONObject();
                hangout.put("title", data.get(i).getTitle());
                hangout.put("date", data.get(i).getDate());
                hangout.put("location", data.get(i).getLocation());
                hangout.put("description", data.get(i).getDescription());
                hangout.put("participants", new JSONArray(data.get(i).getParticipants()));
                hangout.put("minParticipants", data.get(i).getMinParticipants());
                hangout.put("maxParticipants", data.get(i).getMaxParticipants());
                myHangoutsList.put(hangout);
            }
            FileWriter file =
                    new FileWriter("/data/data/" + context.getPackageName() + "/myHangouts.json");
            file.write(myHangoutsList.toString());
            file.flush();
            file.close();
        }
        catch(IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        dataModels = getMyHangouts(getContext());
        MyHangoutsAdapter adapter = new MyHangoutsAdapter(dataModels);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.my_hangouts_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}