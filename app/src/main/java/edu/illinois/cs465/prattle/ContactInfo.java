package edu.illinois.cs465.prattle;

import android.graphics.Bitmap;

public class ContactInfo {
    private String name;
    private ContactTag tag;
    private Bitmap photo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContactTag getTag() {
        return tag;
    }

    public void setTag(ContactTag tag) {
        this.tag = tag;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
}
