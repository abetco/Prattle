package edu.illinois.cs465.prattle;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

public class ContactAdapter extends ArrayAdapter {
    private List contactInfoList;
    private Context context;

    public ContactAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.contactInfoList = objects;
        this.context = context;
    }

    private class ViewHolder {
        TextView name;
        Button status;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void UpdateButton(ViewHolder holder, ContactStatus status) {
        holder.status.setText(status.toString());
        int newColor;
        if (status == ContactStatus.OFTEN) {
            newColor = Color.argb(180, 20, 150, 20);
        } else if (status == ContactStatus.SOMETIMES) {
            newColor = Color.argb(200, 240, 150, 25);
        } else {
            newColor = Color.argb(180, 250, 30, 30);
        }
        holder.status.setBackgroundTintList(ColorStateList.valueOf(newColor));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.contact_info, null);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.contactName);
            holder.status = (Button) convertView.findViewById(R.id.contactStatus);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ContactInfo contactInfo = (ContactInfo) contactInfoList.get(position);
        holder.name.setText(contactInfo.getName());
        UpdateButton(holder, contactInfo.getStatus());

        ViewHolder finalHolder = holder;
        holder.status.setOnClickListener(view -> {
            ContactStatus status = contactInfo.getStatus();
            if (status == ContactStatus.OFTEN) {
                contactInfo.setStatus(ContactStatus.NEVER);
            } else if (status == ContactStatus.SOMETIMES) {
                contactInfo.setStatus(ContactStatus.OFTEN);
            } else {
                contactInfo.setStatus(ContactStatus.SOMETIMES);
            }
            UpdateButton(finalHolder, contactInfo.getStatus());
        });
        return convertView;
    }
}
