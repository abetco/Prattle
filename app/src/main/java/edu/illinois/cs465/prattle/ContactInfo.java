package edu.illinois.cs465.prattle;

import android.graphics.Bitmap;

public class ContactInfo {
    private String name;
    private ContactStatus status;
    private Bitmap photo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContactStatus getStatus() {
        return status;
    }

    public void setStatus(ContactStatus status) {
        this.status = status;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
}
