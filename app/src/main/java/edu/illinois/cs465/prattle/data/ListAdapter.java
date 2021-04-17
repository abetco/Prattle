package edu.illinois.cs465.prattle.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.illinois.cs465.prattle.R;

public class ListAdapter extends ArrayAdapter<MyHangoutsModel> implements View.OnClickListener{

    private ArrayList<MyHangoutsModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtDate;
        TextView txtLocation;
    }

    public ListAdapter(ArrayList<MyHangoutsModel> data, Context context) {
        super(context, R.layout.my_hangouts_item);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
//        int position=(Integer) v.getTag();
//        Object object= getItem(position);
//        DataModel dataModel=(DataModel)object;
//
//        switch (v.getId())
//        {
//            case R.id.item_info:
//                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//                break;
//        }
    }

//    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        MyHangoutsModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.my_hangouts_item, parent, false);
            viewHolder.txtName = convertView.findViewById(R.id.my_hangouts_name);
            viewHolder.txtDate = convertView.findViewById(R.id.my_hangouts_date);
            viewHolder.txtLocation = convertView.findViewById(R.id.my_hangouts_location);

            result = convertView;

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

//        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
//        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getName());
        viewHolder.txtDate.setText(dataModel.getDate());
        viewHolder.txtLocation.setText(dataModel.getLocation());
        return convertView;
    }
}
