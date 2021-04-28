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
import android.widget.ImageView;
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
        Button tag;
        ImageView photo;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void UpdateButton(ViewHolder holder, ContactTag tag) {
        holder.tag.setText(tag.toString());
        int newColor;
        if (tag == ContactTag.OFTEN) {
            newColor = Color.argb(180, 20, 150, 20);
        } else if (tag == ContactTag.SOMETIMES) {
            newColor = Color.argb(200, 240, 150, 25);
        } else {
            newColor = Color.argb(180, 250, 30, 30);
        }
        holder.tag.setBackgroundTintList(ColorStateList.valueOf(newColor));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.contact_info, null);

            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.contactName);
            holder.tag = convertView.findViewById(R.id.contactStatus);
            holder.photo = convertView.findViewById(R.id.contactPhoto);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ContactInfo contactInfo = (ContactInfo) contactInfoList.get(position);
        holder.name.setText(contactInfo.getName());
        holder.photo.setImageBitmap(contactInfo.getPhoto());
        UpdateButton(holder, contactInfo.getTag());

        ViewHolder finalHolder = holder;
        holder.tag.setOnClickListener(view -> {
            ContactTag tag = contactInfo.getTag();
            if (tag == ContactTag.OFTEN) {
                contactInfo.setTag(ContactTag.NEVER);
            } else if (tag == ContactTag.SOMETIMES) {
                contactInfo.setTag(ContactTag.OFTEN);
            } else {
                contactInfo.setTag(ContactTag.SOMETIMES);
            }
            UpdateButton(finalHolder, contactInfo.getTag());
        });
        return convertView;
    }
}
