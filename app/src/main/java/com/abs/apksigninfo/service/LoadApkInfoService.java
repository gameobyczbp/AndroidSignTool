package com.abs.apksigninfo.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.abs.apksigninfo.bean.ApkInfo;
import com.abs.apksigninfo.helper.ApkHelper;
import com.abs.apksigninfo.manager.ApkInfoManager;

import java.util.List;

public class LoadApkInfoService extends IntentService {

    private static final String TAG = "LoadApkInfoService";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public LoadApkInfoService() {
        super("LoadApkInfoService");
        Log.i(TAG, "LoadApkInfoService:");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final List<ApkInfo> result = ApkInfoManager.newInstance().loadApkInfo(this);
        new Handler(Looper.getMainLooper())
                .post(new Runnable() {
                    @Override
                    public void run() {
                        ApkHelper.newInstance().dispatchApkInfo(result);
                    }
                });
    }
}
