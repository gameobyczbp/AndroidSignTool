package com.abs.apksigninfo.helper;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.abs.apksigninfo.bean.ApkInfo;
import com.abs.apksigninfo.interfaces.ApkInfoChangedListener;
import com.abs.apksigninfo.manager.ApkInfoManager;
import com.abs.apksigninfo.service.LoadApkInfoService;

import java.util.ArrayList;
import java.util.List;

public class ApkHelper {
    private static final String TAG = "ApkHelper";

    private static ApkHelper smApkHelper;
    private List<ApkInfoChangedListener> listeners;

    private ApkInfoManager manager;


    private ApkHelper() {
        listeners = new ArrayList<>();
        manager = ApkInfoManager.newInstance();
    }

    public static ApkHelper newInstance() {
        if (smApkHelper == null) {
            smApkHelper = new ApkHelper();
        }
        return smApkHelper;
    }

    public void loadApkInfo(Context context) {
        Intent serviceIntent = new Intent(context, LoadApkInfoService.class);
        context.startService(serviceIntent);
    }

    public List<ApkInfo> loadApkInfoByPkgName(String pkgNameOrAppName) {
        if (TextUtils.isEmpty(pkgNameOrAppName)) {
            return manager.loadDataFromCache();
        }

        pkgNameOrAppName = pkgNameOrAppName.trim();
        List<ApkInfo> result = new ArrayList<>();
        for (ApkInfo each : manager.loadDataFromCache()) {
            if (each.pkgName.toLowerCase().contains(pkgNameOrAppName.toLowerCase())) {//先检测包名忽略大写
                result.add(each);
            } else if (each.appName.contains(pkgNameOrAppName)) {//检测应用名
                result.add(each);
            }
        }
        return result;
    }

    public List<ApkInfo> getApkInfoDataFromCache() {
        return manager.loadDataFromCache();
    }


    public List<ApkInfoChangedListener> getApkInfoListeners() {
        return listeners;
    }

    public void dispatchApkInfo(List<ApkInfo> result) {
        for (ApkInfoChangedListener each : getApkInfoListeners()) {
            each.onApkInfoChanged(result);
        }
    }

    public void registerListener(ApkInfoChangedListener listener) {
        if (!listeners.contains(listener))
            listeners.add(listener);
    }

    public void unRegisterListener(ApkInfoChangedListener listener) {
        listeners.remove(listener);
    }


}
