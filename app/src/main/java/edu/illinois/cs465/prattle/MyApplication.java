package edu.illinois.cs465.prattle;

import android.app.Application;

public class MyApplication extends Application {
    private Boolean clicked;

    public Boolean getClicked() {
        return clicked;
    }

    public void setClicked(Boolean clicked) {
        this.clicked = clicked;
    }
}
