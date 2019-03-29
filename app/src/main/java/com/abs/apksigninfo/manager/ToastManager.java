package com.abs.apksigninfo.manager;

import android.widget.Toast;

import com.abs.apksigninfo.app.App;

public class ToastManager {

    public static void s(String msg) {
        Toast.makeText(App.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }
}
