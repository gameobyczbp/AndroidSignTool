package com.abs.apksigninfo.app;

import android.app.Application;

public class App extends Application {

    public static App smApp;

    @Override
    public void onCreate() {
        super.onCreate();
        smApp = this;
    }


    public static App getInstance() {
        return smApp;
    }
}
